package com.switchfully.digibooky.user.service;

import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public MemberDto addMember(CreateMemberDto createMemberDTO) {
        User savedUser = userRepository.addUser(userMapper.toMember(createMemberDTO));
        return userMapper.toMemberDto(savedUser);
    }
}
