package com.bohdanbest.catalog;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import com.bohdanbest.catalog.grpc.*;

@GrpcService
public class CatalogGrpcServiceImpl implements CatalogGrpcService {

    @Inject
    InMemoryCatalogRepository catalogRepository;

    @Override
    public Uni<TrackInfo> addTrack(AddTrackRequest request) {
        Track newTrack = catalogRepository.addTrack(
                request.getTitle(),
                request.getArtist(),
                request.getAlbum(),
                request.getDurationSeconds()
        );

        TrackInfo trackInfo = TrackInfo.newBuilder()
                .setId(newTrack.id)
                .setTitle(newTrack.title)
                .setArtist(newTrack.artist)
                .setAlbum(newTrack.album)
                .setDurationSeconds(newTrack.durationSeconds)
                .build();

        return Uni.createFrom().item(trackInfo);
    }
}
