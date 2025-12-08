package com.bohdanbest.catalog;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.bohdanbest.catalog.grpc.*; // Ваші згенеровані gRPC класи

@GrpcService
public class CatalogGrpcServiceImpl implements CatalogGrpcService {

    @Override
    @Transactional
    public Uni<TrackInfo> addTrack(AddTrackRequest request) {
        Track track = new Track();
        track.title = request.getTitle();
        track.artist = request.getArtist();
        track.album = request.getAlbum();


        track.persist();

        TrackInfo response = TrackInfo.newBuilder()
                .setId(track.id)
                .setTitle(track.title)
                .setArtist(track.artist)
                .setAlbum(track.album)
                .build();

        return Uni.createFrom().item(response);
    }
}