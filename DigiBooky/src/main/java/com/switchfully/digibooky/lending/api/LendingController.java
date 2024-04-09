package com.switchfully.digibooky.lending.api;

import com.switchfully.digibooky.authorization.service.AuthorizationService;
import com.switchfully.digibooky.exception.ExceptionDto;
import com.switchfully.digibooky.lending.service.LendingService;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        authorizationService.isUserSameAsAuthorizationUser(createLendingDto.getUserId(), authorization);
        return lendingService.createLending(createLendingDto);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        authorizationService.hasFeature(RoleFeature.RENT_BOOK, authorization);
        authorizationService.isLendingOwnedByAuthorizationUser(id, authorization);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(lendingService.returnBook(id));
    }

    @GetMapping("/overdue")
    public List<LendingDto> getOverdueLendings(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        authorizationService.hasFeature(RoleFeature.CHECK_ALL_RENTALS, authorization);
        return lendingService.getOverdueLendings();
    }
}
