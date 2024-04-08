package com.switchfully.digibooky.user.service.dto.admin;

public class AdminDto {

    String email;
    String lastName;
    String firstName;
    String password;

    public AdminDto(String email, String lastName, String firstName, String password) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
