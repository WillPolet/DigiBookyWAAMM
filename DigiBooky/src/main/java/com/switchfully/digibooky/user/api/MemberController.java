package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.service.UserService;
import com.switchfully.digibooky.user.service.dto.CreateMemberDTO;
import com.switchfully.digibooky.user.service.dto.MemberDTO;
import org.springframework.http.HttpHeaders;
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
    public MemberDTO createMember(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody CreateMemberDTO createMemberDTO){

        return userService.addMember(createMemberDTO);
    }
}
