package com.mercadolibre.mutant.seeker.service.router;


import com.mercadolibre.mutant.seeker.service.SeekerMutantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

/**
 * Class Router of resources involved in the Rest Api Mutant
 * @Author: Deimer Ballesteros
 */
@Configuration
public class Router {

    /**
     * Method route of resources in Spring webFlux - style functional
     * @param seekerMutantService
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> route(SeekerMutantService seekerMutantService) {
        return RouterFunctions
                .route(RequestPredicates.POST("/v1/mutant")
                                .and(accept(MediaType.APPLICATION_JSON))
                                .and(contentType(MediaType.APPLICATION_JSON))
                                            , seekerMutantService::findMutant)
                .andRoute(RequestPredicates.POST("/v2/mutant")
                                .and(accept(MediaType.APPLICATION_JSON))
                                .and(contentType(MediaType.APPLICATION_JSON))
                                            , seekerMutantService::seekMutant)
                .andRoute(RequestPredicates.GET("/stats"), seekerMutantService::getStats);
    }


}