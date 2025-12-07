package org.example.notificationservice.infrastructure.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.notificationservice.api.dto.MessageDto;
import org.example.notificationservice.common.enums.Operation;
import org.example.notificationservice.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventsListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "user.event", groupId = "notification-group")
    public void handleUserEvent(MessageDto message) throws MessagingException {
        System.out.println("Received Message: " + message);

        switch (message.operation()) {
            case CREATE -> handleUserCreated(message.email());
            case DELETE -> handleUserDeleted(message.email());
        }
    }

    private void handleUserCreated(String email) throws MessagingException {
        notificationService.sendMessage(
                new MessageDto(
                        Operation.CREATE,
                        email));
    }

    private void handleUserDeleted(String email) throws MessagingException {
        notificationService.sendMessage(
                new MessageDto(
                        Operation.DELETE,
                        email));
    }
}
