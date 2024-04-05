package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class BookRepository {
    private final HashMap<UUID, Book> books;

    public BookRepository() {
        books = new HashMap<>();
    }

    public String getIsbnById(UUID uuid){
        return books.get(uuid).getIsbn();
    }

    public void addBook(Book newBook) {
        if (!isIsbnAlreadyUsed(newBook.getIsbn())) {
            books.put(newBook.getUuid(), newBook);
        } else {
            throw new UniqueFieldAlreadyExistException("There is already a book with this isbn");
        }
    }

    public boolean isIsbnAlreadyUsed(String bookIsbn) {
        return books.values().stream().anyMatch(book -> book.getIsbn().equals(bookIsbn));
    }

    public void setBookToInaccessible(UUID id) {
        books.get(id).setAccessible(false);
    }

    public void updateBook(Book book) {

    }
}
