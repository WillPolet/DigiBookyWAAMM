package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.author.domain.Author;

import java.util.Objects;
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

    public Boolean getIsAccessible() {
        return isAccessible;
    }

    public Boolean getIsRented() {
        return isRented;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(uuid, bookDto.uuid) && Objects.equals(isbn, bookDto.isbn) && Objects.equals(title, bookDto.title) && Objects.equals(summary, bookDto.summary) && Objects.equals(isAccessible, bookDto.isAccessible) && Objects.equals(isRented, bookDto.isRented) && Objects.equals(author, bookDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, isbn, title, summary, isAccessible, isRented, author);
    }
}
