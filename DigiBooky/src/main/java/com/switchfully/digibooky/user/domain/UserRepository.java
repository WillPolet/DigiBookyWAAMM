package com.switchfully.digibooky.user.domain;

import com.switchfully.digibooky.exception.UniqueFieldAlreadyExistException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private Map<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public User addUser(User newUser) {
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public Collection<User> getAllUsers() {
        return users.values().stream().toList();
    }

    public User getUserByEmail(String email) {
        return null;
    }

    public Optional<Member> getMemberByInss(String inss) {
        return users.values().stream()
                .filter(user -> user instanceof Member)
                .map(user -> (Member) user)
                .filter(member -> member.getInss().equals(inss))
                .findFirst();
    }
}