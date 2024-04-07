package com.switchfully.digibooky.author.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.author.service.dto.AuthorDto;
import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AuthorMapperTest {
    private static final Author AUTHOR1 = new Author("firstname1", "lastname1");
    private static final Author AUTHOR2 = new Author("firstname2", "lastname2");
    private static final Author AUTHOR3 = new Author("firstname3", "lastname3");
    private static final List<Author> AUTHORS = List.of(AUTHOR1, AUTHOR2, AUTHOR3);
    private static final AuthorDto AUTHOR1_DTO = new AuthorDto(AUTHOR1.getId(), AUTHOR1.getFirstname(), AUTHOR1.getLastname());
    private static final AuthorDto AUTHOR2_DTO = new AuthorDto(AUTHOR2.getId(), AUTHOR2.getFirstname(), AUTHOR2.getLastname());
    private static final AuthorDto AUTHOR3_DTO = new AuthorDto(AUTHOR3.getId(), AUTHOR3.getFirstname(), AUTHOR3.getLastname());
    private static final List<AuthorDto> AUTHORS_DTO = List.of(AUTHOR1_DTO, AUTHOR2_DTO, AUTHOR3_DTO);
    private static final CreateAuthorDto CREATE_AUTHOR_DTO = new CreateAuthorDto(AUTHOR1.getFirstname(), AUTHOR1.getLastname());

    private final AuthorMapper authorMapper = new AuthorMapper();

    @Test
    void givenAuthor_whenToDto_thenReturnAuthorDto() {
        Assertions.assertThat(authorMapper.toDTO(AUTHOR1)).isEqualTo(AUTHOR1_DTO);
    }

    @Test
    void givenAuthors_whenToDto_thenReturnAuthorsDto() {
        Assertions.assertThat(authorMapper.toDTO(AUTHORS)).isEqualTo(AUTHORS_DTO);
    }

    @Test
    void givenAuthorDto_whenFromDto_thenReturnAuthor() {
        Author author = authorMapper.fromDto(CREATE_AUTHOR_DTO);
        Assertions.assertThat(author.getFirstname()).isEqualTo(AUTHOR1.getFirstname());
        Assertions.assertThat(author.getLastname()).isEqualTo(AUTHOR1.getLastname());
    }
}