package com.switchfully.digibooky.lending.service;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.exception.AccessForbiddenException;
import com.switchfully.digibooky.exception.NotFoundException;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.domain.LendingRepository;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.Admin;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.MemberDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LendingServiceTest {
    private static final Address ADDRESS = new Address("streetname", "streetnumber", "zipcode", "city");
    private static final Member MEMBER1 = new Member("email1", "lastname1", "firstname1", "password", ADDRESS, "inss1");
    private static final MemberDTO MEMBER1_DTO = new MemberDTO(MEMBER1.getId(), MEMBER1.getEmail(), MEMBER1.getLastname(), MEMBER1.getFirstname(), MEMBER1.getAddress());
    private static final Author AUTHOR = new Author("firstname", "lastname");
    private static final Book BOOK1 = new Book("isbn1", "title1", "summary", true, false, AUTHOR);
    private static final BookDto BOOK1_DTO = new BookDto(BOOK1.getId(), BOOK1.getIsbn(), BOOK1.getIsbn(), BOOK1.getSummary(), BOOK1.getAccessible(), BOOK1.getRented(), BOOK1.getAuthor());
    private static final CreateLendingDto CREATE_LENDING1_DTO = new CreateLendingDto("isbn1", "userid1", null);
    private static final Lending LENDING1 = new Lending(MEMBER1, BOOK1);
    public static final LendingDto LENDING1_DTO = new LendingDto(LENDING1.getId(), MEMBER1_DTO, BOOK1_DTO, LENDING1.getLendingDate().toString(), LENDING1.getReturningDate().toString());
    private static final Admin ADMIN1 = new Admin("email1", "lastname1", "firstname1", "password");

    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LendingMapper lendingMapper;
    @Mock
    private LendingRepository lendingRepository;
    @InjectMocks
    private LendingService lendingService;

    @Test
    void givenLendingDto_whenCreateLending_thenReturnLendingDto() {
        Mockito.when(bookRepository.getBookByIsbn(CREATE_LENDING1_DTO.getBookIsbn())).thenReturn(Optional.of(BOOK1));
        Mockito.when(userRepository.getUserById(CREATE_LENDING1_DTO.getUserId())).thenReturn(Optional.of(MEMBER1));
        Mockito.when(lendingMapper.fromDto(CREATE_LENDING1_DTO, BOOK1, MEMBER1)).thenReturn(LENDING1);
        Mockito.when(lendingRepository.addLending(LENDING1)).thenReturn(LENDING1);
        Mockito.when(lendingMapper.toDto(LENDING1)).thenReturn(LENDING1_DTO);

        Assertions.assertThat(lendingService.createLending(CREATE_LENDING1_DTO)).isEqualTo(LENDING1_DTO);
    }

    @Test
    void givenLendingDtoWithNonExistingIsbn_whenCreateLending_thenThrowException() {
        Mockito.when(bookRepository.getBookByIsbn(CREATE_LENDING1_DTO.getBookIsbn())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> lendingService.createLending(CREATE_LENDING1_DTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("There is no book with the isbn : " + CREATE_LENDING1_DTO.getBookIsbn());
    }

    @Test
    void givenLendingDtoWithNonExistingId_whenCreateLending_thenThrowException() {
        Mockito.when(bookRepository.getBookByIsbn(CREATE_LENDING1_DTO.getBookIsbn())).thenReturn(Optional.of(BOOK1));
        Mockito.when(userRepository.getUserById(CREATE_LENDING1_DTO.getUserId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> lendingService.createLending(CREATE_LENDING1_DTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("There is no member with the identification number : " + CREATE_LENDING1_DTO.getUserId());
    }

    @Test
    void givenLendingDtoWithNotAMember_whenCreateLending_thenThrowException() {
        Mockito.when(bookRepository.getBookByIsbn(CREATE_LENDING1_DTO.getBookIsbn())).thenReturn(Optional.of(BOOK1));
        Mockito.when(userRepository.getUserById(CREATE_LENDING1_DTO.getUserId())).thenReturn(Optional.of(ADMIN1));

        Assertions.assertThatThrownBy(() -> lendingService.createLending(CREATE_LENDING1_DTO))
                .isInstanceOf(AccessForbiddenException.class)
                .hasMessage("This user cannot lend a book");
    }
}