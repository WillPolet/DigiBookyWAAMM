package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.authorization.service.AuthorizationService;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;
import com.switchfully.digibooky.user.service.UserService;
import com.switchfully.digibooky.user.service.dto.admin.AdminDto;
import com.switchfully.digibooky.user.service.dto.admin.CreateAdminDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/admins")
public class AdminController {
    private final UserService userService;
    private AuthorizationService authorizationService;
    public AdminController (UserService userService, AuthorizationService authorizationService){
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AdminDto createAdmin(@RequestBody @Valid CreateAdminDto createAdminDTO,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        authorizationService.hasFeature(RoleFeature.CREATE_NEW_ADMIN, authorization);
        return userService.addAdmin(createAdminDTO);
    }
}
