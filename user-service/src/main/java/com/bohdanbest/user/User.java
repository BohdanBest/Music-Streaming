package com.bohdanbest.user;

public class User {
    public Long id;
    public String username;
    public String email;
    public User(Long id, String username, String email) {
        this.id = id; this.username = username; this.email = email;
    }
}
