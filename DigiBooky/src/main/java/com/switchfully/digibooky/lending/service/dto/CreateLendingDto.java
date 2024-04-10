package com.switchfully.digibooky.lending.service.dto;

import jakarta.validation.constraints.NotEmpty;

public class CreateLendingDto {
    @NotEmpty(message = "Isbn required to lend a book.")
    private String bookIsbn;
    @NotEmpty(message = "User id required to lend a book.")
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
