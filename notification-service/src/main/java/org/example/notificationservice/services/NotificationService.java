package org.example.notificationservice.services;

import jakarta.mail.MessagingException;
import org.example.notificationservice.api.dto.MessageDto;


public interface NotificationService {
    void sendMessage(MessageDto messageDto) throws MessagingException;
}
