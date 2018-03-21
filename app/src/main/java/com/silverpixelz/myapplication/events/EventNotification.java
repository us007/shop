package com.silverpixelz.myapplication.events;

import com.silverpixelz.myapplication.data.db.model.NotificationDB;

/**
 * Created by Dharmik Patel on 22-Nov-17.
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
