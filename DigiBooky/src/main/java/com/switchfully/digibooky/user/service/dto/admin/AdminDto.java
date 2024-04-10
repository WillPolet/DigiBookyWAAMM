package com.switchfully.digibooky.user.service.dto.admin;

public class AdminDto {

    private String id;
    private String email;
    private String lastname;
    private String firstname;

    public AdminDto(String id, String email, String lastname, String firstname) {
        this.id = id;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }
}
