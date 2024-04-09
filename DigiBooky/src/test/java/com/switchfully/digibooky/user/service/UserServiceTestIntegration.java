package com.switchfully.digibooky.user.service;

import com.switchfully.digibooky.user.domain.Librarian;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.admin.AdminDto;
import com.switchfully.digibooky.user.service.dto.admin.CreateAdminDto;
import com.switchfully.digibooky.user.service.dto.librarian.CreateLibrarianDto;
import com.switchfully.digibooky.user.service.dto.librarian.LibrarianDto;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTestIntegration {

    UserMapper userMapper = new UserMapper();
    UserRepository userRepository = new UserRepository();
    UserService userService = new UserService(userMapper, userRepository);
    private final static Address ADDRESS = new Address(
            "street",
            "number",
            "0000",
            "Bxl");
    private final static CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto(
            "a@a.com",
            "lastname",
            "firstname",
            "pswd",
            ADDRESS,
            "A33");
    private static final CreateAdminDto CREATE_ADMIN_DTO = new CreateAdminDto(
            "couocu@mail.com",
            "Atmey",
            "Luke",
            "pwd");

    private static final CreateLibrarianDto CREATE_LIBRARIAN_DTO = new CreateLibrarianDto(
            "couocu@mail.com",
            "Atmey",
            "Luke",
            "pwd");

    @Test
    void addMember_whenCorrectMemberDTO_thenGoodMemberDTOReturn() {
        //GIVEN

        //WHEN
        MemberDto memberDto = userService.addMember(CREATE_MEMBER_DTO);

        //THEN
        Assertions.assertThat(CREATE_MEMBER_DTO.getEmail()).isEqualTo(memberDto.getEmail());
        Assertions.assertThat(memberDto.getId()).isNotNull();
        Assertions.assertThat(memberDto.getLastName()).isNotNull();
        Assertions.assertThat(memberDto.getFirstName()).isNotNull();
        Assertions.assertThat(memberDto.getAddress().getStreetName()).isNotNull();
        Assertions.assertThat(memberDto.getAddress().getStreetNumber()).isNotNull();
        Assertions.assertThat(memberDto.getAddress().getCity()).isNotNull();
        Assertions.assertThat(memberDto.getAddress().getZipCode()).isNotNull();
    }

    @Test
    void addAdmin_whenCorrectAdminDTO_thenGoodAdminDTOReturn() {
        //GIVEN

        //WHEN
        AdminDto adminDto = userService.addAdmin(CREATE_ADMIN_DTO);

        //THEN
        Assertions.assertThat(CREATE_ADMIN_DTO.getEmail()).isEqualTo(adminDto.getEmail());
        Assertions.assertThat(adminDto.getId()).isNotNull();
        Assertions.assertThat(adminDto.getFirstName()).isNotNull();
        Assertions.assertThat(adminDto.getLastName()).isNotNull();
    }

    @Test
    void addLibrarian_whenCorrectLibrarianDTO_thenGoodLibrarianDTOReturn() {
        //GIVEN

        //WHEN
        LibrarianDto librarianDto = userService.addLibrarian(CREATE_LIBRARIAN_DTO);

        //THEN
        Assertions.assertThat(CREATE_LIBRARIAN_DTO.getEmail()).isEqualTo(librarianDto.getEmail());
        Assertions.assertThat(librarianDto.getId()).isNotNull();
        Assertions.assertThat(librarianDto.getFirstName()).isNotNull();
        Assertions.assertThat(librarianDto.getLastName()).isNotNull();
    }


}