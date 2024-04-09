package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.service.dto.UpdateBookDto;
import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public void updateBook(Book book, String id) {
        books.put(id, book);
    }

    public boolean doesIdExist(String id){
        return books.containsKey(id);
    }

    public Book getBookById(String id) {
        return books.get(id);
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return books.values().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst();
    }

    public List<Book> getAllBooks() {
        return books.values().stream()
                .filter(Book::getAccessible)
                .collect(Collectors.toList());
    }
}
