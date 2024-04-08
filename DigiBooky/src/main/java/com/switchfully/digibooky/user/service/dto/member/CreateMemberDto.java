package com.switchfully.digibooky.user.service.dto.member;

import com.switchfully.digibooky.user.domain.userAttribute.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class CreateMemberDto {
    @NotNull(message = "Email cannot be null.")
    @Email(message = "Invalid email address. Please use email@domain.com.")
    private String email;
    @NotNull(message = "Last name cannot be null.")
    private String lastname;
    private String firstname;
    private String password;
    @Valid
    private Address address;
    private String inss;

    public CreateMemberDto() {}

    public CreateMemberDto(String email, String lastname, String firstname, String password, Address address, String inss) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.address = address;
        this.inss = inss;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public String getInss() {
        return inss;
    }
}
