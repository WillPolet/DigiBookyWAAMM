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

    private static final Author AUTHOR = new Author();
    private static final Book BOOK = new Book("isbn", "title", "summary", true, false, AUTHOR);
    private static final BookDto BOOK_DTO = new BookDto(BOOK.getUuid(), BOOK.getIsbn(), BOOK.getTitle(), BOOK.getSummary(), BOOK.getAccessible(), BOOK.getRented(), BOOK.getAuthor());
    @Mock
    BookMapper bookMapper;
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookService bookService;

    @Test
    void givenTitle_whenSearchBookByTitle_thenReturnBook() {
        Mockito.when(bookRepository.searchBooksByTitleWithWildcard(BOOK.getTitle())).thenReturn(List.of(BOOK));
        Mockito.when(bookMapper.toDTO(List.of(BOOK))).thenReturn(List.of(BOOK_DTO));
        Assertions.assertThat(bookService.searchBookByTitle(BOOK.getTitle())).isEqualTo(List.of(BOOK_DTO));
    }

    @Test
    void givenIsbn_whenSearchBookByIsbn_thenReturnBook() {
        Mockito.when(bookRepository.searchBooksByIsbnWithWildcard(BOOK.getIsbn())).thenReturn(List.of(BOOK));
        Mockito.when(bookMapper.toDTO(List.of(BOOK))).thenReturn(List.of(BOOK_DTO));
        Assertions.assertThat(bookService.searchBookByIsbn(BOOK.getIsbn())).isEqualTo(List.of(BOOK_DTO));
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookByTitleAndIsbn_thenReturnBook() {
        Mockito.when(bookRepository.searchBooksByTitleAndIsbnWithWildcard(BOOK.getTitle(), BOOK.getIsbn())).thenReturn(List.of(BOOK));
        Mockito.when(bookMapper.toDTO(List.of(BOOK))).thenReturn(List.of(BOOK_DTO));
        Assertions.assertThat(bookService.searchBookByTitleAndIsbn(BOOK.getTitle(), BOOK.getIsbn())).isEqualTo(List.of(BOOK_DTO));
    }

    @Test
    void givenExistingBooks_whengetBooks_thenReturnAllTheBooks() {
        Mockito.when(bookRepository.getBooks()).thenReturn(List.of(BOOK));
        Mockito.when(bookMapper.toDTO(List.of(BOOK))).thenReturn(List.of(BOOK_DTO));
        Assertions.assertThat(bookService.getBooks()).isEqualTo(List.of(BOOK_DTO));
    }
}