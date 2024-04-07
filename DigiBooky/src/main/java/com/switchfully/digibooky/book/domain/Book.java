package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.author.domain.Author;

import java.util.UUID;

public class Book {
    private final String id;
    private final String isbn;
    private final String title;
    private final String summary;
    private Boolean isAccessible;
    private Boolean isRented;
    private final Author author;

    public Book(String isbn, String title, String summary, Boolean isAccessible, Boolean isRented, Author author) {
        this.id = UUID.randomUUID().toString();
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.isAccessible = isAccessible;
        this.isRented = isRented;
        this.author = author;
    }

    public String getId() {
        return id;
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

    public void setAccessible(Boolean accessible) {
        isAccessible = accessible;
    }
}
