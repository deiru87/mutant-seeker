package com.mercadolibre.mutant.seeker.business;


import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Interface with methods use in the services mutant and stats
 * @Author: Deimer Ballesteros
 */
public interface Delegate {

    /**
     * Method that trigger process for recognize a mutant. it´s used
     * for service Mutant V1.
     *
     * @param seekerServiceRequest - Request with dna sequences
     * @return Mono<ServerResponse> - Response in webFlux
     */
    Mono<ServerResponse> findMutant(Mono<SeekerServiceRequest> seekerServiceRequest);
    /**
     * Method that trigger process for recognize a mutant. it´s used
     * for service Mutant V2.
     *
     * @param seekerServiceRequest - Request with dna sequences
     * @return Mono<ServerResponse> - Response in webFlux
     */
    Mono<ServerResponse> seekMutant(Mono<SeekerServiceRequest> seekerServiceRequest);

    /**
     * Method that get statistic information of dna processed
     * @return Mono<ServerResponse> - Response in webFlux. Contains statistic information
     */
    Mono<ServerResponse> getStats();


}
