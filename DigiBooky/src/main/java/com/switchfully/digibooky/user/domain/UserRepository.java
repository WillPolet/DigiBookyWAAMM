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
        Admin adminRoot = new Admin("root@root.com", "rootLastName", "rootFirstName", "rootPswd");
        users.put(adminRoot.getId(), adminRoot);
    }

    public User addUser(User newUser) {
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public Collection<User> getAllUsers() {
        return users.values().stream().toList();
    }


    public Optional<Member> getMemberByInss(String inss) {
        return users.values().stream()
                .filter(user -> user instanceof Member)
                .map(user -> (Member) user)
                .filter(member -> member.getInss().equals(inss))
                .findFirst();
    }

    public Optional<User> getUserByEmail(String email) {
        return users.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }

    public List<Member> getAllMembers() {
        return users.values().stream()
                .filter(user -> user instanceof Member)
                .map(user -> (Member) user)
                .collect(Collectors.toList());
    }
}