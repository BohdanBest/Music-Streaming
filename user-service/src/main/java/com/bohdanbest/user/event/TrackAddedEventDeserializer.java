package com.bohdanbest.user.event;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TrackAddedEventDeserializer extends ObjectMapperDeserializer<TrackAddedEvent> {
    public TrackAddedEventDeserializer() {
        super(TrackAddedEvent.class);
    }
}
