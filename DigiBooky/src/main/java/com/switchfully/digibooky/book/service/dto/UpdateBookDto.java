package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.author.service.dto.UpdateAuthorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class UpdateBookDto {
    @NotEmpty
    private String isbn;
    private String title;
    private String summary;
    private Boolean isAccessible;
    private Boolean isRented;
    @Valid
    private UpdateAuthorDto author;

    public UpdateBookDto() {
        // JACKSON
    }

    public UpdateBookDto(String isbn,String title, String summary, Boolean isAccessible, Boolean isRented, UpdateAuthorDto author) {
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.isAccessible = isAccessible;
        this.isRented = isRented;
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

    public Boolean getAccessible() {
        return isAccessible;
    }

    public Boolean getRented() {
        return isRented;
    }

    public UpdateAuthorDto getAuthor() {
        return author;
    }
}
