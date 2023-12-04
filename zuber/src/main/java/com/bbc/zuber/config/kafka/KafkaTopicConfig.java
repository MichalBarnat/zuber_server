package com.bbc.zuber.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Bean
    public NewTopic rideAssignmentTopic() {
        return TopicBuilder.name("ride-assignment")
                .build();
    }

    @Bean
    public NewTopic rideInfoTopic() {
        return TopicBuilder.name("ride-info")
                .build();
    }

}
