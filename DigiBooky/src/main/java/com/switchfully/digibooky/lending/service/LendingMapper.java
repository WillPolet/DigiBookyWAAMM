package com.switchfully.digibooky.lending.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.BookMapper;
import com.switchfully.digibooky.exception.DateFormatException;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.service.UserMapper;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
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

    public List<LendingDto> toDto(Collection<Lending> lendings) {
        return lendings.stream().map(this::toDto).toList();
    }

    public Lending fromDto(CreateLendingDto createLendingDto, Book book, Member member) {
        if (createLendingDto.getReturningDate() == null) {
            return new Lending(member, book);
        }
        LocalDate returningDate;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            returningDate = LocalDate.parse(createLendingDto.getReturningDate(), dateTimeFormatter);
        }
        catch (Exception ex) {
            throw new DateFormatException("Returning date format is not valid");
        }
        return new Lending(member, book, returningDate);
    }
}
