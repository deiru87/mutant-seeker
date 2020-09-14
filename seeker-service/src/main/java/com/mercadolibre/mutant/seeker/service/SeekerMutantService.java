package com.mercadolibre.mutant.seeker.service;

import com.mercadolibre.mutant.seeker.business.Delegate;
import com.mercadolibre.mutant.seeker.util.RequestHandler;
import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 ** Class component Bean Spring that contains business login methods of the
 *  service Mutant
 *
 ** @Author: Deimer Ballesteros
 */
@Component
public class SeekerMutantService {

    private RequestHandler requestHandler;
    private Delegate delegate;

    @Autowired
    public SeekerMutantService(RequestHandler requestHandler, Delegate delegate){
        this.requestHandler = requestHandler;
        this.delegate = delegate;
    }

    /**
     * Method that use the business logic for find mutants use in the Mutant service.V2
     *
     * @param request - Incoming request
     * @return Mono<ServerResponse> - Object response in Reactive Webflux
     */
    public Mono<ServerResponse> seekMutant(ServerRequest request) {
        return requestHandler.requireValidBody(body -> delegate.seekMutant(body)
                                                 ,request, SeekerServiceRequest.class);
    }

    /**
     * Method that use the business logic for find mutants use in the Mutant service.V1
     * @param request - Incoming request
     * @return Mono<ServerResponse> - Object response in Reactive Webflux
     */
    public Mono<ServerResponse> findMutant(ServerRequest request){
        return requestHandler.requireValidBody(body -> delegate.findMutant(body)
                ,request, SeekerServiceRequest.class);
    }

    /**
     * Method that use the business logic for find statistics information of dna processed
     * @param request - Incoming request
     * @return  Mono<ServerResponse> - Object response in Reactive Webflux
     */
    public Mono<ServerResponse> getStats(ServerRequest request){
        return delegate.getStats();
    }


}