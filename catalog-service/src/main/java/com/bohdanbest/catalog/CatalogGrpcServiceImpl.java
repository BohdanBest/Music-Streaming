package com.bohdanbest.catalog;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.bohdanbest.catalog.grpc.*; // Ваші згенеровані gRPC класи

@GrpcService
public class CatalogGrpcServiceImpl implements CatalogGrpcService {

    @Override
    @Transactional // Обов'язково: відкриває транзакцію для запису в базу даних
    public Uni<TrackInfo> addTrack(AddTrackRequest request) {
        // 1. Створюємо нову сутність Track (Active Record)
        Track track = new Track();
        track.title = request.getTitle();
        track.artist = request.getArtist();
        track.album = request.getAlbum();

        // Якщо у вашій Entity класі Track є поле duration, розкоментуйте:
        // track.duration = request.getDurationSeconds();

        // 2. Зберігаємо в базу даних (PostgreSQL)
        track.persist();

        // 3. Формуємо відповідь (gRPC об'єкт)
        // ID генерується автоматично під час persist(), тому ми можемо його тут дістати (track.id)
        TrackInfo response = TrackInfo.newBuilder()
                .setId(track.id)
                .setTitle(track.title)
                .setArtist(track.artist)
                .setAlbum(track.album)
                // .setDurationSeconds(track.duration)
                .build();

        // 4. Повертаємо результат як Uni (асинхронно)
        return Uni.createFrom().item(response);
    }
}