package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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

    public List<Book> searchBooksByTitleWithWildcard(String title) {
        return getBooksByTitleWithWildcard(books.values(), title);
    }

    public List<Book> searchBooksByIsbnWithWildcard(String isbn) {
        return getBooksByIsbnWithWildcard(books.values(), isbn);
    }

    public List<Book> searchBooksByTitleAndIsbnWithWildcard(String title, String isbn) {
        return getBooksByTitleWithWildcard(
                getBooksByIsbnWithWildcard(books.values(), isbn),
                title
        );
    }

    private List<Book> getBooksByTitleWithWildcard(Collection<Book> books, String title) {
        Pattern pattern = getPattern(title);
        return books.stream()
                .filter(b -> pattern.matcher(b.getTitle()).find())
                .toList();
    }

    private List<Book> getBooksByIsbnWithWildcard(Collection<Book> books, String isbn) {
        Pattern pattern = getPattern(isbn);
        return books.stream()
                .filter(b -> pattern.matcher(b.getIsbn()).find())
                .toList();
    }

    private Pattern getPattern(String regex) {
        return Pattern.compile("^" + regex);
    }
}
