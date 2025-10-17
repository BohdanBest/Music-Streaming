package com.bohdanbest.playlist;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import com.bohdanbest.playlist.client.CatalogRestClient;
import com.bohdanbest.playlist.client.Track;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
public class PlaylistResource {

    @Inject
    InMemoryPlaylistRepository playlistRepository;

    @Inject
    @RestClient
    CatalogRestClient catalogRestClient;

    @GET
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @POST
    @Path("/{playlistId}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    public Playlist addTrackToPlaylist(@PathParam("playlistId") Long playlistId, TrackIdRequest request) {
        try {
            catalogRestClient.getTrackById(request.trackId);
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 404) {
                throw new NotFoundException("Track with id " + request.trackId + " not found in catalog.");
            }
            throw e;
        }

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NotFoundException("Playlist not found"));

        if (!playlist.trackIds.contains(request.trackId)) {
            playlist.trackIds.add(request.trackId);
        }

        return playlist;
    }

    public static class TrackIdRequest {
        public Long trackId;
    }
}