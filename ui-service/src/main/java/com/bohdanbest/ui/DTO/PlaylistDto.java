package com.bohdanbest.ui.DTO;

public class PlaylistDto {
    public Long id;
    public String name;
    public java.util.List<Long> trackIds; // Список ID треків

    // Пустий конструктор для JSON десеріалізації
    public PlaylistDto() {}

    // Конструктор для створення
    public PlaylistDto(String name) {
        this.name = name;
    }
}
