package com.bohdanbest.catalog;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/tracks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class CatalogResource {

    @GET
    public List<Track> getAll(@QueryParam("artist") String artist) {
        if (artist != null && !artist.isBlank()) {
            return Track.findByArtist(artist);
        }
        return Track.listAll();
    }

    @POST
    @Transactional
    @RolesAllowed("admin") // Тільки адмін може додавати через REST
    public Track add(Track track) {
        track.persist();
        return track;
    }

    @GET
    @Path("/{id}")
    public Track getById(@PathParam("id") Long id) {
        return Track.findById(id);
    }
    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin") // Тільки адмін може змінювати
    public Track update(@PathParam("id") Long id, Track newTrack) {
        // Active Record стиль: шукаємо об'єкт
        Track entity = Track.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        // Оновлюємо поля
        entity.title = newTrack.title;
        entity.artist = newTrack.artist;
        entity.album = newTrack.album;

        // Метод persist() не потрібен, оскільки ми в транзакції і об'єкт керований Hibernate
        return entity;
    }

    @GET
    @Path("/recommendations")
    public List<Track> getRecommendations() {
        return Track.findAll().list();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin") // Тільки адмін може видаляти
    public void delete(@PathParam("id") Long id) {
        // Active Record стиль: видалення за ID
        boolean deleted = Track.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
    }
}
