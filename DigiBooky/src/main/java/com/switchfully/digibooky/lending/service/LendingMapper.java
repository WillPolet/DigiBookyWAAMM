package com.switchfully.digibooky.lending.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.BookMapper;
import com.switchfully.digibooky.exception.DateFormatException;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.service.UserMapper;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.zip.DataFormatException;

@Component
public class LendingMapper {
    private UserMapper userMapper;
    private BookMapper bookMapper;

    public LendingMapper(UserMapper userMapper, BookMapper bookMapper) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public LendingDto toDto(Lending lending) {
        return new LendingDto(lending.getId(), userMapper.toMemberDto(lending.getMember()), bookMapper.toDTO(lending.getBook()), lending.getLendingDate().toString(), lending.getReturningDate().toString());
    }

    public Lending fromDto(CreateLendingDto createLendingDto, Book book, Member member) {
        if (createLendingDto.getReturningDate() == null) {
            return new Lending(member, book);
        }
        LocalDate returningDate;
        try {
            returningDate = LocalDate.parse(createLendingDto.getReturningDate());
        }
        catch (DateTimeException ex) {
            throw new DateFormatException("Returning date format is not valid");
        }
        return new Lending(member, book, returningDate);
    }
}
