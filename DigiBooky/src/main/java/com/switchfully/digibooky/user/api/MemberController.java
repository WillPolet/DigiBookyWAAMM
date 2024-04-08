package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.user.service.UserService;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    private final UserService userService;
    public MemberController (UserService userService){
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public MemberDto createMember(@RequestBody @Valid CreateMemberDto createMemberDTO){
        // Example for authorization
        // @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, -> in the parameter
        //
        // then : authorizationService.hasFeature(RoleFeature.ADD_MEMBER, authorization);
        return userService.addMember(createMemberDTO);
    }
}
