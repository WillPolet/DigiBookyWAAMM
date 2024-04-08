package com.switchfully.digibooky.user.service.dto.librarian;

public class LibrarianDto {
    private String id;
    private String email;
    private String lastName;
    private String firstName;

    public LibrarianDto(String id, String email, String lastName, String firstName) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
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
    public String getId() {
        return id;
    }
}
