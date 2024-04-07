package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.author.domain.AuthorRepository;
import com.switchfully.digibooky.author.service.AuthorMapper;
import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.book.service.dto.UpdateBookDto;
import com.switchfully.digibooky.book.service.utility.SearchBookUtility;
import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;


    public BookService(BookRepository bookRepository, BookMapper bookMapper, AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public BookDto createBook(CreateBookDto bookDto) {
        Author author = getExistingAuthorOrCreateIt(bookDto.getAuthor());
        Book book = bookMapper.fromDto(bookDto, author);
        return bookMapper.toDTO(bookRepository.addBook(book));
    }

    private Author getExistingAuthorOrCreateIt(CreateAuthorDto createAuthorDto) {
        return authorRepository
                .getAuthorByFirstnameAndLastname(
                        createAuthorDto.getFirstname(),
                        createAuthorDto.getLastname())
                .orElseGet(() -> authorRepository.addAuthor(authorMapper.fromDto(createAuthorDto)));
    }

    public BookDto updateBook(UpdateBookDto bookDto, String id) {
        // get isbn based on ID
        String isbn = bookRepository.getIsbnById(id);
        Book book = new Book(isbn, bookDto.getTitle(), bookDto.getSummary(), bookDto.getAccessible(), bookDto.getRented(), bookDto.getAuthor());
        bookRepository.updateBook(book);
        return null;
    }

    public void deleteBook(String id) {
        bookRepository.setBookToInaccessible(id);
    }

    public List<BookDto> searchBooks(String title, String isbn, String authorFirstname, String authorLastname) {
        Collection<Book> books = bookRepository.getBooks();
        if (title != null) {
            books = SearchBookUtility.getBooksByTitleWithWildcard(books, title);
        }
        if (isbn != null) {
            books = SearchBookUtility.getBooksByIsbnWithWildcard(books, isbn);
        }
        if (authorFirstname != null) {
            books = SearchBookUtility.getBooksByFirstnameWithWildcard(books, authorFirstname);
        }
        if (authorLastname != null) {
            books = SearchBookUtility.getBooksByLastnameWithWildcard(books, authorLastname);
        }
        return bookMapper.toDTO(books);
    }
}
