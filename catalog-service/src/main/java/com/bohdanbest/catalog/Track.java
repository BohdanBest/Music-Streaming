package com.bohdanbest.catalog;

public class Track {
    public Long id;
    public String title;
    public String artist;
    public String album;
    public int durationSeconds;

    public Track(Long id, String title, String artist, String album, int durationSeconds) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.durationSeconds = durationSeconds;
    }
}
