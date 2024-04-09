package com.switchfully.digibooky.lending.service.dto;

public class CreateLendingDto {
    private String bookIsbn;
    private String userId;
    private String returningDate;

    public CreateLendingDto(String bookIsbn, String userId, String returningDate) {
        this.bookIsbn = bookIsbn;
        this.userId = userId;
        this.returningDate = returningDate;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getUserId() {
        return userId;
    }

    public String getReturningDate() {
        return returningDate;
    }
}
