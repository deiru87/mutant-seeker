package com.mercadolibre.mutant.seeker.business;

import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Interface That contain methods to allow validate if a dna sequences is mutant.
 * It's use for V2 of services mutant
 * @Author: Deimer Ballesteros
 */
public interface MutantSeeker {

    /**
     * Method that validate if an array of string correspond with a mutant
     *
     * @param seekerServiceRequest - Request with sequence dna
     * @return boolean that represent if sequence is mutant or not
     */
    Mono<Boolean> validateMutant(Mono<SeekerServiceRequest> seekerServiceRequest);

}
