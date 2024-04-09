package com.switchfully.digibooky.user.domain;

import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Member extends User {
    private Address address;
    private String inss;
    //TODO issue with calling constructor ? Validation for non null fields not done
    public Member(String email, String lastName, String firstName, String password, Address address, String inss){
        super(UUID.randomUUID().toString(),
                email,
                lastName,
                firstName,
                password,
                List.of(RoleFeature.RENT_BOOK));
        this.address = address;
        this.inss = inss;
    }

    public Member(String email, String lastName, String password, Address address, String inss){
        super(UUID.randomUUID().toString(),
                email,
                lastName,
                "",
                password,
                List.of(RoleFeature.RENT_BOOK));
        this.address = address;
        this.inss = inss;
    }

    public Address getAddress() {
        return address;
    }

    public String getInss() {
        return inss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return Objects.equals(getAddress(), member.getAddress()) && Objects.equals(getInss(), member.getInss());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getInss());
    }
}
