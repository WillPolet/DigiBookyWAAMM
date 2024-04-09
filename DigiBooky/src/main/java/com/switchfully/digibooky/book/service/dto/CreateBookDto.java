package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateBookDto {
        @NotEmpty
        private String isbn;
        @NotEmpty
        private String title;
        private String summary;
        private Boolean isAccessible;
        private Boolean isRented;
        @Valid
        private CreateAuthorDto author;

    public CreateBookDto() {
        // JACKSON
    }

    public CreateBookDto(String isbn, String title, String summary, CreateAuthorDto author) {
            this.isbn = isbn;
            this.title = title;
            this.summary = summary;
            this.isAccessible = true;
            this.isRented = false;
            this.author = author;
    }


    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public Boolean getIsAccessible() {
        return isAccessible;
    }

    public Boolean getIsRented() {
        return isRented;
    }

    public CreateAuthorDto getAuthor() {
        return author;
    }
}
