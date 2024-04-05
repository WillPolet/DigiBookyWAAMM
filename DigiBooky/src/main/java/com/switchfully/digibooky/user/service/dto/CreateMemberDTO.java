package com.switchfully.digibooky.user.service.dto;

import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.UUID;

public class CreateMemberDTO {
    private String email;
    private String lastname;
    private String firstname;
    private String password;
    private Address address;
    private String inss;

    public CreateMemberDTO(String email, String lastname, String firstname, String password, Address address, String inss) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.address = address;
        this.inss = inss;
    }

    public CreateMemberDTO(String email, String lastname, String password, Address address, String inss) {
        this(email, lastname, "", password, address, inss);
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
