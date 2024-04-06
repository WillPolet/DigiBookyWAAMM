package com.switchfully.digibooky.author.service.dto;

import java.util.Objects;
import java.util.UUID;

public class AuthorDto {
    private final String id;
    private final String firstname;
    private final String lastname;

    public AuthorDto(String id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(id, authorDto.id) && Objects.equals(firstname, authorDto.firstname) && Objects.equals(lastname, authorDto.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname);
    }
}
