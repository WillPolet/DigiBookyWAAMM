package com.switchfully.digibooky.user.service;

import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.springframework.stereotype.Component;

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
            throw new IllegalArgumentException("Implement me");
        }
        Member memberToConvert = (Member) savedUser;

        return new MemberDto(
                memberToConvert.getId(),
                memberToConvert.getEmail(),
                memberToConvert.getLastname(),
                memberToConvert.getFirstname(),
                memberToConvert.getAddress());
    }
}

