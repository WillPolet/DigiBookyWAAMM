package com.switchfully.digibooky.user.service;


import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import com.switchfully.digibooky.user.domain.Librarian;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.librarian.CreateLibrarianDto;
import com.switchfully.digibooky.user.service.dto.librarian.LibrarianDto;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final Address ADDRESS = new Address("rue Ferra", "3", "75013", "Paris");
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserMapper userMapperMock;
    @InjectMocks
    private UserService userServiceMock;

    @Nested
    @DisplayName("MEMBER CREATION")
    class MemberCreation {
        private static final Member MEMBER = new Member("a.b@hotmail.fr", "lastName", "password", ADDRESS, "33A");
        private static final CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto(
                MEMBER.getEmail(),
                MEMBER.getLastname(),
                MEMBER.getFirstname(),
                MEMBER.getPassword(),
                MEMBER.getAddress(),
                MEMBER.getInss());
        private static final MemberDto MEMBER_DTO = new MemberDto(
                MEMBER.getId(),
                MEMBER.getEmail(),
                MEMBER.getLastname(),
                MEMBER.getFirstname(),
                MEMBER.getAddress());

        private static final CreateLibrarianDto CREATE_LIBRARIAN_DTO = new CreateLibrarianDto(
                "couocu@mail.com",
                "Atmey",
                "Luke",
                "pwd");
        private static final Librarian LIBRARIAN = new Librarian(
                CREATE_LIBRARIAN_DTO.getEmail(),
                CREATE_LIBRARIAN_DTO.getLastName(),
                CREATE_LIBRARIAN_DTO.getFirstName(),
                CREATE_LIBRARIAN_DTO.getPassword());

        private static final LibrarianDto LIBRARIAN_DTO = new LibrarianDto(
                LIBRARIAN.getId(),
                LIBRARIAN.getEmail(),
                LIBRARIAN.getLastname(),
                LIBRARIAN.getFirstname());

        @Test
        @DisplayName("Creating a member should return a new corresponding member DTO")
        void creatingUser_whenGivenCorrectData_thenWillReturnUserDTO() {
            //GIVEN
            Mockito.when(userMapperMock.toMember(CREATE_MEMBER_DTO))
                    .thenReturn(MEMBER);
            Mockito.when(userRepositoryMock.addUser(MEMBER)).thenReturn(MEMBER);
            Mockito.when(userMapperMock.toMemberDto(MEMBER))
                    .thenReturn(MEMBER_DTO);

            //WHEN
            MemberDto actualMemberDto = userServiceMock.addMember(CREATE_MEMBER_DTO);

            // THEN
            Assertions.assertThat(actualMemberDto.getId()).isEqualTo(MEMBER.getId());
        }

        @Test
        @DisplayName("Creating a librarian should return a new corresponding librarian DTO")
        void creatingLibrarian_whenGivenCorrectData_thenWillReturnLibrarianDTO() {
            //GIVEN
            Mockito.when(userMapperMock.toLibrarian(CREATE_LIBRARIAN_DTO))
                    .thenReturn(LIBRARIAN);
            Mockito.when(userRepositoryMock.addUser(LIBRARIAN)).thenReturn(LIBRARIAN);
            Mockito.when(userMapperMock.toLibrarianDto(LIBRARIAN))
                    .thenReturn(LIBRARIAN_DTO);

            LibrarianDto actualLibrarianDto = userServiceMock.addLibrarian(CREATE_LIBRARIAN_DTO);
            Assertions.assertThat(actualLibrarianDto.getId()).isEqualTo(LIBRARIAN.getId());
        }

        @Test
        @DisplayName("Trying to create an already existing member(unique INSS) : throw Exc")
        void creatingUser_whenMemberAlreadyCreatedCheckOnINSS_thenPermissionDeniedAndNoMemberCreated() {
            //GIVEN
            Mockito.when(userRepositoryMock.getMemberByInss(CREATE_MEMBER_DTO.getInss())).thenReturn(Optional.of(MEMBER));

            String expectedMessage = "A user with this INSS is already created.";
            Assertions.assertThatThrownBy(() -> userServiceMock.addMember(CREATE_MEMBER_DTO))
                    .isInstanceOf(UniqueFieldAlreadyExistException.class)
                    .hasMessageContaining(expectedMessage);
        }

        @Nested
        @DisplayName("ADMIN CREATION")
        class AdminCreation {
//        private static final Member MEMBER = new Member("a.b@hotmail.fr", "lastName", "password", ADDRESS, "33A");
//        private static final CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto(
//                MEMBER.getEmail(),
//                MEMBER.getLastname(),
//                MEMBER.getFirstname(),
//                MEMBER.getPassword(),
//                MEMBER.getAddress(),
//                MEMBER.getInss());
//        private static final MemberDto MEMBER_DTO = new MemberDto(
//                MEMBER.getId(),
//                MEMBER.getEmail(),
//                MEMBER.getLastname(),
//                MEMBER.getFirstname(),
//                MEMBER.getAddress());
//
//        @Test
//        @DisplayName("Creating a member should return a new corresponding member DTO")
//        void creatingUser_whenGivenCorrectData_thenWillReturnUserDTO() {
//            //GIVEN
//            Mockito.when(userMapperMock.toMember(CREATE_MEMBER_DTO))
//                    .thenReturn(MEMBER);
//            Mockito.when(userRepositoryMock.addUser(MEMBER)).thenReturn(MEMBER);
//            Mockito.when(userMapperMock.toMemberDto(MEMBER))
//                    .thenReturn(MEMBER_DTO);
//
//            //WHEN
//            MemberDto actualMemberDto = userServiceMock.addMember(CREATE_MEMBER_DTO);
//
//            // THEN
//            Assertions.assertThat(actualMemberDto.getId()).isEqualTo(MEMBER.getId());
//        }
//
//        @Test
//        @DisplayName("Trying to create an already existing member(unique INSS) : throw Exc")
//        void creatingUser_whenMemberAlreadyCreated_thenPermissionDeniedAndNoMemberCreated() {
//            //GIVEN
//            Mockito.when(userRepositoryMock.getMemberByInss(CREATE_MEMBER_DTO.getInss())).thenReturn(Optional.of(MEMBER));
//
//            String expectedMessage = "A user with this INSS is already created.";
//            Assertions.assertThatThrownBy(() -> userServiceMock.addMember(CREATE_MEMBER_DTO))
//                    .isInstanceOf(UniqueFieldAlreadyExistException.class)
//                    .hasMessageContaining(expectedMessage);
//        }
        }


        @Test
        @DisplayName("Trying to create an already existing member(unique EMAIL) : throw Exc")
        void creatingUser_whenMemberAlreadyCreatedCheckOnEmail_thenPermissionDeniedAndNoMemberCreated() {
            //GIVEN
            Mockito.when(userRepositoryMock.getMemberByEmail(CREATE_MEMBER_DTO.getEmail())).thenReturn(Optional.of(MEMBER));

            String expectedMessage = "A user with this email is already created.";
            Assertions.assertThatThrownBy(() -> userServiceMock.addMember(CREATE_MEMBER_DTO))
                    .isInstanceOf(UniqueFieldAlreadyExistException.class)
                    .hasMessageContaining(expectedMessage);
        }
    }

    @Nested
    @DisplayName("ADMIN CREATION")
    class AdminCreation {
    }
}

