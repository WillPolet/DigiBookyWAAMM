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
import com.switchfully.digibooky.book.service.utility.SearchBookUtility;
import com.switchfully.digibooky.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collection;

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

    public BookDto updateBook(UpdateBookDto updateBookDto, String id) {
        // Should check if book already exist or not ?
        if (!bookRepository.doesIdExist(id)) {
            throw new NotFoundException("Book with id " + id + " does not exist.");
        }

        // Call author service to check if author exist if not create it and add it to the authorRepository
        Author authorFromUpdateBook = getExistingAuthorOrCreateIt(updateBookDto.getAuthor());
        // get isbn (in DB) based on ID

        String isbn = bookRepository.getIsbnById(id);

        if (!isbn.equals(updateBookDto.getIsbn())){
            throw new NotFoundException("The isbn provided and of book " + id + " doesn't match.");
        }

        Book book = new Book(id, updateBookDto.getIsbn(),
                updateBookDto.getTitle(),
                updateBookDto.getSummary(),
                updateBookDto.isAvailable(),
                updateBookDto.isLent(),
                authorFromUpdateBook);

        bookRepository.updateBook(book, id);
        return bookMapper.toDTO(book);
    }

    private Author getExistingAuthorOrCreateIt(CreateAuthorDto createAuthorDto) {
        return authorRepository
                .getAuthorByFirstnameAndLastname(
                        createAuthorDto.getFirstname(),
                        createAuthorDto.getLastname())
                .orElseGet(() -> authorRepository.addAuthor(authorMapper.fromDto(createAuthorDto)));
    }

    private Author getExistingAuthorOrCreateIt(UpdateAuthorDto updateAuthorDto){
        return authorRepository
                .getAuthorByFirstnameAndLastname(
                        updateAuthorDto.getFirstname(),
                        updateAuthorDto.getLastname())
                .orElseGet(() -> authorRepository.addAuthor(authorMapper.fromDto(updateAuthorDto)));
    }



    public void deleteBook(String id) {
        bookRepository.setBookToInaccessible(id);
    }

    public BookDto getBookById(String id) {
        if (!bookRepository.doesIdExist(id)) {
            throw new IllegalArgumentException("Book with id " + id + " does not exist");
        }
        return bookMapper.toDTO(bookRepository.getBookById(id));
    }

    public List<BookDto> searchBooks(String title, String isbn, String authorFirstname, String authorLastname) {
        Collection<Book> books = bookRepository.getAllBooks();
        if (title != null) {
            books = SearchBookUtility.getBooksByTitleWithWildcard(books, title);
        }
        if (isbn != null) {
            books = SearchBookUtility.getBooksByIsbnWithWildcard(books, isbn);
        }
        if (authorFirstname != null) {
            books = SearchBookUtility.getBooksByAuthorFirstnameWithWildcard(books, authorFirstname);
        }
        if (authorLastname != null) {
            books = SearchBookUtility.getBooksByAuthorLastnameWithWildcard(books, authorLastname);
        }
        return bookMapper.toDTO(books);
    }
}
