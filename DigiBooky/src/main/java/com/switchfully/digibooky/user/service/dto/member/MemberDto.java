package com.switchfully.digibooky.user.service.dto.member;

import com.switchfully.digibooky.user.domain.userAttribute.Address;

import java.util.Objects;

public class MemberDto {

    private String id;
    private String email;
    private String lastName;
    private String firstName;
    private Address address;

    public MemberDto(String id, String email, String lastName, String firstName, Address address) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDto memberDto = (MemberDto) o;
        return Objects.equals(id, memberDto.id) && Objects.equals(email, memberDto.email) && Objects.equals(lastName, memberDto.lastName) && Objects.equals(firstName, memberDto.firstName) && Objects.equals(address, memberDto.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, lastName, firstName, address);
    }
}
