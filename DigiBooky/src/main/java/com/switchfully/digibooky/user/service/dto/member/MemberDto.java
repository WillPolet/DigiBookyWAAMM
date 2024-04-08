package com.switchfully.digibooky.user.service.dto.member;

import com.switchfully.digibooky.user.domain.userAttribute.Address;

import java.util.UUID;

public class MemberDto {

    private String id;
    private String email;
    private String lastname;    private String firstname;

    private Address address;

    public MemberDto(String id, String email, String lastname, String firstname, Address address) {
        this.id = id;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
    }

    public String getId() {
        return id;
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

    public Address getAddress() {
        return address;
    }
}
