package com.switchfully.digibooky.user.service;


import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final Address ADDRESS = new Address("rue Ferra", "3", "75013", "Paris");
    private static final Member MEMBER = new Member("m.b@hotmail.fr", "lastName", "password",  ADDRESS, "33A");
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
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserMapper userMapperMock;
    @InjectMocks
    private UserService userServiceMock;

    @Test
    @DisplayName("Creating a member should return a new member DTO")
    void creatingUser_GivenCorrectData_WillReturnUserDTO(){
        //GIVEN
        Mockito.when(userRepositoryMock.addUser(MEMBER)).thenReturn(MEMBER);
        Mockito.when(userMapperMock.toMember(CREATE_MEMBER_DTO))
                .thenReturn(MEMBER);
        Mockito.when(userMapperMock.toMemberDto(MEMBER))
                .thenReturn(MEMBER_DTO);
        //WHEN
        MemberDto actualMemberDto = userServiceMock.addMember(CREATE_MEMBER_DTO);
        // THEN
        Assertions.assertThat(actualMemberDto.getId()).isEqualTo(MEMBER.getId());
    }
}

