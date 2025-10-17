package com.bohdanbest.playlist;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class InMemoryPlaylistRepository {
    private final List<Playlist> playlists = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    public InMemoryPlaylistRepository() {
        playlists.add(new Playlist(counter.incrementAndGet(), "My Rock Favorites", 1L));
    }

    public List<Playlist> findAll() {
        return playlists;
    }

    public Optional<Playlist> findById(Long id) {
        return playlists.stream().filter(p -> p.id.equals(id)).findFirst();
    }
}
