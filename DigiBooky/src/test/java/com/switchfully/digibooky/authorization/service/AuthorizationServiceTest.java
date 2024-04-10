package com.switchfully.digibooky.authorization.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.exception.AccessForbiddenException;
import com.switchfully.digibooky.exception.NotFoundException;
import com.switchfully.digibooky.exception.PasswordNotMatchException;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.domain.LendingRepository;
import com.switchfully.digibooky.user.domain.*;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    private static final Address ADDRESS = new Address("streetName", "streetNumber", "zipCode", "city");
    private static final Member USER_MEMBER1 = new Member("email", "lastname", "firstname", "password", ADDRESS, "INSS");
    private static final User USER_MEMBER2 = new Member("email2", "lastname", "firstname", "password2", ADDRESS, "INSS");
    private static final User USER_ADMIN = new Admin("email", "lastname", "firstname", "pass");
    private static final User USER_LIBRARIAN = new Librarian("email", "lastname", "firstname", "password");
    private static final Author AUTHOR = new Author("firstname", "lastname");
    private static final Book BOOK1 = new Book("isbn1", "title1", "summary", AUTHOR);
    private static final Lending LENDING1 = new Lending(USER_MEMBER1, BOOK1);
    @Mock
    UserRepository userRepository;
    @Mock
    LendingRepository lendingRepository;

    @InjectMocks
    AuthorizationService authorizationService;

    @Test
    void givenAuthorizationHeaderWithNotExistingMember_whenAccessFeature_thenThrowException() {
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());
        Assertions.assertThatThrownBy(
                () -> authorizationService.hasFeature(RoleFeature.DELETE_BOOK, authorization))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Email in authorization header not found");
    }

    @Test
    void givenAuthorizationHeaderWithExistingMemberWithBadPassword_whenAccessFeature_thenThrowException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_ADMIN));
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());
        Assertions.assertThatThrownBy(
                        () -> authorizationService.hasFeature(RoleFeature.DELETE_BOOK, authorization))
                .isInstanceOf(PasswordNotMatchException.class)
                .hasMessage("Email/password in authorization header doesn't match");
    }

    @Test
    void givenAuthorizationHeaderWithExistingMemberWithoutFeature_whenAccessFeature_thenThrowException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_MEMBER1));
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());
        Assertions.assertThatThrownBy(
                        () -> authorizationService.hasFeature(RoleFeature.DELETE_BOOK, authorization))
                .isInstanceOf(AccessForbiddenException.class)
                .hasMessage("Authenticated user have no access to this feature");
    }

    @Test
    void givenAuthorizationHeaderWithExistingMemberWithFeature_whenAccessFeature_thenNoException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_LIBRARIAN));
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());
        authorizationService.hasFeature(RoleFeature.DELETE_BOOK, authorization);
    }

    @Test
    void givenAuthorizationHeaderWithExistingMember_whenSameUserId_thenNoException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_MEMBER1));
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());

        authorizationService.isUserSameAsAuthorizationUser(USER_MEMBER1.getId(), authorization);
    }

    @Test
    void givenAuthorizationHeaderWithExistingMember_whenNotTheSameUserId_thenThrowException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_MEMBER1));
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());

        Assertions.assertThatThrownBy(
                        () -> authorizationService.isUserSameAsAuthorizationUser(USER_MEMBER2.getId(), authorization))
                .isInstanceOf(AccessForbiddenException.class)
                .hasMessage("Authenticated user cannot lend a book for another member");
    }

    @Test
    void givenAuthorizationHeaderAndLendingWithSameUser_whenCheckLendingOwner_thenNoException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_MEMBER1));
        Mockito.when(lendingRepository.getLendingById(LENDING1.getId())).thenReturn(Optional.of(LENDING1));
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());
        authorizationService.isLendingOwnedByAuthorizationUser(LENDING1.getId(), authorization);
    }

    @Test
    void givenAuthorizationHeaderAndLendingWithDifferentUser_whenCheckLendingOwner_thenThrowException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_MEMBER2));
        Mockito.when(lendingRepository.getLendingById(LENDING1.getId())).thenReturn(Optional.of(LENDING1));

        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());

        Assertions.assertThatThrownBy(
                        () -> authorizationService.isLendingOwnedByAuthorizationUser(LENDING1.getId(), authorization))
                .isInstanceOf(AccessForbiddenException.class)
                .hasMessage("Authenticated user does not own this lending");
    }
}