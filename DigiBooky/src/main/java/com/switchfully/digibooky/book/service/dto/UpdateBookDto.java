package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.author.domain.Author;

import java.util.UUID;

public class UpdateBookDto {
    private UUID uuid;
    private String title;
    private String summary;
    private Boolean isAccessible;
    private Boolean isRented;
    private Author author;

    public UpdateBookDto() {
        // JACKSON
    }

    public UpdateBookDto(String title, String summary, Boolean isAccessible, Boolean isRented, Author author) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.summary = summary;
        this.isAccessible = isAccessible;
        this.isRented = isRented;
        this.author = author;
    }

    public UpdateBookDto(String title, Boolean isAccessible, Boolean isRented, Author author) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.summary = "";
        this.isAccessible = isAccessible;
        this.isRented = isRented;
        this.author = author;
    }

    public UUID getUuid() {
        return uuid;
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
