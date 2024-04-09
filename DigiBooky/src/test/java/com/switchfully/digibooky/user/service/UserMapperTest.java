package com.switchfully.digibooky.user.service;

import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private static final Address ADDRESS = new Address("rue Ferra", "3", "75013", "Paris");

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

    @Nested
    @DisplayName("UserMapper Test")
    class UserMapperTesting {
        UserMapper myMapper = new UserMapper();

        @Test
        @DisplayName("CreateUserDTO to Member : return Member")
        void toMember_whenGivenCreateUserDTO_thenReturnMember() {
            //GIVEN

            //WHEN
            Member actualMember = myMapper.toMember(CREATE_MEMBER_DTO);

            //THEN
            Assertions.assertEquals(MEMBER, actualMember);
        }

        @Test
        @DisplayName("User to MemberDTO : return MemberDTO")
        void toMemberDto_whenUseGiven_thenReturnMemberDTO() {
            //GIVEN

            //WHEN
            MemberDto actualMemberDto = myMapper.toMemberDto(MEMBER);

            //THEN
            Assertions.assertEquals(MEMBER_DTO, actualMemberDto);
        }

    }
}