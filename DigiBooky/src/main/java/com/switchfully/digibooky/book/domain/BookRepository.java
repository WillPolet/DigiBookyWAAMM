package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class BookRepository {
    private final HashMap<String, Book> books;

    public BookRepository() {
        books = new HashMap<>();
    }

    public String getIsbnById(String id){
        return books.get(id).getIsbn();
    }

    public Book addBook(Book newBook) {
        if (!isIsbnAlreadyUsed(newBook.getIsbn())) {
            books.put(newBook.getId(), newBook);
        } else {
            throw new UniqueFieldAlreadyExistException("There is already a book with this isbn");
        }
        return newBook;
    }

    public boolean isIsbnAlreadyUsed(String bookIsbn) {
        return books.values().stream().anyMatch(book -> book.getIsbn().equals(bookIsbn));
    }

    public void setBookToInaccessible(String id) {
        books.get(id).setAccessible(false);
    }

    public void updateBook(Book book) {
    }

    public Collection<Book> getBooks() {
        return books.values();
    }
}
