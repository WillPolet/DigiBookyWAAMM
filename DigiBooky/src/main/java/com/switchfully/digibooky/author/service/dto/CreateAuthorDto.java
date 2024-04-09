package com.switchfully.digibooky.author.service.dto;

import jakarta.validation.constraints.NotEmpty;

public class CreateAuthorDto {
    private String firstname;
    @NotEmpty(message = "Lastname is required for author.")
    private String lastname;

    public CreateAuthorDto() {
        // JACKSON
    }

    public CreateAuthorDto(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public CreateAuthorDto(String lastname) {
        this("", lastname);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
