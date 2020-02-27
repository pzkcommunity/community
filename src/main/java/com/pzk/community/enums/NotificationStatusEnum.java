package com.pzk.community.enums;

import com.pzk.community.model.Notification;

/**
 * notification status
 */
public enum NotificationStatusEnum {
    READ(1),
    UNREAD(0);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
