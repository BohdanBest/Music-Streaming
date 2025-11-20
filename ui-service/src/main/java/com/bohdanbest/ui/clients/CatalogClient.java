package com.bohdanbest.ui.clients;


import com.bohdanbest.ui.DTO.TrackDto;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "catalog-api") // Вказуємо ключ для налаштування URL
@AccessToken // Пропагуємо безпеку
@Path("/tracks") // Базовий шлях контролера в catalog-service
public interface CatalogClient {

    // Пошук треків за виконавцем (query param "artist")
    // Сигнатура методу в CatalogResource була: getAll(@QueryParam("artist") String artist)
    @GET
    List<TrackDto> search(@QueryParam("artist") String query);
}
