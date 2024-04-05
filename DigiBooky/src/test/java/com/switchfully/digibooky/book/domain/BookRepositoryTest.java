package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.author.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookRepositoryTest {

    private static final Author AUTHOR1 = new Author();
    private static final Book BOOK1 = new Book("is1bn1", "title1", "summary", true, false, AUTHOR1);
    private static final Book BOOK2 = new Book("isbn2", "title2super", "summary", true, false, AUTHOR1);
    private static final Book BOOK3 = new Book("isbn3", "3title", "summary", true, false, AUTHOR1);
    private static final List<Book> BOOKS = List.of(BOOK1, BOOK2, BOOK3);

    private final BookRepository bookRepository = new BookRepository();

    @Test
    void givenTitle_whenSearchBookWithTitleNotExisting_thenReturn0Book() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByTitleWithWildcard("bla")).contains();
    }

    @Test
    void givenTitle_whenSearchBookWithExactTitle_thenReturnTheGoodBook() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByTitleWithWildcard("title1")).contains(BOOK1);
    }

    @Test
    void givenTitle_whenSearchBookWithWildcardTitle_thenReturnMatchedBook() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByTitleWithWildcard("title*")).contains(BOOK1, BOOK2);
    }

    @Test
    void givenISBN_whenSearchBookWithTitleNotExisting_thenReturn0Book() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByIsbnWithWildcard("bla")).contains();
    }

    @Test
    void givenISBN_whenSearchBookWithExactTitle_thenReturnTheGoodBook() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByIsbnWithWildcard("is1bn")).contains(BOOK1);
    }

    @Test
    void givenISBN_whenSearchBookWithWildcardTitle_thenReturnMatchedBook() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByIsbnWithWildcard("isbn*")).contains(BOOK2, BOOK3);
    }

    @Test
    void givenTitleAndISBN_whenSearchBookNotExisting_thenReturn0Book() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByTitleAndIsbnWithWildcard("bla", "bla")).contains();
    }

    @Test
    void givenTitleAndISBN_whenSearchBookWithExistingTitleButNotExistingIsbn_thenReturn0Book() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByTitleAndIsbnWithWildcard("title1", "bla")).contains();
    }

    @Test
    void givenTitleAndISBN_whenSearchBookWithExistingIsbnButNotExistingTitle_thenReturn0Book() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByTitleAndIsbnWithWildcard("bla", "isbn2")).contains();
    }

    @Test
    void givenTitleAndISBN_whenSearchBookTitleAndIsbn_thenReturnBooks() {
        addBooksToRepository();
        Assertions.assertThat(bookRepository.searchBooksByTitleAndIsbnWithWildcard("*title", "isbn*")).contains(BOOK3);
    }

    private void addBooksToRepository() {
        bookRepository.addBook(BOOK1);
        bookRepository.addBook(BOOK2);
        bookRepository.addBook(BOOK3);
    }
}
