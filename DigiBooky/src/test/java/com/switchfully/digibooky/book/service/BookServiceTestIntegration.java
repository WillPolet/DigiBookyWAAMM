package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.author.domain.AuthorRepository;
import com.switchfully.digibooky.author.service.AuthorMapper;
import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.author.service.dto.UpdateAuthorDto;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.book.service.dto.UpdateBookDto;
import com.switchfully.digibooky.lending.domain.LendingRepository;
import com.switchfully.digibooky.user.service.UserMapper;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookServiceTestIntegration {

    private BookRepository bookRepository = new BookRepository();
    private UserMapper userMapper = new UserMapper();
    private BookMapper bookMapper = new BookMapper(userMapper);
    private AuthorMapper authorMapper = new AuthorMapper();
    private AuthorRepository authorRepository = new AuthorRepository();
    private LendingRepository lendingRepository = new LendingRepository();
    private BookService bookService = new BookService(bookRepository, bookMapper, authorRepository, authorMapper, lendingRepository);


    private static final Author AUTHOR1 = new Author("firstname1", "lastname1");
    private static final Book BOOK1 = new Book("is1bn1", "title1", "summary", AUTHOR1);
    private static final CreateAuthorDto AUTHOR1_CREATE_DTO = new CreateAuthorDto("firstname1", "lastname1");
    private static final UpdateAuthorDto AUTHOR1_UPDATE_DTO = new UpdateAuthorDto("firstname1", "lastname1");
    private static final CreateBookDto BOOK1_CREATE_DTO = new CreateBookDto("isbn1", "title1","summary", AUTHOR1_CREATE_DTO);
    private static final UpdateBookDto BOOK1_UPDATE_DTO = new UpdateBookDto("is1bn1", "title1", "summaryhihi",AUTHOR1_UPDATE_DTO);
    private static final BookDto BOOK1_DTO = new BookDto(BOOK1.getId(), BOOK1.getIsbn(), BOOK1.getTitle(), BOOK1.getSummary(), BOOK1.isAvailable(), BOOK1.isLent(), BOOK1.getAuthor(), null);


    @Test
    void givenCorrectData_CreatingBook_WillRetrunBookDTO(){
        BookDto actualBookDto = bookService.createBook(BOOK1_CREATE_DTO);
        Assertions.assertThat(actualBookDto.getIsbn()).isEqualTo(BOOK1_CREATE_DTO.getIsbn());
    }

    @Test
    void givenUpdateBookAndId_UpdateBook_WillReturnBookDtoModified(){
        bookRepository.addBook(BOOK1);
        BookDto actualBook = bookService.updateBook(BOOK1_UPDATE_DTO, BOOK1.getId());
        Assertions.assertThat(actualBook.getTitle()).isEqualTo(BOOK1_UPDATE_DTO.getTitle());
        Assertions.assertThat(actualBook.getSummary()).isEqualTo(BOOK1_UPDATE_DTO.getSummary());
        Assertions.assertThat(actualBook.getAuthor().getFirstname()).isEqualTo(BOOK1_UPDATE_DTO.getAuthor().getFirstname());
        Assertions.assertThat(actualBook.getAuthor().getLastname()).isEqualTo(BOOK1_UPDATE_DTO.getAuthor().getLastname());
    }

    @Test
    void givenId_DeleteBook_WillChangeIsAvailableToFalse(){
        Book unavailableBook1 = new Book(BOOK1.getId(), BOOK1.getIsbn(),BOOK1.getTitle(), BOOK1.getSummary(), false, BOOK1.isLent(), BOOK1.getAuthor());
        bookRepository.addBook(BOOK1);
        bookService.deleteBook(BOOK1.getId());
        Assertions.assertThat(BOOK1).isEqualTo(unavailableBook1);
    }

    @Test
    void givenId_FindBookById_WillGetTheAppropriateBook(){
        String id = BOOK1.getId();
        bookRepository.addBook(BOOK1);
        BookDto actual = bookService.getBookById(BOOK1.getId());
        Assertions.assertThat(actual.getId()).isEqualTo(id );
    }
}
