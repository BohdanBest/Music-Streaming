package com.bohdanbest.user.event;

public class NotificationEvent {
    public String message;
    public String userId;

    public NotificationEvent() {}

    public NotificationEvent(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "NotificationEvent{message='" + message + "', userId='" + userId + "'}";
    }
}
