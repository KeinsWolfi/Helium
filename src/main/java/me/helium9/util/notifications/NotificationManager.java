package me.helium9.util.notifications;

import lombok.Getter;

import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {

    @Getter
    private static final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();

    public static void post(NotificationType type, String title, String description, int durationMS) {
        post(new Notification(type, title, description, durationMS));
    }

    private static void post(Notification notification) {
        notifications.add(notification);
    }

}
