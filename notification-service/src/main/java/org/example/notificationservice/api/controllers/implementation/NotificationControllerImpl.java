package org.example.notificationservice.api.controllers.implementation;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.notificationservice.api.controllers.NotificationController;
import org.example.notificationservice.api.dto.MessageDto;
import org.example.notificationservice.services.NotificationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService notificationService;


    @Override
    public void sendMessage(@RequestBody MessageDto messageDto) throws MessagingException {
        notificationService.sendMessage(messageDto);
    }
}
