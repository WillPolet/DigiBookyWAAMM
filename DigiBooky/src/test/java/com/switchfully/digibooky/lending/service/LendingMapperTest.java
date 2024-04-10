package com.switchfully.digibooky.lending.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.BookMapper;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.UserMapper;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LendingMapperTest {
    private static final Address ADDRESS = new Address("streetname", "streetnumber", "zipcode", "city");
    private static final Member MEMBER1 = new Member("email1", "lastname1", "firstname1", "password", ADDRESS, "inss1");
    private static final MemberDto MEMBER1_DTO = new MemberDto(MEMBER1.getId(), MEMBER1.getEmail(), MEMBER1.getLastname(), MEMBER1.getFirstname(), ADDRESS);
    private static final Author AUTHOR = new Author("firstname", "lastname");
    private static final Book BOOK1 = new Book("isbn1", "title1", "summary", AUTHOR);
    private static final BookDto BOOK1_DTO = new BookDto(BOOK1.getId(), BOOK1.getIsbn(), BOOK1.getTitle(), BOOK1.getSummary(), BOOK1.isAvailable(), BOOK1.isLent(), BOOK1.getAuthor());
    public static final String RETURNING_DATE = "13/01/2024";
    private static final Lending LENDING1 = new Lending(MEMBER1, BOOK1);
    private static final LendingDto LENDING1_DTO = new LendingDto(LENDING1.getId(), MEMBER1_DTO, BOOK1_DTO, LENDING1.getLendingDate().toString(), LENDING1.getReturningDate().toString());
    private static final Lending LENDING2 = new Lending(MEMBER1, BOOK1);
    private static final LendingDto LENDING2_DTO = new LendingDto(LENDING2.getId(), MEMBER1_DTO, BOOK1_DTO, LENDING2.getLendingDate().toString(), LENDING2.getReturningDate().toString());
    private static final CreateLendingDto CREATE_LENDING1_DTO = new CreateLendingDto("isbn1", "userid1", RETURNING_DATE);

    @Mock
    private UserMapper userMapper;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private LendingMapper lendingMapper;

    @Test
    void givenCreateLendingDto_whenMapFromDto_thenReturnLending() {
        Lending actualLending = lendingMapper.fromDto(CREATE_LENDING1_DTO, BOOK1, MEMBER1);
        Assertions.assertThat(actualLending.getReturningDate()).isEqualTo(LocalDate.of(2024,1,13));
    }

    @Test
    void givenLending_whenMapToDto_thenReturnLendingDto() {
        Mockito.when(userMapper.toMemberDto(MEMBER1)).thenReturn(MEMBER1_DTO);
        Mockito.when(bookMapper.toDTO(BOOK1)).thenReturn(BOOK1_DTO);

        LendingDto actualLendingDto = lendingMapper.toDto(LENDING1);
        Assertions.assertThat(actualLendingDto).isEqualTo(LENDING1_DTO);
    }

    @Test
    void givenLendings_whenMapToDto_thenReturnListOfLendingDto() {
        Mockito.when(userMapper.toMemberDto(MEMBER1)).thenReturn(MEMBER1_DTO);
        Mockito.when(bookMapper.toDTO(BOOK1)).thenReturn(BOOK1_DTO);

        Assertions.assertThat(lendingMapper.toDto(List.of(LENDING1, LENDING2))).containsExactlyInAnyOrder(LENDING1_DTO, LENDING2_DTO);
    }
}
