package com.switchfully.digibooky.user.domain;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class UserRepository {
    private Map<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public User addUser(User newUser){
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public Collection<User> getAllUsers(){
        return users.values().stream().toList();
    }

    public Optional<User> getUserByEmail(String email){
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

}