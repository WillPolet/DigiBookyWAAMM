package com.switchfully.digibooky.user.service.dto;

import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.UUID;

public class MemberDTO {

    private UUID id;
    private String email;
    private String lastname;
    private String firstname;
    private Address address;

    public MemberDTO(UUID id, String email, String lastname, String firstname, Address address) {
        this.id = id;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
    }

    public UUID getId() {
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
