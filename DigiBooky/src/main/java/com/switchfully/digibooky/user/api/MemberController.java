package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.user.service.UserService;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        return userService.addMember(createMemberDTO);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
