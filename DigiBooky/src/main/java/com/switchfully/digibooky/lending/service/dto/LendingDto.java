package com.switchfully.digibooky.lending.service.dto;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.service.dto.MemberDTO;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class LendingDto {
    private String id;
    private MemberDTO member;
    private BookDto book;
    private String lendingDate;
    private String returningDate;

    public LendingDto(String id, MemberDTO member, BookDto book, String lendingDate, String returningDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.lendingDate = lendingDate;
        this.returningDate = returningDate;
    }

    public String getId() {
        return id;
    }

    public MemberDTO getMember() {
        return member;
    }

    public BookDto getBook() {
        return book;
    }

    public String getLendingDate() {
        return lendingDate;
    }

    public String getReturningDate() {
        return returningDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LendingDto that = (LendingDto) o;
        return Objects.equals(id, that.id) && Objects.equals(member, that.member) && Objects.equals(book, that.book) && Objects.equals(lendingDate, that.lendingDate) && Objects.equals(returningDate, that.returningDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, book, lendingDate, returningDate);
    }
}
