package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.book.service.dto.UpdateBookDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto createBook(CreateBookDto bookDto) {
        Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), bookDto.getSummary(), bookDto.getIsAccessible(), bookDto.getIsRented(), bookDto.getAuthor());
        bookRepository.addBook(book);
        return bookMapper.toDTO(book);
    }

    public BookDto updateBook(UpdateBookDto bookDto, UUID id) {
        // get isbn based on ID
        String isbn = bookRepository.getIsbnById(id);
        Book book = new Book(isbn, bookDto.getTitle(), bookDto.getSummary(), bookDto.getAccessible(), bookDto.getRented(), bookDto.getAuthor());
        bookRepository.updateBook(book);
        return null;
    }

    public void deleteBook(UUID id) {
        bookRepository.setBookToInaccessible(id);
    }

    public List<BookDto> searchBooksByTitle(String title) {
        return bookMapper.toDTO(getBooksByTitleWithWildcard(bookRepository.getBooks(), title));
    }

    public List<BookDto> searchBooksByIsbn(String isbn) {
        return bookMapper.toDTO(getBooksByIsbnWithWildcard(bookRepository.getBooks(), isbn));
    }

    public List<BookDto> searchBooksByTitleAndIsbn(String title, String isbn) {
        return bookMapper.toDTO(getBooksByTitleWithWildcard(
                getBooksByIsbnWithWildcard(bookRepository.getBooks(), isbn),
                title
        ));
    }

    private List<Book> getBooksByTitleWithWildcard(Collection<Book> books, String title) {
        Pattern pattern = getPattern(title);
        return books.stream()
                .filter(b -> pattern.matcher(b.getTitle()).find())
                .toList();
    }

    private List<Book> getBooksByIsbnWithWildcard(Collection<Book> books, String isbn) {
        Pattern pattern = getPattern(isbn);
        return books.stream()
                .filter(b -> pattern.matcher(b.getIsbn()).find())
                .toList();
    }

    private Pattern getPattern(String regex) {
        return Pattern.compile("^" + regex.replace("*", ".*") + "$");
    }

    public List<BookDto> getBooks() {
        return bookMapper.toDTO(bookRepository.getBooks());
    }
}
