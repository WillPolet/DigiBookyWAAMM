package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.author.service.dto.UpdateAuthorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class UpdateBookDto {
    @NotEmpty(message = "Isbn is required for a book update.")
    private String isbn;
    @NotEmpty(message = "Title is required for a book update.")

    private String title;
    @NotEmpty(message = "Summary is required for a book update.")

    private String summary;
    @NotEmpty(message = "Availability is required for a book update.")
    private Boolean available;
    @NotEmpty(message = "Lent status is required for a book update.")
    private Boolean lent;
    @Valid
    private UpdateAuthorDto author;

    public UpdateBookDto() {
        // JACKSON
    }

    public UpdateBookDto(String isbn,String title, String summary, Boolean available, Boolean lent, UpdateAuthorDto author) {
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.available = available;
        this.lent = lent;
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

    public UpdateAuthorDto getAuthor() {
        return author;
    }
}
