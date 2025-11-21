package com.bohdanbest.ui.clients;

import com.bohdanbest.ui.DTO.PlaylistDetailDto;
import com.bohdanbest.ui.DTO.PlaylistDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "playlist-api")
@io.quarkus.oidc.token.propagation.common.AccessToken
@Path("/playlists")
public interface PlaylistClient {

    @GET
    List<PlaylistDto> getAll();

    @GET
    @Path("/{id}")
    PlaylistDetailDto getById(@PathParam("id") Long id);

    @POST
    void create(PlaylistDto playlist);

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    void addTrack(@PathParam("id") Long playlistId, Long trackId);

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    void removeTrack(@jakarta.ws.rs.PathParam("id") Long playlistId,
                     @jakarta.ws.rs.PathParam("trackId") Long trackId);

    @DELETE
    @Path("/{id}")
    void delete(@jakarta.ws.rs.PathParam("id") Long id);
}
