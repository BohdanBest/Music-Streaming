package com.bohdanbest.playlist;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import com.bohdanbest.playlist.client.CatalogClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
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

    @POST
    @Path("/{id}/tracks")
    @Transactional
    public Playlist addTrack(@PathParam("id") Long id, Long trackId) {

        try { catalogClient.getTrack(trackId); }
        catch (Exception e) { throw new NotFoundException("Track not found"); }

        // 2. Додавання
        Playlist playlist = Playlist.findById(id);
        if (playlist == null || !playlist.owner.equals(identity.getPrincipal().getName())) {
            throw new NotFoundException();
        }
        playlist.trackIds.add(trackId);
        return playlist;
    }
}