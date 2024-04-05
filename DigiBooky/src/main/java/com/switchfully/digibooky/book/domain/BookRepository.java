package com.switchfully.digibooky.book.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class BookRepository {
    private HashMap<String, Book> Books;

    public void addBook() {

    }
    // Dans le add vérifier si l'isbn et l'id sont unique
}
