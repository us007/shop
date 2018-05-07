package com.flipboard.myapplication.events;

import com.flipboard.myapplication.data.db.model.NotificationDB;

/**
 */

public class EventNotification {

    public EventNotification(NotificationDB notificationDB) {
        this.notificationDB = notificationDB;
    }

    public NotificationDB getNotificationDB() {
        return notificationDB;
    }

    public EventNotification setNotificationDB(NotificationDB notificationDB) {
        this.notificationDB = notificationDB;
        return this;
    }

    private NotificationDB notificationDB;
}
