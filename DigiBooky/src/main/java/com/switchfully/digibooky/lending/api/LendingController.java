package com.switchfully.digibooky.lending.api;

import com.switchfully.digibooky.authorization.service.AuthorizationService;
import com.switchfully.digibooky.lending.service.LendingService;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lendings")
public class LendingController {
    private LendingService lendingService;
    private AuthorizationService authorizationService;

    public LendingController(LendingService lendingService, AuthorizationService authorizationService) {
        this.lendingService = lendingService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LendingDto createLending(@RequestBody CreateLendingDto createLendingDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        authorizationService.hasFeature(RoleFeature.RENT_BOOK, authorization);
        authorizationService.isSameUser(createLendingDto.getUserId(), authorization);
        return lendingService.createLending(createLendingDto);
    }
}
