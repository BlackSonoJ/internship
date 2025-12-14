package org.example.userservice.configs.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final int NUMBER_OF_PARTITIONS = 1;
    private final short NUMBER_OF_REPLICAS = 1;

    @Bean
    public NewTopic userEventsTopic() {
        return new NewTopic("user.event", NUMBER_OF_PARTITIONS, NUMBER_OF_REPLICAS);
    }
}
