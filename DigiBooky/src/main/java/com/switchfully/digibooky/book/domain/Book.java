package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.author.domain.Author;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String id;
    private String isbn;
    private String title;
    private String summary;
    private Boolean available;
    private Boolean lent;
    private Author author;

    public Book(String isbn, String title, String summary, Author author) {
        this(UUID.randomUUID().toString(), isbn, title, summary, true, false, author );
    }

    public Book(String id, String isbn, String title, String summary, Boolean available, Boolean lent, Author author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.available = available;
        this.lent = lent;
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

    public Boolean isAvailable() {
        return available;
    }

    public Boolean isLent() {
        return lent;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAccessible(Boolean accessible) {
        available = accessible;
    }

    public void setLent(Boolean lent) {
        this.lent = lent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(summary, book.summary) && Objects.equals(available, book.available) && Objects.equals(lent, book.lent) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title);
    }
}
