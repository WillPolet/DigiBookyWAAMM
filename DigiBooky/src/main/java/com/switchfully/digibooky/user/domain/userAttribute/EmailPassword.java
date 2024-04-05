package com.switchfully.digibooky.user.domain.userAttribute;

public class EmailPassword {

    private String email;
    private String password;

    public EmailPassword(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
