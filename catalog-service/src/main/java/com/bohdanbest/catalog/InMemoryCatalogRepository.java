package com.bohdanbest.catalog;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class InMemoryCatalogRepository {
    private final List<Track> tracks = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    public InMemoryCatalogRepository() {
        tracks.add(new Track(counter.incrementAndGet(), "Bohemian Rhapsody", "Queen", "A Night at the Opera", 355));
        tracks.add(new Track(counter.incrementAndGet(), "Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV", 482));
        tracks.add(new Track(counter.incrementAndGet(), "Hotel California", "Eagles", "Hotel California", 391));
    }

    public List<Track> findAll() {
        return tracks;
    }

    public Optional<Track> findById(Long id) {
        return tracks.stream().filter(t -> t.id.equals(id)).findFirst();
    }

    public Track addTrack(String title, String artist, String album, int duration) {
        Track newTrack = new Track(counter.incrementAndGet(), title, artist, album, duration);
        tracks.add(newTrack);
        return newTrack;
    }
}
