package com.switchfully.digibooky.lending.domain;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.user.domain.Member;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Lending {
    public static final int DEFAULT_LENDING_DURATION = 3;
    private String id;
    private Member member;
    private Book book;
    private LocalDate lendingDate;
    private LocalDate returningDate;
    private boolean active;

    public Lending(Member member, Book book) {
        initLending(member, book);
        this.returningDate = lendingDate.plusWeeks(DEFAULT_LENDING_DURATION);
    }
    public Lending(Member member, Book book, LocalDate returningDate) {
        initLending(member, book);
        this.returningDate = returningDate;
    }
    private void initLending(Member member, Book book) {
        this.id = UUID.randomUUID().toString();
        this.member = member;
        this.book = book;
        this.book.setRented(true);
        this.lendingDate = LocalDate.now();
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public LocalDate getReturningDate() {
        return returningDate;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
        this.book.setRented(false);
    }

    public boolean isOverdue() {
        return returningDate.isBefore(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lending lending = (Lending) o;
        return Objects.equals(id, lending.id) && Objects.equals(member, lending.member) && Objects.equals(book, lending.book) && Objects.equals(lendingDate, lending.lendingDate) && Objects.equals(returningDate, lending.returningDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, book, lendingDate, returningDate);
    }
}
