package com.switchfully.digibooky.book.service.utility;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchUtilityTest {

    private static final Author AUTHOR1 = new Author("firstname1", "last1name1");
    private static final Author AUTHOR2 = new Author("firstname2", "lastname2");
    private static final Author AUTHOR3 = new Author("3firstname3", "lastname3");
    private static final Book BOOK1 = new Book("is1bn1", "title1", "summary", true, false, AUTHOR1);
    private static final Book BOOK2 = new Book("isbn2", "title2super", "summary", true, false, AUTHOR2);
    private static final Book BOOK3 = new Book("isbn3", "3title", "summary", true, false, AUTHOR3);
    private static final List<Book> BOOKS = List.of(BOOK1, BOOK2, BOOK3);

    @Test
    void givenTitle_whenSearchByTitle_thenReturnAuthor() {
        Assertions.assertThat(SearchUtility.getBooksByTitleWithWildcard(BOOKS, "title*")).containsExactlyInAnyOrder(BOOK1, BOOK2);
    }

    @Test
    void givenIsbn_whenSearchByIsbn_thenReturnAuthor() {
        Assertions.assertThat(SearchUtility.getBooksByIsbnWithWildcard(BOOKS, "isbn*")).containsExactlyInAnyOrder(BOOK2, BOOK3);
    }

    @Test
    void givenAuthorFirstname_whenSearchByAuthorFirstname_thenReturnAuthor() {
        Assertions.assertThat(SearchUtility.getBooksByFirstnameWithWildcard(BOOKS, "firstname*")).containsExactlyInAnyOrder(BOOK1, BOOK2);
    }

    @Test
    void givenAuthorLastname_whenSearchByAuthorLastname_thenReturnAuthor() {
        Assertions.assertThat(SearchUtility.getBooksByLastnameWithWildcard(BOOKS, "lastname*")).containsExactlyInAnyOrder(BOOK2, BOOK3);
    }
}