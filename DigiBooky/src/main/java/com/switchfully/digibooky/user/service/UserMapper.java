package com.switchfully.digibooky.user.service;

import com.switchfully.digibooky.exception.WrongEntityException;
import com.switchfully.digibooky.user.domain.Admin;
import com.switchfully.digibooky.user.domain.Librarian;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.service.dto.admin.AdminDto;
import com.switchfully.digibooky.user.service.dto.admin.CreateAdminDto;
import com.switchfully.digibooky.user.service.dto.librarian.CreateLibrarianDto;
import com.switchfully.digibooky.user.service.dto.librarian.LibrarianDto;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public Member toMember(CreateMemberDto createMemberDTO) {
        return new Member(
                createMemberDTO.getEmail(),
                createMemberDTO.getLastname(),
                createMemberDTO.getFirstname(),
                createMemberDTO.getPassword(),
                createMemberDTO.getAddress(),
                createMemberDTO.getInss()
        );
    }

    public MemberDto toMemberDto(User savedUser) {
        if (!(savedUser instanceof Member)) {
            throw new WrongEntityException("Error server : User is not a member");
        }
        Member memberToConvert = (Member) savedUser;

        return new MemberDto(
                memberToConvert.getId(),
                memberToConvert.getEmail(),
                memberToConvert.getLastname(),
                memberToConvert.getFirstname(),
                memberToConvert.getAddress());
    }

    public List<MemberDto> toMemberDto(List<Member> savedUsers){
        return savedUsers.stream().map(this::toMemberDto).collect(Collectors.toList());
    }

    public LibrarianDto toLibrarianDto(User savedUser) {
        if (!(savedUser instanceof Librarian)) {
            throw new WrongEntityException("Error server : User is not a librarian");
        }

        Librarian librarianToConvert = (Librarian) savedUser;
        return new LibrarianDto(
                librarianToConvert.getId(),
                librarianToConvert.getEmail(),
                librarianToConvert.getLastname(),
                librarianToConvert.getFirstname());
    }

    public Librarian toLibrarian(CreateLibrarianDto librarianToCreate) {
        return new Librarian(
                librarianToCreate.getEmail(),
                librarianToCreate.getLastname(),
                librarianToCreate.getFirstname(),
                librarianToCreate.getPassword());
    }

    public AdminDto toAdminDto(User savedUser) {
        if (!(savedUser instanceof Admin)) {
            throw new WrongEntityException("Error server : User is not an admin");
        }

        Admin adminToConvert = (Admin) savedUser;
        return new AdminDto(
                adminToConvert.getId(),
                adminToConvert.getEmail(),
                adminToConvert.getLastname(),
                adminToConvert.getFirstname());
    }

    public Admin toAdmin(CreateAdminDto createAdminDto) {
        return new Admin(
                createAdminDto.getEmail(),
                createAdminDto.getLastname(),
                createAdminDto.getFirstname(),
                createAdminDto.getPassword());
    }
}

