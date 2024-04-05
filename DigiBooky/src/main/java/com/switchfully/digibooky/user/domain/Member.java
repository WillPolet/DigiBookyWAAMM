package com.switchfully.digibooky.user.domain;

import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.UUID;

public class Member extends User {
    private Address address;
    private String inss;
    public Member(String email, String lastName, String firstName, String password, Address address, String inss){
        super(UUID.randomUUID(),
                email,
                lastName,
                firstName,
                password,
                List.of(RoleFeature.RENT_BOOK));
        this.address = address;
        this.inss = inss;
    }

    public Member(String email, String lastName, String password, Address address, String inss){
        super(UUID.randomUUID(),
                email,
                lastName,
                "",
                password,
                List.of(RoleFeature.RENT_BOOK));
        this.address = address;
        this.inss = inss;
    }
}
