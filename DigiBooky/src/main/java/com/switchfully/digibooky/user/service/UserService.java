package com.switchfully.digibooky.user.service;

import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.service.dto.CreateMemberDTO;
import com.switchfully.digibooky.user.service.dto.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public MemberDTO addMember(CreateMemberDTO createMemberDTO) {
        User savedUser = userRepository.addUser(userMapper.toMember(createMemberDTO));
        return userMapper.toMemberDto(savedUser);
    }
}
