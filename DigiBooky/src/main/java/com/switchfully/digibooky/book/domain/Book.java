package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.author.domain.Author;

import java.util.UUID;

public class Book {
    private UUID uuid;
    private String isbn;
    private String title;
    private String summary;
    private Boolean isAccessible;
    private Boolean isRanted;
    private Author author;

    public Book(String isbn, String title, String summary, Boolean isAccessible, Boolean isRanted, Author author) {
        this.uuid = UUID.randomUUID();
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.isAccessible = isAccessible;
        this.isRanted = isRanted;
        this.author = author;
    }

    public Book(String isbn, String title, Boolean isAccessible, Boolean isRanted, Author author) {
        this.uuid = UUID.randomUUID();
        this.isbn = isbn;
        this.title = title;
        this.summary = "";
        this.isAccessible = isAccessible;
        this.isRanted = isRanted;
        this.author = author;
    }

}
