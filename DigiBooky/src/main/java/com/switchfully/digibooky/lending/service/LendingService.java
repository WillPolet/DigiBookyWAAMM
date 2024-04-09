package com.switchfully.digibooky.lending.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.exception.AccessForbiddenException;
import com.switchfully.digibooky.exception.NotFoundException;
import com.switchfully.digibooky.lending.domain.LendingRepository;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.UserRepository;
import org.springframework.stereotype.Service;

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
        User user = userRepository.getUserById(createLendingDto.getUserId())
                .orElseThrow(() -> new NotFoundException("There is no member with the identification number : " + createLendingDto.getUserId()));
        if (!(user instanceof Member member)) {
            throw new AccessForbiddenException("This user cannot lend a book");
        }
        return lendingMapper.toDto(lendingRepository.addLending(lendingMapper.fromDto(createLendingDto, book, member)));
    }
}
