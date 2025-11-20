package com.bohdanbest.ui;
import com.bohdanbest.ui.DTO.PlaylistDto;
import com.bohdanbest.ui.clients.CatalogClient;
import com.bohdanbest.ui.clients.PlaylistClient;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class HomeResource {
    @Inject Template index;
    @Inject @RestClient
    PlaylistClient playlistClient;
    @Inject @RestClient
    CatalogClient catalogClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("search") String search) {
        return index.data("playlists", playlistClient.getAll())
                .data("tracks", search != null ? catalogClient.search(search) : null);
    }

    @POST
    @Path("/create-playlist")
    public void createPlaylist(@FormParam("name") String name) {
        playlistClient.create(new PlaylistDto(name));
    }
}