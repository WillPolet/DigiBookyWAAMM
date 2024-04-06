package com.switchfully.digibooky.author.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.author.domain.AuthorRepository;
import com.switchfully.digibooky.author.service.dto.AuthorDto;
import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    private static final Author AUTHOR1 = new Author("firstname1", "last1name1");
    private static final Author AUTHOR2 = new Author("firstname2", "lastname2");
    private static final Author AUTHOR3 = new Author("3firstname", "lastname3");
    private static final List<Author> AUTHORS = List.of(AUTHOR1, AUTHOR2, AUTHOR3);
    private static final AuthorDto AUTHOR1_DTO = new AuthorDto(AUTHOR1.getId(), AUTHOR1.getFirstname(), AUTHOR1.getLastname());
    private static final AuthorDto AUTHOR2_DTO = new AuthorDto(AUTHOR2.getId(), AUTHOR2.getFirstname(), AUTHOR2.getLastname());
    private static final AuthorDto AUTHOR3_DTO = new AuthorDto(AUTHOR3.getId(), AUTHOR3.getFirstname(), AUTHOR3.getLastname());
    private static final CreateAuthorDto CREATE_AUTHOR_DTO = new CreateAuthorDto(AUTHOR1.getFirstname(), AUTHOR1.getLastname());

    @Mock
    AuthorMapper authorMapper;
    @Mock
    AuthorRepository authorRepository;
    @InjectMocks
    AuthorService authorService;

    @Test
    void givenCreationAuthorDto_whenCreateAuthor_thenReturnAuthor() {
        Mockito.when(authorMapper.fromDto(CREATE_AUTHOR_DTO)).thenReturn(AUTHOR1);
        Mockito.when(authorRepository.addAuthor(AUTHOR1)).thenReturn(AUTHOR1);
        Mockito.when(authorMapper.toDTO(AUTHOR1)).thenReturn(AUTHOR1_DTO);
        Assertions.assertThat(authorService.createAuthor(CREATE_AUTHOR_DTO)).isEqualTo(AUTHOR1_DTO);
    }

    @Test
    void givenFirstname_whenSearchAuthorsWithFirstnameNotExisting_thenReturn0Author() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(authorService.searchAuthorsByFirstname("bla")).containsExactlyInAnyOrder();
    }

    @Test
    void givenFirstname_whenSearchAuthorWithExactFirstname_thenReturnTheGoodAuthor() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of(AUTHOR1))).thenReturn(List.of(AUTHOR1_DTO));
        Assertions.assertThat(authorService.searchAuthorsByFirstname("firstname1")).containsExactlyInAnyOrder(AUTHOR1_DTO);
    }

    @Test
    void givenFirstname_whenSearchAuthorWithWildcardFirstname_thenReturnMatchedAuthor() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of(AUTHOR1, AUTHOR2))).thenReturn(List.of(AUTHOR1_DTO, AUTHOR2_DTO));
        Assertions.assertThat(authorService.searchAuthorsByFirstname("firstname*")).containsExactlyInAnyOrder(AUTHOR1_DTO, AUTHOR2_DTO);
    }

    @Test
    void givenLastname_whenSearchAuthorWithLastnameNotExisting_thenReturn0Author() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(authorService.searchAuthorsByLastname("bla")).containsExactlyInAnyOrder();
    }

    @Test
    void givenLastname_whenSearchAuthorWithExactLastname_thenReturnTheGoodAuthor() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of(AUTHOR1))).thenReturn(List.of(AUTHOR1_DTO));
        Assertions.assertThat(authorService.searchAuthorsByLastname("last1name1")).containsExactlyInAnyOrder(AUTHOR1_DTO);
    }

    @Test
    void givenLastname_whenSearchAuthorWithWildcardLastname_thenReturnMatchedAuthor() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of(AUTHOR2, AUTHOR3))).thenReturn(List.of(AUTHOR2_DTO, AUTHOR3_DTO));
        Assertions.assertThat(authorService.searchAuthorsByLastname("lastname*")).containsExactlyInAnyOrder(AUTHOR2_DTO, AUTHOR3_DTO);
    }

    @Test
    void givenFirstnameAndLastname_whenSearchAuthorNotExisting_thenReturn0Author() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(authorService.searchAuthorsByFirstnameAndLastname("bla", "bla")).containsExactlyInAnyOrder();
    }

    @Test
    void givenFirstnameAndLastname_whenSearchAuthorWithExistingFirstnameButNotExistingLastname_thenReturn0Author() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(authorService.searchAuthorsByFirstnameAndLastname("firstname1", "bla")).containsExactlyInAnyOrder();
    }

    @Test
    void givenFirstnameAndLastname_whenSearchAuthorWithExistingLastnameButNotExistingFirstname_thenReturn0Author() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of())).thenReturn(List.of());
        Assertions.assertThat(authorService.searchAuthorsByFirstnameAndLastname("bla", "lastname2")).containsExactlyInAnyOrder();
    }

    @Test
    void givenFirstnameAndLastname_whenSearchAuthorFirstnameAndLastname_thenReturnAuthors() {
        Mockito.when(authorRepository.getAuthors()).thenReturn(AUTHORS);
        Mockito.when(authorMapper.toDTO(List.of(AUTHOR3))).thenReturn(List.of(AUTHOR3_DTO));
        Assertions.assertThat(authorService.searchAuthorsByFirstnameAndLastname("*firstname", "lastname*")).containsExactlyInAnyOrder(AUTHOR3_DTO);
    }
}