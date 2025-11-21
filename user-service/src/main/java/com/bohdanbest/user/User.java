package com.bohdanbest.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // "user" - зарезервоване слово в SQL, краще "users"
public class User {

    @Id
    @GeneratedValue
    public Long id;

    public String username;
    public String email;

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
