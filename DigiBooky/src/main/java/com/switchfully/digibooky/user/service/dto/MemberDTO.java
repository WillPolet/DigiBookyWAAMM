package com.switchfully.digibooky.user.service.dto;

import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MemberDTO {

    private String id;
    private String email;
    private String lastname;
    private String firstname;
    private Address address;

    public MemberDTO(String id, String email, String lastname, String firstname, Address address) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDTO memberDTO = (MemberDTO) o;
        return Objects.equals(id, memberDTO.id) && Objects.equals(email, memberDTO.email) && Objects.equals(lastname, memberDTO.lastname) && Objects.equals(firstname, memberDTO.firstname) && Objects.equals(address, memberDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, lastname, firstname, address);
    }
}
