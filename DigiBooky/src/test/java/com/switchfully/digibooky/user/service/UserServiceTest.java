package com.switchfully.digibooky.user.service;


import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.CreateMemberDTO;
import com.switchfully.digibooky.user.service.dto.MemberDTO;
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
    private static final CreateMemberDTO CREATE_MEMBER_DTO = new CreateMemberDTO(
            MEMBER.getEmail(),
            MEMBER.getLastname(),
            MEMBER.getFirstname(),
            MEMBER.getPassword(),
            MEMBER.getAddress(),
            MEMBER.getInss());
    private static final MemberDTO MEMBER_DTO = new MemberDTO(
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
        MemberDTO actualMemberDTO = userServiceMock.addMember(CREATE_MEMBER_DTO);
        // THEN
        Assertions.assertThat(actualMemberDTO.getId()).isEqualTo(MEMBER.getId());
    }
}

