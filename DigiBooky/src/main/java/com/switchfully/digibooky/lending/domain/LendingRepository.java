package com.switchfully.digibooky.lending.domain;

import com.switchfully.digibooky.user.domain.Member;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class LendingRepository {
    private HashMap<String, Lending> lendings = new HashMap<>();

    public Lending addLending(Lending lending) {
        lendings.put(lending.getId(), lending);
        return lending;
    }

    public Optional<Lending> getLendingById(String id) {
        return Optional.of(lendings.get(id));
    }

    public List<Lending> getLendingsByMember(Member member) {
        return lendings.values().stream()
                .filter(l -> l.isActive() && l.getMember().equals(member))
                .toList();
    }

    public List<Lending> getOverdueLendings() {
        return lendings.values().stream()
                .filter(l -> l.isActive() && l.getReturningDate().isBefore(LocalDate.now()))
                .toList();
    }

    public Optional<Lending> getLendingByBookId(String bookId) {
        return lendings.values().stream()
                .filter(l -> l.getBook().getId().equals(bookId))
                .findFirst();
    }
}
