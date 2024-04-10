package com.switchfully.digibooky.book.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.switchfully.digibooky.author.service.dto.UpdateAuthorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// E2E test crash if not applied
//  Id from the path variable in put request is passed as variable in Jackson
// since id is not in the object, the test crashes
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBookDto {
    @NotEmpty(message = "Isbn is required for a book update.")
    private String isbn;
    @NotEmpty(message = "Title is required for a book update.")
    private String title;
    @NotEmpty(message = "Summary is required for a book update.")
    private String summary;
    @Valid
    private UpdateAuthorDto author;

    public UpdateBookDto(String isbn, String title, String summary, UpdateAuthorDto author) {
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
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

    public UpdateAuthorDto getAuthor() {
        return author;
    }
}
