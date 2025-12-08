package com.bohdanbest.playlist.event;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class NotificationDeserializer extends ObjectMapperDeserializer<NotificationEvent> {
    public NotificationDeserializer() {
        super(NotificationEvent.class);
    }
}