package com.switchfully.digibooky.user.service.dto.admin;

public class AdminDto {

    private String id;
    private String email;
    private String lastName;
    private String firstName;

    public AdminDto(String id, String email, String lastName, String firstName) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
