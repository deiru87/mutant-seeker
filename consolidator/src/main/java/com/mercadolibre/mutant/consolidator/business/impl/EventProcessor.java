package com.mercadolibre.mutant.consolidator.business.impl;


import com.mercadolibre.mutant.consolidator.dao.DnaDao;
import com.mercadolibre.shared.dto.Dna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

/**
 * Class that consume message from kafka broker
 *
 * @Author: Deimer Ballesteros
 */
@Component
public class EventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);

    private DnaDao dnaDao;

    @Autowired
    public EventProcessor(DnaDao dnaDao) {
        this.dnaDao = dnaDao;
    }


    /**
     * Method with listener that take message from topic kafka and start the process to persisted information about
     * dna.
     * @param dna
     * @return
     */
    @KafkaListener(topics = "${kafka.events.topic}",  containerFactory = "kafkaListenerContainerFactory")
    public Mono<Void> receive(Dna dna){
         logger.debug("received message: {}", dna.toString());
        dnaDao.saveDna(dna).subscribe();
        return Mono.create(MonoSink::success);
    }


}
