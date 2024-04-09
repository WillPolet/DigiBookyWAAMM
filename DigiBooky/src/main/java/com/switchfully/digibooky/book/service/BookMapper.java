package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

     public BookDto toDTO(Book book){
        return new BookDto(book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getSummary(),
                book.isAvailable(),
                book.isLent(),
                book.getAuthor());
    }

    public List<BookDto> toDTO(Collection<Book> books){
         return books.stream()
                 .map(this::toDTO)
                 .collect(Collectors.toList());
    }

    public Book fromDto(CreateBookDto bookDto, Author author) {
        return new Book(bookDto.getIsbn(), bookDto.getTitle(), bookDto.getSummary(), bookDto.isAvailable(), bookDto.isLent(), author);
    }
}
