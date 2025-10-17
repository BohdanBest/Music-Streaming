package com.bohdanbest.cli;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import com.bohdanbest.catalog.grpc.AddTrackRequest;
import com.bohdanbest.catalog.grpc.CatalogGrpcService;
import com.bohdanbest.catalog.grpc.TrackInfo;

@QuarkusMain
public class CatalogCliCommand implements QuarkusApplication {

    @GrpcClient("catalog")
    CatalogGrpcService catalogClient;

    @Override
    public int run(String... args) throws Exception {
        if (args.length < 4) {
            System.err.println("Usage: java -jar catalog-cli-runner.jar <title> <artist> <album> <duration_seconds>");
            return 1;
        }

        String title = args[0];
        String artist = args[1];
        String album = args[2];
        int duration = Integer.parseInt(args[3]);

        AddTrackRequest request = AddTrackRequest.newBuilder()
                .setTitle(title)
                .setArtist(artist)
                .setAlbum(album)
                .setDurationSeconds(duration)
                .build();

        System.out.println("Sending request to add track: " + title);

        TrackInfo response = catalogClient.addTrack(request).await().indefinitely();

        System.out.println("Successfully added track! Response from server:");
        System.out.println(response);

        return 0;
    }
}
