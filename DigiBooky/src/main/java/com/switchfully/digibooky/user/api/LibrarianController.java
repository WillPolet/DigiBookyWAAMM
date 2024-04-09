package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.authorization.service.AuthorizationService;
import com.switchfully.digibooky.user.domain.Librarian;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;
import com.switchfully.digibooky.user.service.UserService;
import com.switchfully.digibooky.user.service.dto.librarian.CreateLibrarianDto;
import com.switchfully.digibooky.user.service.dto.librarian.LibrarianDto;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/librarians")
public class LibrarianController {
    private final UserService userService;
    private AuthorizationService authorizationService;
    public LibrarianController (UserService userService, AuthorizationService authorizationService){
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LibrarianDto createLibrarian(@RequestBody @Valid CreateLibrarianDto createLibrarianDTO,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        authorizationService.hasFeature(RoleFeature.CREATE_LIBRARIAN, authorization);
        return userService.addLibrarian(createLibrarianDTO);
    }
}
