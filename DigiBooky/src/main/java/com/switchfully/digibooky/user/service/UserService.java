package com.switchfully.digibooky.user.service;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public MemberDto addMember(CreateMemberDto createMemberDTO) throws UniqueFieldAlreadyExistException {
        String inss = createMemberDTO.getInss();
        Optional<Member> isUserInRepo = userRepository.getMemberByInss(inss);

        if (isUserInRepo.isPresent()){
            throw new UniqueFieldAlreadyExistException("A user with this INSS is already created.");
        }

        User savedUser = userRepository.addUser(userMapper.toMember(createMemberDTO));
        return userMapper.toMemberDto(savedUser);
    }
}
