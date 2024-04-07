package com.switchfully.digibooky.book.service.utility;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.utility.PatternUtility;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class SearchBookUtility {

    public static List<Book> getBooksByTitleWithWildcard(Collection<Book> books, String title) {
        Pattern pattern = PatternUtility.getPattern(title);
        return books.stream()
                .filter(b -> pattern.matcher(b.getTitle()).find())
                .toList();
    }

    public static List<Book> getBooksByIsbnWithWildcard(Collection<Book> books, String isbn) {
        Pattern pattern = PatternUtility.getPattern(isbn);
        return books.stream()
                .filter(b -> pattern.matcher(b.getIsbn()).find())
                .toList();
    }

    public static List<Book> getBooksByFirstnameWithWildcard(Collection<Book> books, String firstname) {
        Pattern pattern = PatternUtility.getPattern(firstname);
        return books.stream()
                .filter(b -> pattern.matcher(b.getAuthor().getFirstname()).find())
                .toList();
    }

    public static List<Book> getBooksByLastnameWithWildcard(Collection<Book> books, String lastname) {
        Pattern pattern = PatternUtility.getPattern(lastname);
        return books.stream()
                .filter(b -> pattern.matcher(b.getAuthor().getLastname()).find())
                .toList();
    }
}
