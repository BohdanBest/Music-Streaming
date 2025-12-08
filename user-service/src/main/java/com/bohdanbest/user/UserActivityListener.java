package com.bohdanbest.user;

import com.bohdanbest.user.event.NotificationEvent;
import com.bohdanbest.user.event.TrackAddedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserActivityListener {

    private static final Logger LOG = Logger.getLogger(UserActivityListener.class);

    // Патерн PROCESSOR: Отримує з 'track-added-in', відправляє в 'notifications-out'
    @Incoming("track-added-in")
    @Outgoing("notifications-out")
    public NotificationEvent onTrackAdded(TrackAddedEvent event) {
        LOG.infof("🔄 PROCESSING: User '%s' added track %d. Generating notification...",
                event.userId, event.trackId);

        // Формуємо нову подію (сповіщення)
        String msg = String.format("User %s successfully updated playlist %d", event.userId, event.playlistId);
        return new NotificationEvent(msg, event.userId);
    }
}
