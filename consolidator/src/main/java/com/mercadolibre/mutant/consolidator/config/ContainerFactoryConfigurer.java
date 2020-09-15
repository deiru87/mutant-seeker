package com.mercadolibre.mutant.consolidator.config;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
class ContainerFactoryConfigurer {

    ContainerFactoryConfigurer(ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
        factory.getContainerProperties().setMissingTopicsFatal(false);
    }

}
