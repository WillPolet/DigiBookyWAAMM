package com.switchfully.digibooky.author.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.author.service.dto.AuthorDto;
import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.author.service.dto.UpdateAuthorDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    public AuthorDto toDTO(Author author){
        return new AuthorDto(author.getId(), author.getFirstname(), author.getLastname());
    }

    public List<AuthorDto> toDTO(Collection<Author> authors){
        return authors.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Author fromDto(CreateAuthorDto createAuthorDto) {
        return new Author(createAuthorDto.getFirstname(), createAuthorDto.getLastname());
    }


    public Author fromDto(UpdateAuthorDto updateAuthorDto) {
        return new Author(updateAuthorDto.getFirstname(), updateAuthorDto.getLastname());
    }
}
