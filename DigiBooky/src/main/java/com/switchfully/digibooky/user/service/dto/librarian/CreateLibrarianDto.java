package com.switchfully.digibooky.user.service.dto.librarian;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CreateLibrarianDto {

    @NotEmpty(message = "Impossible to create librarian without email.")
    @Email(message = "Email invalid. Provide name@domain.com")
    private String email;
    @NotEmpty(message = "Impossible to create librarian without last name.")
    private String lastname;
    @NotEmpty(message = "Impossible to create librarian without first name.")
    private String firstname;
    @NotEmpty(message = "Impossible to create librarian without password.")
    private String password;

    public CreateLibrarianDto(String email, String lastname, String firstname, String password) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
