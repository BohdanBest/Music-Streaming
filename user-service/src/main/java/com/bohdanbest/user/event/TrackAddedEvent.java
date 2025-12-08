package com.bohdanbest.user.event;

public class TrackAddedEvent {
    public Long playlistId;
    public Long trackId;
    public String userId;
    public long timestamp;

    public TrackAddedEvent() {}

    @Override
    public String toString() {
        return "TrackAddedEvent{" +
                "playlistId=" + playlistId +
                ", trackId=" + trackId +
                ", userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
