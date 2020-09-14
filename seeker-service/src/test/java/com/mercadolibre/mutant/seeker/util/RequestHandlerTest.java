package com.mercadolibre.mutant.seeker.util;


import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Field;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class RequestHandlerTest {


    private static String json = "{\n" +
            "\t\"dna\" :  [\"ATGCGT\",\"CAGTGC\",\"TTATGG\",\"AGAAGG\",\"CCCYAA\",\"TCACTG\"]\n" +
            "}";

    private static String json1 = "{\n" +
            "\t\"dna\" :  [\"ATGCGT\",\"CAGTGC\",\"TTATGG\",\"AGAAGG\",\"CCCCAA\",\"TCACTG\"]\n" +
            "}";

    private static String json2 = "{\n" +
            "\t\"dna\" :  [\"ATGCGT\",\"CAGTGC\",\"TTATGG\",\"AGAAGG\",\"CCCCA\",\"TCACTG\"]\n" +
            "}";

    /**
     * Method to validate request body when Request Object is OK
     */
    @Test
    public void requireValidBodyTest() throws NoSuchFieldException, IllegalAccessException {
        Validator validator = new Validator();
        Field fieldRegex = validator.getClass().getDeclaredField("regex");
        fieldRegex.setAccessible(true);
        fieldRegex.set(validator, "(A|C|T|G)+");
        RequestHandler requestHandler = new RequestHandler(validator);
        MockServerHttpRequest mock = MockServerHttpRequest.post("https://example.com").contentType(MediaType.APPLICATION_JSON).body(json1);

        MockServerWebExchange exchange = MockServerWebExchange.from(mock);
        ServerRequest serverRequest =
                ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        Mono<ServerResponse> monoResponse = requestHandler.requireValidBody(body ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).body(fromObject("ok"))
                ,serverRequest, SeekerServiceRequest.class );
        StepVerifier.create(monoResponse).assertNext(serverResponse -> {
            Assert.assertEquals(HttpStatus.OK.value(), serverResponse.statusCode().value());
        }).verifyComplete();

    }

    /**
     * Method to validate request body when Request Object is Wrong
     */
    @Test
    public void requireValidBodyWrongTest() throws NoSuchFieldException, IllegalAccessException {
        Validator validator = new Validator();
        Field fieldRegex = validator.getClass().getDeclaredField("regex");
        fieldRegex.setAccessible(true);
        fieldRegex.set(validator, "(A|C|T|G)+");
        RequestHandler requestHandler = new RequestHandler(validator);
        MockServerHttpRequest mock = MockServerHttpRequest.post("https://example.com").contentType(MediaType.APPLICATION_JSON).body(json);
        MockServerWebExchange exchange = MockServerWebExchange.from(mock);
        ServerRequest serverRequest =
                ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        Mono<ServerResponse> monoResponse = requestHandler.requireValidBody( body ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject("ok"))
                ,serverRequest, SeekerServiceRequest.class );
        StepVerifier.create(monoResponse).assertNext(serverResponse -> {
            Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), serverResponse.statusCode().value());
        }).verifyComplete();

    }

    /**
     * Method to validate request body when Request Object is Wrong matrix NxN wrong
     */
    @Test
    public void requireValidBodyWrongWithInternalValueTest() throws NoSuchFieldException, IllegalAccessException {
        Validator validator = new Validator();
        Field fieldRegex = validator.getClass().getDeclaredField("regex");
        fieldRegex.setAccessible(true);
        fieldRegex.set(validator, "(A|C|T|G)+");
        RequestHandler requestHandler = new RequestHandler(validator);
        MockServerHttpRequest mock = MockServerHttpRequest.post("https://localhost.com").contentType(MediaType.APPLICATION_JSON).body(json2);
        MockServerWebExchange exchange = MockServerWebExchange.from(mock);
        ServerRequest serverRequest =
                ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        Mono<ServerResponse> monoResponse = requestHandler.requireValidBody( body ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject("All Fine"))
                ,serverRequest, SeekerServiceRequest.class );
        StepVerifier.create(monoResponse).assertNext(serverResponse -> {
            Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), serverResponse.statusCode().value());
        }).verifyComplete();

    }



}
