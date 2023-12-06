package com.nguyenquocthai.real_time_tracker.Model;

public class NotificationItem {
    private String message;
    private String timestamp;
    private int iconResource; // Optional, if you want to show different icons for different notifications

    public NotificationItem(String message, String timestamp, int iconResource) {
        this.message = message;
        this.timestamp = timestamp;
        this.iconResource = iconResource;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}
