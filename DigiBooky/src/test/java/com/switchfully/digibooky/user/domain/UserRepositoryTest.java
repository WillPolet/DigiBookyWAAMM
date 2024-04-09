package com.switchfully.digibooky.user.domain;

import com.switchfully.digibooky.user.domain.userAttribute.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private static final Admin ROOT =  new Admin("root@root.com", "rootLastName", "rootFirstName", "rootPswd");
    private static final Admin ADMIN = new Admin("p@a.com", "Phoenix", "Wright", "pwd");
    private static final Librarian LIBRARIAN = new Librarian("l@a.com", "Luke", "Atmey", "pwd");
    private static final Address ADDRESS = new Address("Justice Street", "7", "2000", "Charleroi");
    private static final Member MEMBER = new Member("m@a.com", "Maya", "Fey", "pwd", ADDRESS, "1234567890o");
    private static UserRepository userRepo;

    @BeforeEach
    void setUp() {
        userRepo = new UserRepository();
        userRepo.addUser(ADMIN);
        userRepo.addUser(LIBRARIAN);
        userRepo.addUser(MEMBER);
    }

    @Test
    void gettingUser_givenRepoContainsData_willReturnListOfUser() {
        Collection<User> actual = userRepo.getAllUsers();

        List<User> expected = List.of(ADMIN, MEMBER, LIBRARIAN);
        Assertions.assertThat(actual).containsAnyElementsOf(expected);
    }

    @Test
    void gettingUserByEmail_givenRepoContainsData_willReturnAppropriateUser() {
        String emailToCheck = "l@a.com";
        Optional<User> actual = userRepo.getUserByEmail(emailToCheck);
        Assertions.assertThat(actual.get()).isEqualTo(LIBRARIAN);
    }

    @Test
    void gettingMemberByInss_givenMemberExistsInSystem_willReturnMember() {
        String emailToCheck = "1234567890o";
        Optional<Member> actual = userRepo.getMemberByInss(emailToCheck);
        Assertions.assertThat(actual.get()).isEqualTo(MEMBER);
    }
}