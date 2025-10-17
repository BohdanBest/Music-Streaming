package com.bohdanbest.playlist;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public Long id;
    public String name;
    public Long userId;
    public List<Long> trackIds = new ArrayList<>();

    public Playlist(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
}
