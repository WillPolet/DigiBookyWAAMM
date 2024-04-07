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

    public BookDto updateBook(UpdateBookDto bookDto, String id) {
        // Should check if book already exist or not ?
        if (!bookRepository.doesIdExist(id)) {
            throw new IllegalArgumentException("Book with id " + id + " does not exist");
        }
        // get isbn based on ID
        String isbn = bookRepository.getIsbnById(id);
        Book book = new Book(isbn, bookDto.getTitle(), bookDto.getSummary(), bookDto.getAccessible(), bookDto.getRented(), bookDto.getAuthor());
        bookRepository.updateBook(book, id);
        return bookMapper.toDTO(book);
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

    public List<BookDto> getAllBooks() {
        return bookMapper.toDTO(bookRepository.getAllBooks());
    }
}
