package com.bohdanbest.playlist;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Playlist extends PanacheEntity {
    public String name;
    public String owner;

    @ElementCollection(fetch = FetchType.EAGER)
    public List<Long> trackIds = new ArrayList<>();

    public static List<Playlist> findByOwner(String owner) {
        return list("owner", owner);
    }

    public static class TrackIdRequest {
        public Long trackId;
    }
}