package com.mercadolibre.mutant.seeker.util;

import com.mercadolibre.shared.dto.Dna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * class where the messages are sent to the kafka broker
 *
 * @Author: Deimer Ballesteros
 */
@Component
public class EventQueue {
    private static final Logger logger = LoggerFactory.getLogger(EventQueue.class);


    private KafkaTemplate<String, Dna> kafkaSender;

    @Autowired
    public EventQueue(KafkaTemplate<String, Dna> kafkaSender) {
        this.kafkaSender = kafkaSender;
    }


    /**
     * Method used for send messages through topic messages in kafka. specifically the message
     * for save and consolidate dna information processed
     * @param dna - Dna Object
     * @param topic - Name of topic used for send message
     */
    public void queue(Dna dna, String topic){
        logger.debug("Sending Message to Kafka Topic for consolidate DNA processed: {} ", dna.toString());
        try {
            kafkaSender.send(topic, dna);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }



}
