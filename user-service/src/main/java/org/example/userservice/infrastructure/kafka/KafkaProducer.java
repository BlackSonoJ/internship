package org.example.userservice.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.example.userservice.infrastructure.kafka.events.MessageDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    public void sendMessage(String topic, MessageDto message) {
        kafkaTemplate.send(topic, message);
    }
}

