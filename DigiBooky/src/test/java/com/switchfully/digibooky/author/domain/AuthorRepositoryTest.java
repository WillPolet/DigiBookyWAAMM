package com.switchfully.digibooky.author.domain;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthorRepositoryTest {
    private static final Author AUTHOR1 = new Author("firstname1", "lastname1");
    private static final Author AUTHOR2 = new Author("firstname2", "lastname2");
    private static final Author AUTHOR3 = new Author("firstname3", "lastname3");

    private final AuthorRepository authorRepository = new AuthorRepository();

    @Test
    void givenAuthor_whenAddAuthor_thenReturnAuthor() {
        Assertions.assertThat(authorRepository.addAuthor(AUTHOR1)).isEqualTo(AUTHOR1);
    }

    @Test
    void givenAuthors_whenAddAuthorsWithSameFirstnameLastname_thenThrowException() {
        authorRepository.addAuthor(AUTHOR1);
        Assertions.assertThatThrownBy(() -> authorRepository.addAuthor(AUTHOR1))
                .isInstanceOf(UniqueFieldAlreadyExistException.class)
                .hasMessage("There is already an author with same firstname and lastname");
    }

    @Test
    void givenExistingAuthors_whenGetAuthors_thenReturnAllAuthors() {
        addAuthorsToRepository();
        Assertions.assertThat(authorRepository.getAuthors()).containsExactlyInAnyOrder(AUTHOR1, AUTHOR2, AUTHOR3);
    }

    private void addAuthorsToRepository() {
        authorRepository.addAuthor(AUTHOR1);
        authorRepository.addAuthor(AUTHOR2);
        authorRepository.addAuthor(AUTHOR3);
    }

}