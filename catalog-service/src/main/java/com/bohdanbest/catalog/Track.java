package com.bohdanbest.catalog;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Track extends PanacheEntity {
    public String title;
    public String artist;
    public String album;

    public static java.util.List<Track> findByArtist(String artist) {
        return list("LOWER(artist) LIKE LOWER(?1)", "%" + artist + "%");
    }
}
