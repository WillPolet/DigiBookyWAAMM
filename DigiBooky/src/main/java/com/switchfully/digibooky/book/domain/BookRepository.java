package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final HashMap<String, Book> books;

    public BookRepository() {
        books = new HashMap<>();
    }

    public String getIsbnById(String uuid){
        return books.get(uuid).getIsbn();
    }

    public void addBook(Book newBook) {
        if (!isIsbnAlreadyUsed(newBook.getIsbn())) {
            books.put(newBook.getUuid().toString(), newBook);
        } else {
            throw new UniqueFieldAlreadyExistException("There is already a book with this isbn");
        }
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

    public List<Book> getAllBooks() {
        return books.values().stream()
                .filter(Book::getAccessible)
                .collect(Collectors.toList());
    }
}
