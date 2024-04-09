package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.authorization.service.AuthorizationService;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.service.LendingService;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    private final UserService userService;
    private final LendingService lendingService;
    private final AuthorizationService authorizationService;
    public MemberController (UserService userService, AuthorizationService authorizationService, LendingService lendingService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.lendingService = lendingService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MemberDto createMember(@RequestBody @Valid CreateMemberDto createMemberDTO){
        return userService.addMember(createMemberDTO);
    }

    @GetMapping("/{id}/lentbooks")
    public List<LendingDto> getLentBooksByMember(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        authorizationService.hasFeature(RoleFeature.CHECK_ALL_RENTALS, authorization);
        return lendingService.getLendingsByMember(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Collection<MemberDto> getAllMembers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        authorizationService.hasFeature(RoleFeature.VIEW_ALL, authorization);
        return userService.getAllMembers();
    }

}
