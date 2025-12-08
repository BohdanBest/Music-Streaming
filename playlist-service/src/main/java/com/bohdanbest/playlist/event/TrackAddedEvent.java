package com.bohdanbest.playlist.event;

public class TrackAddedEvent {
    public Long playlistId;
    public Long trackId;
    public String userId;
    public long timestamp;

    public TrackAddedEvent() {}

    public TrackAddedEvent(Long playlistId, Long trackId, String userId) {
        this.playlistId = playlistId;
        this.trackId = trackId;
        this.userId = userId;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "TrackAddedEvent{playlistId=" + playlistId + ", trackId=" + trackId + ", userId='" + userId + "'}";
    }
}
