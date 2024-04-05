package com.switchfully.digibooky.user.domain;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private Map<UUID, User> users;

    public UserRepository(Map<UUID, User> users) {
        this.users = new ConcurrentHashMap<>();
    }

    public User addUser(User newUser){
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public Collection<User> getAllUsers(){
        return users.values().stream().toList();
    }

}