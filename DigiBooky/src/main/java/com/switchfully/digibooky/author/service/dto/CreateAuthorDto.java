package com.switchfully.digibooky.author.service.dto;

public class CreateAuthorDto {
    private String firstname;
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
