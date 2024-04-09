package com.switchfully.digibooky.user.service;


import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import com.switchfully.digibooky.user.domain.Admin;
import com.switchfully.digibooky.user.domain.Librarian;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.admin.AdminDto;
import com.switchfully.digibooky.user.service.dto.admin.CreateAdminDto;
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

import java.util.List;
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
        private static final Member MEMBER = new Member("a.b@hotmail.fr",
                "lastName",
                "password",
                ADDRESS,
                "33A");

        private static final Member MEMBER2 = new Member("b.c@hotmail.fr",
                "lastName",
                "password",
                ADDRESS,
                "34A");

        private static final Member MEMBER3 = new Member("c.d@hotmail.fr",
                "lastName",
                "password",
                ADDRESS,
                "35A");
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

        private static final MemberDto MEMBER_DTO2 = new MemberDto(
                MEMBER2.getId(),
                MEMBER2.getEmail(),
                MEMBER2.getLastname(),
                MEMBER2.getFirstname(),
                MEMBER2.getAddress());

        private static final MemberDto MEMBER_DTO3 = new MemberDto(
                MEMBER3.getId(),
                MEMBER3.getEmail(),
                MEMBER3.getLastname(),
                MEMBER3.getFirstname(),
                MEMBER3.getAddress());

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

        private static final CreateAdminDto CREATE_ADMIN_DTO = new CreateAdminDto(
                "couocu@mail.com",
                "Atmey",
                "Luke",
                "pwd");
        private static final Admin ADMIN = new Admin(
                CREATE_ADMIN_DTO.getEmail(),
                CREATE_ADMIN_DTO.getLastName(),
                CREATE_ADMIN_DTO.getFirstName(),
                CREATE_ADMIN_DTO.getPassword());

        private static final AdminDto ADMIN_DTO = new AdminDto(
                ADMIN.getId(),
                ADMIN.getEmail(),
                ADMIN.getLastname(),
                ADMIN.getFirstname());

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
        @DisplayName("Creating an admin should return a new corresponding admin DTO")
        void creatingAdmin_givenCorrectData_willReturnAdminDto() {
            //GIVEN
            Mockito.when(userMapperMock.toAdmin(CREATE_ADMIN_DTO))
                    .thenReturn(ADMIN);
            Mockito.when(userRepositoryMock.addUser(ADMIN)).thenReturn(ADMIN);
            Mockito.when(userMapperMock.toAdminDto(ADMIN))
                    .thenReturn(ADMIN_DTO);

            AdminDto actualAdminDto = userServiceMock.addAdmin(CREATE_ADMIN_DTO);
            Assertions.assertThat(actualAdminDto.getId()).isEqualTo(ADMIN.getId());
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

        @Test
        @DisplayName("Trying to create an already existing member(unique EMAIL) : throw Exc")
        void creatingUser_whenMemberAlreadyCreatedCheckOnEmail_thenPermissionDeniedAndNoMemberCreated() {
            // GIVEN
            Mockito.when(userRepositoryMock.getUserByEmail(CREATE_MEMBER_DTO.getEmail())).thenReturn(Optional.of(MEMBER));

            String expectedMessage = "A user with this email is already created.";
            Assertions.assertThatThrownBy(() -> userServiceMock.addMember(CREATE_MEMBER_DTO))
                    .isInstanceOf(UniqueFieldAlreadyExistException.class)
                    .hasMessageContaining(expectedMessage);
        }

        @Test
        @DisplayName("GetAllMembers transorm the list of Member in list of MemberDto")
        void getAllMembers_whenCalled_returnListMemberDto(){
            // GIVEN
            Mockito.when(userRepositoryMock.getAllMembers())
                    .thenReturn(List.of(MEMBER, MEMBER2, MEMBER3));

            Mockito.when(userMapperMock.toMemberDto(List.of(MEMBER, MEMBER2, MEMBER3)))
                    .thenReturn(List.of(MEMBER_DTO, MEMBER_DTO2, MEMBER_DTO3));

            List<MemberDto> listMemberDtoToFind = List.of(MEMBER_DTO, MEMBER_DTO2, MEMBER_DTO3);

            // WHEN
            List<MemberDto> actualListMemberDto = userServiceMock.getAllMembers();

            Assertions.assertThat(actualListMemberDto).containsExactlyInAnyOrderElementsOf(listMemberDtoToFind);
        }
    }
}

