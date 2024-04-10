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
import com.switchfully.digibooky.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static final Author AUTHOR1 = new Author("firstname1", "lastname1");
    private static final Author AUTHOR2 = new Author("firstname2", "lastname2");
    private static final Author AUTHOR3 = new Author("firstname3", "lastname3");
    private static final CreateAuthorDto AUTHOR1_CREATE_DTO = new CreateAuthorDto("firstname1", "lastname1");
    private static final UpdateAuthorDto AUTHOR1_UPDATE_DTO = new UpdateAuthorDto("firstname1", "lastname1");
    private static final Book BOOK1 = new Book("is1bn1", "title1", "summary", true, false, AUTHOR1);
    private static final Book BOOK2 = new Book("isbn2", "title2super", "summary", true, false, AUTHOR2);
    private static final Book BOOK3 = new Book("isbn3", "3title", "summary", true, false, AUTHOR3);

    private static final CreateBookDto BOOK1_CREATE_DTO = new CreateBookDto("isbn1", "title1","summary", AUTHOR1_CREATE_DTO);
    private static final UpdateBookDto BOOK1_UPDATE_DTO = new UpdateBookDto("is1bn1", "title1", "summaryhihi", true, false,AUTHOR1_UPDATE_DTO);
    private static final String BOOK1_UPDATE_DTO_ID = "book1Id";
    private static final List<Book> BOOKS = List.of(BOOK1, BOOK2, BOOK3);
    private static final BookDto BOOK1_DTO = new BookDto(BOOK1.getId(), BOOK1.getIsbn(), BOOK1.getTitle(), BOOK1.getSummary(), BOOK1.isAvailable(), BOOK1.isLent(), BOOK1.getAuthor());
    private static final BookDto BOOK2_DTO = new BookDto(BOOK2.getId(), BOOK2.getIsbn(), BOOK2.getTitle(), BOOK2.getSummary(), BOOK2.isAvailable(), BOOK2.isLent(), BOOK2.getAuthor());
    private static final BookDto BOOK3_DTO = new BookDto(BOOK3.getId(), BOOK3.getIsbn(), BOOK3.getTitle(), BOOK3.getSummary(), BOOK3.isAvailable(), BOOK3.isLent(), BOOK3.getAuthor());

    @Mock
    BookMapper bookMapper;
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    AuthorMapper authorMapper;
    @InjectMocks
    BookService bookService;


    @Test
    void givenCreateBookDto_whenDataAreCorrect_thenSaveBookToRepository(){
        Mockito.when(authorRepository.getAuthorByFirstnameAndLastname(
                AUTHOR1_CREATE_DTO.getFirstname(),
                AUTHOR1_CREATE_DTO.getLastname()))
                    .thenReturn(Optional.of(AUTHOR1));

        Author mockedAuthor = authorRepository.getAuthorByFirstnameAndLastname(
                AUTHOR1_CREATE_DTO.getFirstname(),
                AUTHOR1_CREATE_DTO.getLastname())
                .get();

        Mockito.when(bookMapper.fromDto(BOOK1_CREATE_DTO, mockedAuthor)).thenReturn(BOOK1);

        Mockito.when(bookRepository.addBook(BOOK1)).thenReturn(BOOK1);
        Mockito.when(bookMapper.toDTO(BOOK1)).thenReturn(BOOK1_DTO);

        BookDto actualBookDto = bookService.createBook(BOOK1_CREATE_DTO);
        Assertions.assertThat(actualBookDto.getId()).isEqualTo(BOOK1.getId());
    }

    @Test
    void givenUpdateBook_whenDataAreCorrect_thenReturningBookDtoUpdated(){
        Mockito.when(bookRepository.doesIdExist(BOOK1_UPDATE_DTO_ID)).thenReturn(true);
        Mockito.when(bookRepository.getIsbnById(BOOK1_UPDATE_DTO_ID)).thenReturn(BOOK1.getIsbn());

        Mockito.when(authorRepository.getAuthorByFirstnameAndLastname(
                AUTHOR1_UPDATE_DTO.getFirstname(),
                AUTHOR1_UPDATE_DTO.getLastname()))
                .thenReturn(Optional.of(AUTHOR1));

        Author mockedAuthor = authorRepository.getAuthorByFirstnameAndLastname(
                AUTHOR1_UPDATE_DTO.getFirstname(),
                AUTHOR1_UPDATE_DTO.getLastname())
                .get();

        Book updatedBook = new Book(BOOK1_UPDATE_DTO_ID,
                BOOK1_UPDATE_DTO.getIsbn(),
                BOOK1_UPDATE_DTO.getTitle(),
                BOOK1_UPDATE_DTO.getSummary(),
                BOOK1_UPDATE_DTO.isAvailable(),
                BOOK1_UPDATE_DTO.isLent(),
                mockedAuthor);

        BookDto bookDtoToReturn = new BookDto(updatedBook.getId(),
                updatedBook.getIsbn(),
                updatedBook.getTitle(),
                updatedBook.getSummary(),
                updatedBook.isAvailable(),
                updatedBook.isLent(),
                mockedAuthor);

        Mockito.when(bookMapper.toDTO(updatedBook)).thenReturn(bookDtoToReturn);

        BookDto actualBookDto = bookService.updateBook(BOOK1_UPDATE_DTO, BOOK1_UPDATE_DTO_ID);

        Assertions.assertThat(actualBookDto.getId()).isEqualTo(BOOK1_UPDATE_DTO_ID);
    }

    @Test
    void givenUpdateBook_whenIdDoesNotExist_thenReturningBookDtoUpdated(){
        Mockito.when(bookRepository.doesIdExist(BOOK1_UPDATE_DTO_ID)).thenReturn(false);
        Assertions.assertThatThrownBy(() -> bookService.updateBook(BOOK1_UPDATE_DTO, BOOK1_UPDATE_DTO_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Book with id " + BOOK1_UPDATE_DTO_ID + " does not exist.");
    }

    @Test
    void givenId_whenIdExist_thenReturnBookDto(){
        Mockito.when(bookRepository.doesIdExist(BOOK1_UPDATE_DTO_ID)).thenReturn(true);
        Mockito.when(bookRepository.getBookById(BOOK1_UPDATE_DTO_ID)).thenReturn(BOOK1);
        Mockito.when(bookMapper.toDTO(BOOK1)).thenReturn(BOOK1_DTO);
        BookDto actualBookReturned = bookService.getBookById(BOOK1_UPDATE_DTO_ID);
        Assertions.assertThat(actualBookReturned.getId()).isEqualTo(BOOK1.getId());
    }

    @Test
    void givenTitle_whenSearchBooksWithTitleNotExisting_thenReturn0Book() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooks("bla", null, null, null)).containsExactlyInAnyOrder();
    }

    @Test
    void givenTitle_whenSearchBookWithExactTitle_thenReturnTheGoodBook() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK1))).thenReturn(List.of(BOOK1_DTO));
        Assertions.assertThat(bookService.searchBooks("title1", null, null, null)).containsExactlyInAnyOrder(BOOK1_DTO);
    }

    @Test
    void givenTitle_whenSearchBookWithWildcardTitle_thenReturnMatchedBook() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK1, BOOK2, BOOK3))).thenReturn(List.of(BOOK1_DTO, BOOK2_DTO, BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooks("*title*", null, null, null)).containsExactlyInAnyOrder(BOOK1_DTO, BOOK2_DTO, BOOK3_DTO);
    }

    @Test
    void givenIsbn_whenSearchBookWithIsbnNotExisting_thenReturn0Book() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooks(null, "bla", null, null)).containsExactlyInAnyOrder();
    }

    @Test
    void givenIsbn_whenSearchBookWithExactIsbn_thenReturnTheGoodBook() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK1))).thenReturn(List.of(BOOK1_DTO));
        Assertions.assertThat(bookService.searchBooks(null, "is1bn1", null, null)).containsExactlyInAnyOrder(BOOK1_DTO);
    }

    @Test
    void givenIsbn_whenSearchBookWithWildcardIsbn_thenReturnMatchedBook() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK2, BOOK3))).thenReturn(List.of(BOOK2_DTO, BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooks(null, "isbn*", null, null)).containsExactlyInAnyOrder(BOOK2_DTO, BOOK3_DTO);
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookNotExisting_thenReturn0Book() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooks("bla", "bla", null, null)).containsExactlyInAnyOrder();
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookWithExistingTitleButNotExistingIsbn_thenReturn0Book() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooks("title1", "bla", null, null)).containsExactlyInAnyOrder();
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookWithExistingIsbnButNotExistingTitle_thenReturn0Book() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(bookService.searchBooks("bla", "isbn2", null, null)).containsExactlyInAnyOrder();
    }

    @Test
    void givenTitleAndIsbn_whenSearchBookTitleAndIsbn_thenReturnBooks() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK2, BOOK3))).thenReturn(List.of(BOOK2_DTO, BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooks("*title*", "isbn*", null, null)).containsExactlyInAnyOrder(BOOK2_DTO, BOOK3_DTO);
    }

    @Test
    void givenFirstname_whenSearchBookFirstname_thenReturnBooks() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK3))).thenReturn(List.of(BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooks(null, null, "firstname3", null)).containsExactlyInAnyOrder(BOOK3_DTO);
    }

    @Test
    void givenLastname_whenSearchBookLastname_thenReturnBooks() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK3))).thenReturn(List.of(BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooks(null, null, null, "lastname3")).containsExactlyInAnyOrder(BOOK3_DTO);
    }

    @Test
    void givenAllParameters_whenSearchBook_thenReturnBooks() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BOOKS);
        Mockito.when(bookMapper.toDTO(List.of(BOOK2, BOOK3))).thenReturn(List.of(BOOK2_DTO, BOOK3_DTO));
        Assertions.assertThat(bookService.searchBooks("*title*", "isbn*", "firstname*", "lastname*")).containsExactlyInAnyOrder(BOOK2_DTO, BOOK3_DTO);
    }
}