package com.switchfully.digibooky.user.domain;

import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.UUID;

public class Admin extends User { public Admin(String email, String lastName, String firstName, String password) {
        super(UUID.randomUUID().toString(),
                email,
                lastName,
                firstName,
                password,
                List.of(RoleFeature.CREATE_NEW_ADMIN,
                        RoleFeature.CREATE_LIBRARIAN,
                        RoleFeature.VIEW_ALL));
    }
}
