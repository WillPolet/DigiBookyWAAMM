package com.switchfully.digibooky.book.api;

import com.switchfully.digibooky.book.service.BookService;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.book.service.dto.UpdateBookDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(produces = "application/json", path = "/{id}")
    public BookDto getBookById(@PathVariable String id){
        return bookService.getBookById(id);
    }

    @GetMapping(produces = "application/json")
    public List<BookDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public BookDto createBook(@RequestBody CreateBookDto bookDto){
        return bookService.createBook(bookDto);
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/{id}")
    public BookDto updateBook(@RequestBody UpdateBookDto bookDto, @PathVariable String id){
        // Should put a condition if it's a librairian who try to access the route.

        return bookService.updateBook(bookDto, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBook(@PathVariable String id){
        bookService.deleteBook(id);
//        return ResponseEntity.ok("The book has been successfully deleted");
    }

    @GetMapping
    public List<BookDto> getBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String isbn, @RequestParam(required = false) String authorFirstname, @RequestParam(required = false) String authorLastname) {
        return bookService.searchBooks(title, isbn, authorFirstname, authorLastname);
    }
}
