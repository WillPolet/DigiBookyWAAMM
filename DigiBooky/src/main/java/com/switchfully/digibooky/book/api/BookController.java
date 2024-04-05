package com.switchfully.digibooky.book.api;

import com.switchfully.digibooky.book.service.BookService;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.book.service.dto.UpdateBookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public BookDto createBook(@RequestBody CreateBookDto bookDto){
        return bookService.createBook(bookDto);
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/{id}")
    public BookDto updateBook(@RequestBody UpdateBookDto bookDto, @PathVariable UUID id){
        return bookService.updateBook(bookDto, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBook(@PathVariable UUID id){
        bookService.deleteBook(id);
//        return ResponseEntity.ok("The book has been successfully deleted");
    }

}
