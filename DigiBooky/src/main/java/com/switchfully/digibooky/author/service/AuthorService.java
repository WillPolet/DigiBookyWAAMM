package com.switchfully.digibooky.author.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.author.domain.AuthorRepository;
import com.switchfully.digibooky.author.service.dto.AuthorDto;
import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.utility.PatternUtility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorMapper authorMapper, AuthorRepository authorRepository) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }

    public AuthorDto createAuthor(CreateAuthorDto createAuthorDto) {
        return authorMapper.toDTO(authorRepository.addAuthor(authorMapper.fromDto(createAuthorDto)));
    }

    public List<AuthorDto> searchAuthorsByFirstname(String firstname) {
        return authorMapper.toDTO(getAuthorsByFirstnameWithWildcard(authorRepository.getAuthors(), firstname));
    }

    public List<AuthorDto> searchAuthorsByLastname(String lastname) {
        return authorMapper.toDTO(getAuthorsByLastnameWithWildcard(authorRepository.getAuthors(), lastname));
    }

    public List<AuthorDto> searchAuthorsByFirstnameAndLastname(String firstname, String lastname) {
        return authorMapper.toDTO(getAuthorsByFirstnameWithWildcard(
                getAuthorsByLastnameWithWildcard(authorRepository.getAuthors(), lastname),
                firstname
        ));
    }

    private List<Author> getAuthorsByFirstnameWithWildcard(List<Author> authors, String firstname) {
        Pattern pattern = PatternUtility.getPattern(firstname);
        return authors.stream()
                .filter(a -> pattern.matcher(a.getFirstname()).find())
                .toList();
    }

    private List<Author> getAuthorsByLastnameWithWildcard(List<Author> authors, String lastname) {
        Pattern pattern = PatternUtility.getPattern(lastname);
        return authors.stream()
                .filter(a -> pattern.matcher(a.getLastname()).find())
                .toList();
    }

    public List<AuthorDto> getAuthors() {
        return authorMapper.toDTO(authorRepository.getAuthors());
    }
}
