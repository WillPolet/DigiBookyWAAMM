package com.switchfully.digibooky.user.service.dto.admin;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CreateAdminDto {

    @NotEmpty(message = "Impossible to create admin without email.")
    @Email(message = "Email invalid. Provide name@domain.com")
    private String email;
    @NotEmpty(message = "Impossible to create admin without last name.")
    private String lastName;
    @NotEmpty(message = "Impossible to create admin without first name.")
    private String firstName;
    @NotEmpty(message = "Impossible to create admin without password.")
    private String password;

    public CreateAdminDto(String email, String lastName, String firstName, String password) {
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
