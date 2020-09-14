package com.mercadolibre.mutant.seeker.business.impl;


import com.mercadolibre.mutant.seeker.business.Delegate;
import com.mercadolibre.mutant.seeker.business.MutantChecker;
import com.mercadolibre.mutant.seeker.business.MutantSeeker;
import com.mercadolibre.mutant.seeker.dao.DnaDao;
import com.mercadolibre.mutant.seeker.util.Builder;
import com.mercadolibre.mutant.seeker.util.EventQueue;
import com.mercadolibre.shared.dto.Dna;
import com.mercadolibre.shared.dto.SeekerServiceRequest;
import com.mercadolibre.shared.dto.StatsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Class with delegate methods that use the services Mutant and Stats
 *
 * @Author: Deimer Ballesteros
 */
@Component
public class DelegateImpl implements Delegate {

    private static Logger log = LoggerFactory.getLogger(DelegateImpl.class);
    private MutantChecker mutantChecker;
    private MutantSeeker mutantSeeker;
    private DnaDao dnaDao;
    private EventQueue eventQueue;
    private Builder builder;

    @Value("${topic.consolidator}")
    private String topic;

    @Autowired
    public DelegateImpl(MutantChecker mutantChecker, MutantSeeker mutantSeeker, DnaDao dnaDao, EventQueue eventQueue, Builder builder){
        this.mutantChecker = mutantChecker;
        this.mutantSeeker = mutantSeeker;
        this.dnaDao = dnaDao;
        this.eventQueue = eventQueue;
        this.builder = builder;
    }

    /**
     * Method that invoke method to validate if an array of String correspond with a mutant.
     * if the sequence o dna is mutant then return Http OK Response. if not is mutan then return
     * Http FORBIDDEN Response
     * @param seekerServiceRequest - Request with dna sequences
     * @return Mono<ServerResponse>
     */
    @Override
    public Mono<ServerResponse> findMutant(Mono<SeekerServiceRequest> seekerServiceRequest) {
        log.debug("Searching mutant by V1 version method");
        return mutantChecker.validateMutant(seekerServiceRequest).flatMap(isMutant -> {
            sendStatsConsolidator(builder.toDna(seekerServiceRequest.block(), isMutant));
            log.debug("Result of searching mutant for dna {} was {}", Objects.requireNonNull(seekerServiceRequest.block()).getDna() , isMutant);
            if(isMutant)
                return ServerResponse.ok().build();
            return ServerResponse.status(403).build();
        });
    }

    /**
     * Method that invoke method to validate if an array of String correspond with a mutant.
     * if the sequence o dna is mutant then return Http OK Response. if not is mutan then return
     * Http FORBIDDEN Response
     * @param seekerServiceRequest - Request with dna sequences
     * @return Mono<ServerResponse>
     */
    @Override
    public Mono<ServerResponse> seekMutant(Mono<SeekerServiceRequest> seekerServiceRequest) {
        log.debug("Searching mutant by V2 version method");
        return mutantSeeker.validateMutant(seekerServiceRequest).flatMap(isMutant -> {
            sendStatsConsolidator(builder.toDna(seekerServiceRequest.block(), isMutant));
            log.debug("Result of searching mutant for dna {} was {}", Objects.requireNonNull(seekerServiceRequest.block()).getDna() , isMutant);
            if(isMutant){
                return ServerResponse.ok().build();
            }
            return ServerResponse.status(403).build();
        });
    }


    /**
     * Method used to get the statistic information of the dna processed.
     * if found information in the database in the table "consolidate_dna"
     * return that information. if not found information return Http NO_CONTENT Response
     * @return Mono<ServerResponse>
     */
    @Override
    public Mono<ServerResponse> getStats() {
        log.debug("Getting statistic information");
        return dnaDao.getStats().flatMap(statsResponse -> {
            if(statsResponse == null || statsResponse.getCountHumanDna() == null) {
                log.info("No content available in DB");
                return ServerResponse.noContent().build();
            } else {
                log.info("Found statistical information");
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body((Mono.just(statsResponse)),StatsResponse.class);
            }
        });
    }


    /**
     * Method use for send information of a sequence of dna (Is mutant, sequence dna) to
     * Topic message kafka for this messages will be process for consolidator component
     *
     * @param dna
     */
    private void sendStatsConsolidator(Dna dna){
        log.info("Sending event report to consolidate dna : {}", dna.toString());
        eventQueue.queue(dna, topic);
    }


}
