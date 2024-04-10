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
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.domain.LendingRepository;
import com.switchfully.digibooky.lending.service.LendingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collection;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;
    private final LendingRepository lendingRepository;


    public BookService(BookRepository bookRepository, BookMapper bookMapper, AuthorRepository authorRepository, AuthorMapper authorMapper, LendingRepository lendingRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.lendingRepository = lendingRepository;
    }

    public BookDto createBook(CreateBookDto createBookDto) {
        Author author = getExistingAuthorOrCreateIt(createBookDto.getAuthor());
        Book book = bookMapper.fromDto(createBookDto, author);
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

        Book bookToUpdate = bookRepository.getBookById(id);

        Book book = new Book(id, updateBookDto.getIsbn(),
                updateBookDto.getTitle(),
                updateBookDto.getSummary(),
                bookToUpdate.isAvailable(),
                bookToUpdate.isLent(),
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
            throw new NotFoundException("Book with id " + id + " does not exist");
        }
        Book book = bookRepository.getBookById(id);
        Optional<Lending> optLending = lendingRepository.getLendingByBookId(id);
        if (optLending.isPresent()) {
            return bookMapper.toDTO(book, optLending.get().getMember());
        }
        return bookMapper.toDTO(book);
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
