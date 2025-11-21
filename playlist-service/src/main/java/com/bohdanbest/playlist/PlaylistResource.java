package com.bohdanbest.playlist;

import com.bohdanbest.playlist.dto.PlaylistResponseDto;
import com.bohdanbest.playlist.dto.TrackDto;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import com.bohdanbest.playlist.client.CatalogClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class PlaylistResource {

    @Inject SecurityIdentity identity;
    @Inject @RestClient CatalogClient catalogClient;

    @GET
    public List<Playlist> getMyPlaylists() {
        return Playlist.findByOwner(identity.getPrincipal().getName());
    }

    @POST
    @Transactional
    public Playlist create(Playlist playlist) {
        playlist.owner = identity.getPrincipal().getName();
        playlist.persist();
        return playlist;
    }

    @GET
    @Path("/{id}")
    public PlaylistResponseDto getById(@PathParam("id") Long id) {
        // Знаходимо плейлист
        Playlist playlist = Playlist.findById(id);
        if (playlist == null) throw new NotFoundException();

        // Перевірка прав доступу (власник)
        if (!playlist.owner.equals(identity.getPrincipal().getName())) {
            throw new ForbiddenException();
        }

        // Збираємо DTO
        PlaylistResponseDto response = new PlaylistResponseDto();
        response.id = playlist.id;
        response.name = playlist.name;
        response.tracks = new ArrayList<>();

        for (Long trackId : playlist.trackIds) {
            try {
                var track = catalogClient.getTrack(trackId);
                TrackDto tDto = new TrackDto();

                response.tracks.add(track);
            } catch (Exception e) {
            }
        }
        return response;
    }

    @POST
    @Path("/{id}/tracks")
    @Transactional
    public void addTrack(@PathParam("id") Long id, Long trackId) {
        // Перевірка існування в каталозі
        try { catalogClient.getTrack(trackId); }
        catch (Exception e) { throw new NotFoundException("Track not found in catalog"); }

        Playlist playlist = Playlist.findById(id);
        if (playlist == null || !playlist.owner.equals(identity.getPrincipal().getName())) {
            throw new NotFoundException();
        }

        // --- НОВА ЛОГІКА: ПЕРЕВІРКА НА ДУБЛІКАТИ ---
        if (playlist.trackIds.contains(trackId)) {
            // Повертаємо 409 Conflict
            throw new WebApplicationException("Track already exists in this playlist", 409);
        }

        playlist.trackIds.add(trackId);
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Transactional
    public void removeTrack(@PathParam("id") Long id, @PathParam("trackId") Long trackId) {
        Playlist playlist = Playlist.findById(id);

        if (playlist == null) {
            throw new NotFoundException();
        }

        // Перевірка прав власника
        if (!playlist.owner.equals(identity.getPrincipal().getName())) {
            throw new ForbiddenException();
        }

        playlist.trackIds.remove(trackId);

    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletePlaylist(@PathParam("id") Long id) {
        Playlist playlist = Playlist.findById(id);

        if (playlist == null) {
            throw new NotFoundException();
        }

        if (!playlist.owner.equals(identity.getPrincipal().getName())) {
            throw new ForbiddenException();
        }

        playlist.delete(); // Active Record видалення
    }

}