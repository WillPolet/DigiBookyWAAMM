package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.book.service.dto.UpdateBookDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto createBook(CreateBookDto bookDto) {
        Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), bookDto.getSummary(), bookDto.getAccessible(), bookDto.getRented(), bookDto.getAuthor());
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

    public List<BookDto> searchBookByTitle(String title) {
        return bookMapper.toDTO(bookRepository.searchBooksByTitleWithWildcard(title));
    }

    public List<BookDto> searchBookByIsbn(String isbn) {
        return bookMapper.toDTO(bookRepository.searchBooksByIsbnWithWildcard(isbn));
    }

    public List<BookDto> searchBookByTitleAndIsbn(String title, String isbn) {
        return bookMapper.toDTO(bookRepository.searchBooksByTitleAndIsbnWithWildcard(title, isbn));
    }

    public List<BookDto> getBooks() {
        return bookMapper.toDTO(bookRepository.getBooks());
    }
}
