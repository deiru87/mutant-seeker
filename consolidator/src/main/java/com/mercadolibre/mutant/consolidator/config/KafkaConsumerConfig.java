package com.mercadolibre.mutant.consolidator.config;

import com.mercadolibre.mutant.consolidator.util.JsonDeSerializer;
import com.mercadolibre.shared.dto.Dna;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Class That enable Kafka and allow a consumer to connect
 *
 * @Author: Deimer Ballesteros
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {


    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.enable.auto.commit}")
    private String enableAutoCommit;
    @Value("${kafka.auto.commit.interval}")
    private String autoCommitIntervalMsConfig;
    @Value("${kafka.session.timeout}")
    private String sessionTimeoutMsConfig;
    @Value("${kafka.auto.offset.reset}")
    private String autoOffsetResetConfig;
    @Value("${group.id}")
    private String groupId;
    @Value("${kafka.client.id}")
    private String clientId;
    @Value("${kafka.events.topic}")
    private String topic;
    @Value("${kafka.amount.consumers}")
    private int consumers;
    @Value("${heartbeat.config.ms}")
    private String heartbeatMsConfig;


    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMsConfig);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMsConfig);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeSerializer.class);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatMsConfig);

        return props;
    }


    @Bean
    public ConsumerFactory<Integer, Dna> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, Dna>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, Dna> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(consumers);
        return factory;
    }


}

