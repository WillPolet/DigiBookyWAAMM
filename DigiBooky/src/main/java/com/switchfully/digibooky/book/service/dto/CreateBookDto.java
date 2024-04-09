package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateBookDto {
        @NotEmpty(message = "isbn is required for creation.")
        private String isbn;
        @NotEmpty(message = "Title is required for creation.")
        private String title;
        private String summary;
        private Boolean available;
        private Boolean lent;
        @Valid
        private CreateAuthorDto author;

    public CreateBookDto() {
        // JACKSON
    }

    public CreateBookDto(String isbn, String title, String summary, CreateAuthorDto author) {
            this.isbn = isbn;
            this.title = title;
            this.summary = summary;
            this.available = true;
            this.lent = false;
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

    public Boolean isAvailable() {
        return available;
    }

    public Boolean isLent() {
        return lent;
    }

    public CreateAuthorDto getAuthor() {
        return author;
    }
}
