package com.bohdanbest.ui;
import com.bohdanbest.ui.DTO.PlaylistDetailDto;
import com.bohdanbest.ui.DTO.PlaylistDto;
import com.bohdanbest.ui.DTO.TrackDto;
import com.bohdanbest.ui.clients.CatalogClient;
import com.bohdanbest.ui.clients.PlaylistClient;
import com.bohdanbest.ui.clients.UserClient;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.logging.Log;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class HomeResource {

    @Inject Template index;
    @Inject SecurityIdentity identity; // Для перевірки ролей

    @Inject @RestClient
    UserClient userClient;
    @Inject @RestClient PlaylistClient playlistClient;
    @Inject @RestClient CatalogClient catalogClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("search") String search,
                                @QueryParam("playlistId") Long playlistId,
                                @QueryParam("error") String error) {

        boolean isAdmin = identity.getRoles().contains("admin") || identity.getPrincipal().getName().equals("admin");
        String searchQuery = (search == null || search.isBlank()) ? "" : search;

        // 1. Отримуємо деталі вибраного плейлиста (якщо є ID)
        PlaylistDetailDto selectedPlaylist = null;
        if (playlistId != null) {
            try {
                selectedPlaylist = playlistClient.getById(playlistId);
            } catch (Exception e) {
                // ігноруємо помилку, якщо плейлист не знайдено
            }
        }

        List<TrackDto> recommendations = null;
        if (playlistId == null && searchQuery.isEmpty()) {
            try {
                recommendations = catalogClient.getRecommendations();
            } catch (Exception e) {
                // Якщо catalog-service не відповідає або метод ще не реалізований
                recommendations = new ArrayList<>();
            }
        }

        // 3. Шукаємо треки (якщо ввели запит)
        List<TrackDto> tracks = null;
        if (!searchQuery.isEmpty()) {
            try {
                tracks = catalogClient.search(searchQuery);
            } catch (Exception e) {
                tracks = new ArrayList<>();
            }
        }

        // 4. Повертаємо ВСІ дані в шаблон
        return index.data("user", userClient.getMe())
                .data("playlists", playlistClient.getAll())
                .data("tracks", tracks)
                .data("recommendations", recommendations) // <--- ОСЬ ЦЕ ВАЖЛИВО!
                .data("isAdmin", isAdmin)
                .data("selectedPlaylist", selectedPlaylist)
                .data("error", error);
    }
    // --- Створення плейлиста ---
    @POST
    @Path("/playlist/create")
    public RestResponse<Object> createPlaylist(@FormParam("name") String name) {
        playlistClient.create(new PlaylistDto(name));
        return RestResponse.seeOther(URI.create("/")); // Перезавантаження сторінки
    }

    // --- Додавання треку в плейлист (ЮЗЕР) ---
    @POST
    @Path("/playlist/add-track")
    public RestResponse<Object> addTrackToPlaylist(@FormParam("playlistId") Long playlistId,
                                                   @FormParam("trackId") Long trackId) {
        try {
            playlistClient.addTrack(playlistId, trackId);
            // Успіх: повертаємося на сторінку цього плейлиста
            return RestResponse.seeOther(URI.create("/?playlistId=" + playlistId));
        } catch (ClientWebApplicationException e) {
            if (e.getResponse().getStatus() == 409) {
                // Дублікат: повертаємося з помилкою
                return RestResponse.seeOther(URI.create("/?playlistId=" + playlistId + "&error=DuplicateTrack"));
            }
            throw e;
        }
    }

    // --- Додавання нового треку в БД (АДМІН) ---
    @POST
    @Path("/admin/track/create")
    public RestResponse<Object> createTrack(@FormParam("title") String title,
                                            @FormParam("artist") String artist,
                                            @FormParam("album") String album) {
        TrackDto track = new TrackDto();
        track.title = title;
        track.artist = artist;
        track.album = album;
        catalogClient.create(track);
        return RestResponse.seeOther(URI.create("/"));
    }

    @POST
    @Path("/admin/track/delete")
    public RestResponse<Object> deleteTrack(@FormParam("trackId") Long trackId) {
        catalogClient.delete(trackId);
        return RestResponse.seeOther(URI.create("/"));
    }

    @POST
    @Path("/playlist/remove-track")
    public RestResponse<Object> removeTrackFromPlaylist(@FormParam("playlistId") Long playlistId,
                                                        @FormParam("trackId") Long trackId) {
        playlistClient.removeTrack(playlistId, trackId);
        // Залишаємося на сторінці того ж плейлиста
        return RestResponse.seeOther(URI.create("/?playlistId=" + playlistId));
    }

    // --- Видалення плейлиста ---
    @POST
    @Path("/playlist/delete")
    public RestResponse<Object> deletePlaylist(@FormParam("playlistId") Long playlistId) {
        playlistClient.delete(playlistId);
        // Повертаємося на головну (скидаємо вибір плейлиста)
        return RestResponse.seeOther(URI.create("/"));
    }

    @POST
    @Path("/admin/track/update")
    public RestResponse<Object> updateTrack(@FormParam("trackId") Long trackId,
                                            @FormParam("title") String title,
                                            @FormParam("artist") String artist,
                                            @FormParam("album") String album) {
        TrackDto track = new TrackDto();
        track.title = title;
        track.artist = artist;
        track.album = album;

        catalogClient.update(trackId, track);

        return RestResponse.seeOther(URI.create("/"));
    }
}