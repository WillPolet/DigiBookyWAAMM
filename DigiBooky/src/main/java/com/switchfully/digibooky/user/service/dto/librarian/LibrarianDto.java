package com.switchfully.digibooky.user.service.dto.librarian;

public class LibrarianDto {
    private String id;
    private String email;
    private String lastname;
    private String firstname;

    public LibrarianDto(String id, String email, String lastname, String firstname) {
        this.id = id;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
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
    public String getId() {
        return id;
    }
}
