package com.bohdanbest.ui.clients;

import com.bohdanbest.ui.DTO.PlaylistDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "playlist-api") // Вказуємо ключ для налаштування URL в application.properties
@io.quarkus.oidc.token.propagation.common.AccessToken // Автоматично додає заголовок Authorization: Bearer ...
@Path("/playlists") // Базовий шлях контролера в playlist-service
public interface PlaylistClient {

    @GET
    List<PlaylistDto> getAll();

    @POST
    void create(PlaylistDto playlist);
}
