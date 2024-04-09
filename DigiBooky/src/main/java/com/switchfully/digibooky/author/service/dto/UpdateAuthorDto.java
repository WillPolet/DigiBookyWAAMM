package com.switchfully.digibooky.author.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateAuthorDto {
    private final String firstname;
    @NotEmpty(message = "Lastname is required to update an author")
    private final String lastname;

    public UpdateAuthorDto(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
