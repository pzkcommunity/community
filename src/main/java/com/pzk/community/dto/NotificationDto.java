package com.pzk.community.dto;

import com.pzk.community.model.Notification;
import lombok.Data;

@Data
public class NotificationDto {
    private Long questionId;
    private String title;
    private String notifierName;
    private Notification notification;
}
