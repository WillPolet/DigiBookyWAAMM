package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.author.domain.Author;

import java.util.UUID;

public class BookDto {
    private UUID uuid;
    private String isbn;
    private String title;
    private String summary;
    private Boolean isAccessible;
    private Boolean isRented;
    private Author author;

    public BookDto(UUID uuid, String isbn, String title, String summary, Boolean isAccessible, Boolean isRented, Author author) {
        this.uuid = uuid;
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.isAccessible = isAccessible;
        this.isRented = isRented;
        this.author = author;
    }

    public UUID getUuid() {
        return uuid;
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

    public Author getAuthor() {
        return author;
    }
}
