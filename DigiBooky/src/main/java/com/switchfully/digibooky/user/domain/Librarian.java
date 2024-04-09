package com.switchfully.digibooky.user.domain;

import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.UUID;

public class Librarian extends User {
    public Librarian(String email, String lastName, String firstName, String password) {
        super(UUID.randomUUID().toString(),
                email,
                lastName,
                firstName,
                password,
                List.of(RoleFeature.MODERATE,
                        RoleFeature.CREATE_BOOK,
                        RoleFeature.UPDATE_BOOK,
                        RoleFeature.DELETE_BOOK,
                        RoleFeature.CHECK_ALL_RENTALS));
    }
}
