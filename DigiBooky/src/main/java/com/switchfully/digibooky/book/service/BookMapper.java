package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

     public BookDto toDTO(Book book){
        return new BookDto(book.getUuid(), book.getIsbn(), book.getTitle(), book.getSummary(), book.getAccessible(), book.getRented(), book.getAuthor());
    }

    public List<BookDto> toDTO(Collection<Book> books){
         return books.stream()
                 .map(this::toDTO)
                 .collect(Collectors.toList());
    }
}
