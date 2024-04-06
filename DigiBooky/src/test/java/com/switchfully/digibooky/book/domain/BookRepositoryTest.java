package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.author.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BookRepositoryTest {

    private static final Author AUTHOR1 = new Author();
    private static final Book BOOK1 = new Book("is1bn1", "title1", "summary", true, false, AUTHOR1);
    private static final Book BOOK2 = new Book("isbn2", "title2super", "summary", true, false, AUTHOR1);
    private static final Book BOOK3 = new Book("isbn3", "3title", "summary", true, false, AUTHOR1);

    private final BookRepository bookRepository = new BookRepository();

    @Test
    void givenExistingBook_whenGetBooks_thenReturnAllBooks() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.getBooks()).containsExactlyInAnyOrder(BOOK1, BOOK2, BOOK3);
    }

    private void addBooksToRepository() {
        bookRepository.addBook(BOOK1);
        bookRepository.addBook(BOOK2);
        bookRepository.addBook(BOOK3);
    }
}
