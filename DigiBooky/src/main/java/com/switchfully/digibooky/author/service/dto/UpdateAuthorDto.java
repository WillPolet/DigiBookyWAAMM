package com.switchfully.digibooky.author.service.dto;

import jakarta.validation.constraints.NotEmpty;

public class UpdateAuthorDto {
    @NotEmpty(message = "Author's Id is required to update an author")
    private final String id;
    private final String firstname;
    @NotEmpty(message = "Lastname is required to update an author")
    private final String lastname;

    public UpdateAuthorDto(String id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
