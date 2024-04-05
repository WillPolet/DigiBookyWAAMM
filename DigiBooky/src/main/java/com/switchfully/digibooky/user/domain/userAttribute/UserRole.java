package com.switchfully.digibooky.user.domain.userAttribute;


import java.util.List;

public enum UserRole {
    ADMIN(
            List.of(RoleFeature.CREATE_NEW_ADMIN,
                    RoleFeature.CREATE_LIBRARIAN,
                    RoleFeature.VIEW_ALL)),
    LIBRARIAN(List.of(RoleFeature.MODERATE,
            RoleFeature.VIEW_ALL,
            RoleFeature.CREATE_BOOK,
            RoleFeature.UPDATE_BOOK,
            RoleFeature.DELETE_BOOK,
            RoleFeature.CHECK_ALL_RENTALS)),
    MEMBER(List.of(RoleFeature.RENT_BOOK));

    private List<RoleFeature> features;
    private UserRole(List<RoleFeature> features){
        this.features = features;
    }
}
