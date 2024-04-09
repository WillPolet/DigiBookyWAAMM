package com.switchfully.digibooky.lending.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.exception.AccessForbiddenException;
import com.switchfully.digibooky.exception.AlreadyLentException;
import com.switchfully.digibooky.exception.NotFoundException;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.domain.LendingRepository;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendingService {
    private LendingRepository lendingRepository;
    private LendingMapper lendingMapper;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public LendingService(LendingRepository lendingRepository, LendingMapper lendingMapper, BookRepository bookRepository, UserRepository userRepository) {
        this.lendingRepository = lendingRepository;
        this.lendingMapper = lendingMapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public LendingDto createLending(CreateLendingDto createLendingDto) {
        Book book = bookRepository.getBookByIsbn(createLendingDto.getBookIsbn())
                .orElseThrow(() -> new NotFoundException("There is no book with the isbn : " + createLendingDto.getBookIsbn()));
        if (book.getRented()) {
            throw new AlreadyLentException("The book with isbn " + book.getIsbn() + " is already lent");
        }
        User user = userRepository.getUserById(createLendingDto.getUserId())
                .orElseThrow(() -> new NotFoundException("There is no member with the identification number : " + createLendingDto.getUserId()));
        if (!(user instanceof Member member)) {
            throw new AccessForbiddenException("This user cannot lend a book");
        }
        return lendingMapper.toDto(lendingRepository.addLending(lendingMapper.fromDto(createLendingDto, book, member)));
    }

    public String returnBook(String lendingId) {
        Lending lending = lendingRepository.getLendingById(lendingId).orElseThrow(() -> new NotFoundException("There is no lending with this id"));
        lending.deactivate();
        return "You returned the book with isbn " + lending.getBook().getIsbn() + (lending.isOverdue() ? " late (returning date was " + lending.getReturningDate() + ")" : "");
    }

    public List<LendingDto> getOverdueLendings() {
        return lendingMapper.toDto(lendingRepository.getOverdueLendings());
    }

    public List<LendingDto> getLendingsByMember(String id) {
        User user = userRepository.getUserById(id)
                .orElseThrow(() -> new NotFoundException("There is no member with the identification number : " + id));
        if (!(user instanceof Member member)) {
            throw new AccessForbiddenException("This user cannot lend a book");
        }
        return lendingMapper.toDto(lendingRepository.getLendingsByMember(member));
    }
}
