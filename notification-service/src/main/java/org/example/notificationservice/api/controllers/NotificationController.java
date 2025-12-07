package org.example.notificationservice.api.controllers;

import jakarta.mail.MessagingException;
import org.example.notificationservice.api.dto.MessageDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/v1/notification")
public interface NotificationController {
    @PostMapping
    void sendMessage(@RequestBody MessageDto messageDto) throws MessagingException;
}