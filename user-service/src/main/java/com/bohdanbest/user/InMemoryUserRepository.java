package com.bohdanbest.user;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class InMemoryUserRepository {
    private final List<User> users = new CopyOnWriteArrayList<>();

    public InMemoryUserRepository() {

        users.add(new User(1L, "alice", "alice@example.com"));
        users.add(new User(2L, "bob", "bob@example.com"));
    }

    public List<User> findAll() {
        return users;
    }
}
