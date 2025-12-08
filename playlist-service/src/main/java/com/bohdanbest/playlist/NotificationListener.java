package com.bohdanbest.playlist;

import jakarta.enterprise.context.ApplicationScoped;
import com.bohdanbest.playlist.event.NotificationEvent;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NotificationListener {

    private static final Logger LOG = Logger.getLogger(NotificationListener.class);

    @Incoming("notifications-in")
    public void onNotification(NotificationEvent event) {
        LOG.infof("✅ CONFIRMATION RECEIVED: %s", event.message);
    }
}
