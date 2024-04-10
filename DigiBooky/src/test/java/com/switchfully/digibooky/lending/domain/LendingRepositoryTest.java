package com.switchfully.digibooky.lending.domain;

import com.switchfully.digibooky.author.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

class LendingRepositoryTest {
    private static final Address ADDRESS = new Address("streetname", "streetnumber", "zipcode", "city");
    private static final Member MEMBER1 = new Member("email1", "lastname1", "firstname1", "password", ADDRESS, "inss1");
    private static final Member MEMBER2 = new Member("email2", "lastname2", "firstname2", "password", ADDRESS, "inss2");
    private static final Author AUTHOR = new Author("firstname", "lastname");
    private static final Book BOOK1 = new Book("isbn1", "title1", "summary", AUTHOR);
    private static final Book BOOK2 = new Book("isbn2", "title2", "summary", AUTHOR);
    private static final Book BOOK3 = new Book("isbn3", "title3", "summary", AUTHOR);
    private static final Lending LENDING1 = new Lending(MEMBER1, BOOK1);
    private static final Lending LENDING2 = new Lending(MEMBER1, BOOK2);
    private static final Lending LENDING3 = new Lending(MEMBER2, BOOK3);
    private LendingRepository lendingRepository = new LendingRepository();

    @Test
    void givenLending_whenAddLending_thenLendingAdded() {
        Assertions.assertThat(lendingRepository.addLending(LENDING1)).isEqualTo(LENDING1);
    }

    @Test
    void givenAddedLending_whenGetLendingById_thenReturnLending() {
        lendingRepository.addLending(LENDING1);
        Optional<Lending> optLending = lendingRepository.getLendingById(LENDING1.getId());
        Assertions.assertThat(optLending.isPresent()).isTrue();
        Assertions.assertThat(optLending.get()).isEqualTo(LENDING1);
    }

    @Test
    void givenMember_whenGetLendingsByMember_thenReturnLendings() {
        fillRepository();
        Assertions.assertThat(lendingRepository.getLendingsByMember(MEMBER1)).containsExactlyInAnyOrder(LENDING1, LENDING2);
    }

    @Test
    void givenMember_whenDeactivateLending_thenThisLendingIsNotReturned() {
        fillRepository();
        LENDING2.deactivate();
        Assertions.assertThat(lendingRepository.getLendingsByMember(MEMBER1)).containsExactlyInAnyOrder(LENDING1);
    }

    @Test
    void givenFilledRepository_whenGetOverdueLendings_thenReturnOverdueLendings() {
        fillRepository();
        Lending lendingCustomDate;
        try (MockedStatic<LocalDate> mocked = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDate mockedDate = LocalDate.of(1990, 4, 1);
            mocked.when(LocalDate::now).thenReturn(mockedDate);
            lendingCustomDate = new Lending(MEMBER2, BOOK2);
        }
        lendingRepository.addLending(lendingCustomDate);
        Assertions.assertThat(lendingRepository.getOverdueLendings()).containsExactlyInAnyOrder(lendingCustomDate);
    }

    private void fillRepository() {
        lendingRepository.addLending(LENDING1);
        lendingRepository.addLending(LENDING2);
        lendingRepository.addLending(LENDING3);
    }
}