package com.bohdanbest.ui.DTO;

public class PlaylistDto {
    public Long id;
    public String name;
    public java.util.List<Long> trackIds;

    public PlaylistDto() {}

    public PlaylistDto(String name) {
        this.name = name;
    }
}
