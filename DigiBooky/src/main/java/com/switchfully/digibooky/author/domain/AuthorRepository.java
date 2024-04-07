package com.switchfully.digibooky.author.domain;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository {
    private final HashMap<String, Author> authors = new HashMap<>();

    public Author addAuthor(Author author) {
        if (!isAuthorAlreadyExist(author)) {
            authors.put(author.getId(), author);
        } else {
            throw new UniqueFieldAlreadyExistException("There is already an author with same firstname and lastname");
        }
        return author;
    }

    public List<Author> getAuthors() {
        return authors.values().stream().toList();
    }

    private boolean isAuthorAlreadyExist(Author author) {
        return authors.values().stream()
                .anyMatch(a -> a.getFirstname().equals(author.getFirstname())
                        && a.getLastname().equals(author.getLastname()));
    }

    public Optional<Author> getAuthorById(String id) {
        return Optional.ofNullable(authors.get(id));
    }

    public Optional<Author> getAuthorByFirstnameAndLastname(String firstname, String lastname) {
        return authors.values().stream()
                .filter(a -> a.getFirstname().equals(firstname)
                        && a.getLastname().equals(lastname))
                .findFirst();
    }
}
