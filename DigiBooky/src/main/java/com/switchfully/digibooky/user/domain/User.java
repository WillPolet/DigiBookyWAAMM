package com.switchfully.digibooky.user.domain;

import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class User {
    private String id;
    private String email;
    private String lastname;
    private String firstname;
    private String password;
    private List<RoleFeature> roleFeatures;

    public User(String id, String email, String lastname, String firstname, String password, List<RoleFeature> roleFeatures) {
        this.id = id;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.roleFeatures = roleFeatures;
    }

    public boolean hasFeature(RoleFeature feature){
        return roleFeatures.contains(feature);
    }
    public boolean passwordMatch(String pwd){
        return password.equals(pwd);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPassword() {
        return password;
    }

    public List<RoleFeature> getRoleFeatures() {
        return roleFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getLastname(), user.getLastname()) && Objects.equals(getFirstname(), user.getFirstname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getLastname(), getFirstname());
    }
}















