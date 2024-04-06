package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.service.dto.BookDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static final Author AUTHOR1 = new Author();
    private static final Book BOOK1 = new Book("is1bn1", "title1", "summary", true, false, AUTHOR1);
    private static final Book BOOK2 = new Book("isbn2", "title2super", "summary", true, false, AUTHOR1);
    private static final Book BOOK3 = new Book("isbn3", "3title", "summary", true, false, AUTHOR1);
    private static final List<Book> BOOKS = List.of(BOOK1, BOOK2, BOOK3);
    private static final BookDto BOOK1_DTO = new BookDto(BOOK1.getUuid(), BOOK1.getIsbn(), BOOK1.getTitle(), BOOK1.getSummary(), BOOK1.getAccessible(), BOOK1.getRented(), BOOK1.getAuthor());
    private static final BookDto BOOK2_DTO = new BookDto(BOOK2.getUuid(), BOOK2.getIsbn(), BOOK2.getTitle(), BOOK2.getSummary(), BOOK2.getAccessible(), BOOK2.getRented(), BOOK2.getAuthor());
    private static final BookDto BOOK3_DTO = new BookDto(BOOK3.getUuid(), BOOK3.getIsbn(), BOOK3.getTitle(), BOOK3.getSummary(), BOOK3.getAccessible(), BOOK3.getRented(), BOOK3.getAuthor());

    @Mock
    BookMapper bookMapper;
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookService bookService;

    @Test
    void givenTitle_whenSearchBooksWithTitleNotExisting_thenReturn0Book() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooksByTitle("bla")).contains();
    }

    @Test
    void givenTitle_whenSearchBookWithExactTitle_thenReturnTheGoodBook() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK1))).thenReturn(List.of(BOOK1_DTO));
        Assertions.assertThat(bookService.searchBooksByTitle("title1")).contains(BOOK1_DTO);
    }

    @Test
    void givenTitle_whenSearchBookWithWildcardTitle_thenReturnMatchedBook() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK1, BOOK2))).thenReturn(List.of(BOOK1_DTO, BOOK2_DTO));
        Assertions.assertThat(bookService.searchBooksByTitle("title*")).contains(BOOK1_DTO, BOOK2_DTO);
    }

    @Test
    void givenIsbn_whenSearchBookWithIsbnNotExisting_thenReturn0Book() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooksByIsbn("bla")).contains();
    }

    @Test
    void givenIsbn_whenSearchBookWithExactIsbn_thenReturnTheGoodBook() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK1))).thenReturn(List.of(BOOK1_DTO));
        Assertions.assertThat(bookService.searchBooksByIsbn("is1bn1")).contains(BOOK1_DTO);
    }

    @Test
    void givenIsbn_whenSearchBookWithWildcardIsbn_thenReturnMatchedBook() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK2, BOOK3))).thenReturn(List.of(BOOK2_DTO, BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooksByIsbn("isbn*")).contains(BOOK2_DTO, BOOK3_DTO);
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookNotExisting_thenReturn0Book() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooksByTitleAndIsbn("bla", "bla")).contains();
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookWithExistingTitleButNotExistingIsbn_thenReturn0Book() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooksByTitleAndIsbn("title1", "bla")).contains();
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookWithExistingIsbnButNotExistingTitle_thenReturn0Book() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooksByTitleAndIsbn("bla", "isbn2")).contains();
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookTitleAndIsbn_thenReturnBooks() {
        Mockito.when(bookRepository.getBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK3))).thenReturn(List.of(BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooksByTitleAndIsbn("*title", "isbn*")).contains(BOOK3_DTO);
    }
}