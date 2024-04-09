package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.authorization.service.AuthorizationService;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;
import com.switchfully.digibooky.user.service.UserService;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    private final UserService userService;
    private AuthorizationService authorizationService;
    public MemberController (UserService userService, AuthorizationService authorizationService){
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MemberDto createMember(@RequestBody @Valid CreateMemberDto createMemberDTO){
        return userService.addMember(createMemberDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Collection<MemberDto> getAllMembers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        authorizationService.hasFeature(RoleFeature.VIEW_ALL, authorization);
        return userService.getAllMembers();
    }

}
