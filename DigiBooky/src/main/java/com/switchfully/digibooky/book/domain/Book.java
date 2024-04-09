package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.author.domain.Author;

import java.util.UUID;

public class Book {
    private final String id;
    private String isbn;
    private String title;
    private String summary;
    private Boolean isAccessible;
    private Boolean isRented;
    private Author author;

    public Book(String isbn, String title, String summary, Boolean isAccessible, Boolean isRented, Author author) {
        this(UUID.randomUUID().toString(), isbn, title, summary, isAccessible, isRented, author );
    }

    public Book(String id, String isbn, String title, String summary, Boolean isAccessible, Boolean isRented, Author author) {
        this.id = id;
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

    public void setRented(Boolean rented) {
        isRented = rented;
    }
}
