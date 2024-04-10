package com.switchfully.digibooky.book.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;

import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private final String id;
    private final String isbn;
    private final String title;
    private final String summary;
    private final Boolean available;
    private final Boolean lent;
    private final Author author;
    private final MemberDto lender;

    public BookDto(String id, String isbn, String title, String summary, Boolean available, Boolean lent, Author author, MemberDto lender) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.available = available;
        this.lent = lent;
        this.author = author;
        this.lender = lender;
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

    public MemberDto getLender() {
        return lender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) && Objects.equals(isbn, bookDto.isbn) && Objects.equals(title, bookDto.title) && Objects.equals(summary, bookDto.summary) && Objects.equals(available, bookDto.available) && Objects.equals(lent, bookDto.lent) && Objects.equals(author, bookDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, summary, available, lent, author);
    }
}
