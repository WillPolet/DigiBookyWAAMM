package com.switchfully.digibooky.authorization.service;

import com.switchfully.digibooky.exception.AccessForbiddenException;
import com.switchfully.digibooky.exception.NotFoundException;
import com.switchfully.digibooky.exception.PasswordNotMatchException;
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
    private static final User USER_MEMBER1 = new Member("email", "lastname", "firstname", "password", ADDRESS, "INSS");
    private static final User USER_MEMBER2 = new Member("email2", "lastname", "firstname", "password2", ADDRESS, "INSS");
    private static final User USER_ADMIN = new Admin("email", "lastname", "firstname", "pass");
    private static final User USER_LIBRARIAN = new Librarian("email", "lastname", "firstname", "password");
    @Mock
    UserRepository userRepository;

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

        authorizationService.isSameUser(USER_MEMBER1.getId(), authorization);
    }

    @Test
    void givenAuthorizationHeaderWithExistingMember_whenNotTheSameUserId_thenThrowException() {
        Mockito.when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(USER_MEMBER1));
        String authorization = "Basic " + Base64.getEncoder().encodeToString("email:password".getBytes());

        Assertions.assertThatThrownBy(
                        () -> authorizationService.isSameUser(USER_MEMBER2.getId(), authorization))
                .isInstanceOf(AccessForbiddenException.class)
                .hasMessage("Authenticated user cannot lend a book for another member");
    }
}