package com.mercadolibre.mutant.seeker.util;

import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Class type Bean Spring used to route request between Validator
 * Object and business Logic
 *
 * @Author: Deimer Ballesteros
 */
@Component
public class RequestHandler {


    private final Validator validator;

    @Autowired
    public RequestHandler(Validator validator) {
        this.validator = validator;
    }


    /**
     * Method for process and route request through validation and business Login methods
     *
     * @param block - Function that trigger the business logic flow
     * @param request - ServerRequest object
     * @param bodyClass - Class that apply function
     * @param <BODY>
     * @return
     */
    public <BODY> Mono<ServerResponse> requireValidBody(
            Function<Mono<BODY>, Mono<ServerResponse>> block,
            ServerRequest request, Class<BODY> bodyClass) {

        return request.bodyToMono(SeekerServiceRequest.class)
                .flatMap(
                        body -> validator.validate(body).isEmpty()
                                ? block.apply(Mono.just(body).ofType(bodyClass))
                                : ServerResponse.badRequest().build()
                );
    }


}
