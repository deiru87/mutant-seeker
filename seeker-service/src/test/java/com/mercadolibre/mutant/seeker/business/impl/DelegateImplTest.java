package com.mercadolibre.mutant.seeker.business.impl;

import com.mercadolibre.mutant.seeker.business.MutantChecker;
import com.mercadolibre.mutant.seeker.business.MutantSeeker;
import com.mercadolibre.mutant.seeker.dao.DnaDao;
import com.mercadolibre.mutant.seeker.util.Builder;
import com.mercadolibre.mutant.seeker.util.EventQueue;
import com.mercadolibre.shared.dto.SeekerServiceRequest;
import com.mercadolibre.shared.dto.StatsResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;

public class DelegateImplTest {

    /**
     * Method to validate what happens in Delegate Class when is found a mutant
     */
    @Test
    public void findMutantWhenIsTrue(){
        MutantChecker mutantChecker = mock(MutantChecker.class);
        MutantSeeker mutantSeeker = mock(MutantSeeker.class);
        DnaDao dnaDao = mock(DnaDao.class);
        EventQueue eventQueue = mock(EventQueue.class);
        Builder builder = new Builder();
        DelegateImpl delegate = new DelegateImpl(mutantChecker, mutantSeeker, dnaDao, eventQueue, builder);
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lstStr = new ArrayList<>();
        lstStr.add("TEST");
        seekerServiceRequest.setDna(lstStr);
        Mockito.when(mutantChecker.validateMutant(isA(Mono.class))).thenReturn(Mono.just(true));
        Mono<ServerResponse> resp = delegate.findMutant(Mono.just(seekerServiceRequest));
        Assert.assertEquals(HttpStatus.OK.value(), Objects.requireNonNull(resp.block()).statusCode().value());


    }


    /**
     * Method to validate what happens in Delegate Class when is not found a mutant
     */
    @Test
    public void findMutantWhenIsFalse(){
        MutantChecker mutantChecker = mock(MutantChecker.class);
        MutantSeeker mutantSeeker = mock(MutantSeeker.class);
        DnaDao dnaDao = mock(DnaDao.class);
        EventQueue eventQueue = mock(EventQueue.class);
        Builder builder = new Builder();
        DelegateImpl delegate = new DelegateImpl(mutantChecker, mutantSeeker, dnaDao, eventQueue, builder);
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lstStr = new ArrayList<>();
        lstStr.add("TTTT");
        seekerServiceRequest.setDna(lstStr);
        Mockito.when(mutantChecker.validateMutant(isA(Mono.class))).thenReturn(Mono.just(false));
        Mono<ServerResponse> resp = delegate.findMutant(Mono.just(seekerServiceRequest));
        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), Objects.requireNonNull(resp.block()).statusCode().value());


    }


    /**
     * Method to validate what happens in Delegate Class when is found a mutant
     */
    @Test
    public void seekMutantWhenIsTrue(){
        MutantChecker mutantChecker = mock(MutantChecker.class);
        MutantSeeker mutantSeeker = mock(MutantSeeker.class);
        DnaDao dnaDao = mock(DnaDao.class);
        EventQueue eventQueue = mock(EventQueue.class);
        Builder builder = new Builder();
        DelegateImpl delegate = new DelegateImpl(mutantChecker, mutantSeeker, dnaDao, eventQueue, builder);
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lstStr = new ArrayList<>();
        lstStr.add("GGGG");
        seekerServiceRequest.setDna(lstStr);
        Mockito.when(mutantSeeker.validateMutant(isA(Mono.class))).thenReturn(Mono.just(true));
        Mono<ServerResponse> resp = delegate.seekMutant(Mono.just(seekerServiceRequest));
        Assert.assertEquals(HttpStatus.OK.value(), Objects.requireNonNull(resp.block()).statusCode().value());


    }


    /**
     * Method to validate what happens in Delegate Class when is not found a mutant
     */
    @Test
    public void seekMutantWhenIsFalse(){
        MutantChecker mutantChecker = mock(MutantChecker.class);
        MutantSeeker mutantSeeker = mock(MutantSeeker.class);
        DnaDao dnaDao = mock(DnaDao.class);
        EventQueue eventQueue = mock(EventQueue.class);
        Builder builder = new Builder();
        DelegateImpl delegate = new DelegateImpl(mutantChecker, mutantSeeker, dnaDao, eventQueue, builder);
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lstStr = new ArrayList<>();
        lstStr.add("AACC");
        seekerServiceRequest.setDna(lstStr);
        Mockito.when(mutantSeeker.validateMutant(isA(Mono.class))).thenReturn(Mono.just(false));
        Mono<ServerResponse> resp = delegate.seekMutant(Mono.just(seekerServiceRequest));
        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), Objects.requireNonNull(resp.block()).statusCode().value());

    }

    /**
     * Method to valida response in Delegate class when not found data to stats in dna.
     */
    @Test
    public void getStatsWhenResponseIsNull(){
        MutantChecker mutantChecker = mock(MutantChecker.class);
        MutantSeeker mutantSeeker = mock(MutantSeeker.class);
        DnaDao dnaDao = mock(DnaDao.class);
        EventQueue eventQueue = mock(EventQueue.class);
        Builder builder = new Builder();
        DelegateImpl delegate = new DelegateImpl(mutantChecker, mutantSeeker, dnaDao, eventQueue, builder);
        StatsResponse response = new StatsResponse();
        Mockito.when(dnaDao.getStats()).thenReturn(Mono.just(response));
        Mono<ServerResponse> resp = delegate.getStats();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), Objects.requireNonNull(resp.block()).statusCode().value());
    }


    /**
     * Method to valida response in Delegate class when found data to stats in dna.
     */
    @Test
    public void getStatsWhenResponse(){
        MutantChecker mutantChecker = mock(MutantChecker.class);
        MutantSeeker mutantSeeker = mock(MutantSeeker.class);
        DnaDao dnaDao = mock(DnaDao.class);
        EventQueue eventQueue = mock(EventQueue.class);
        Builder builder = new Builder();
        DelegateImpl delegate = new DelegateImpl(mutantChecker, mutantSeeker, dnaDao, eventQueue, builder);
        StatsResponse response = new StatsResponse();
        response.setRatio(1.0);
        response.setCountMutantDna(1);
        response.setCountHumanDna(1);
        Mockito.when(dnaDao.getStats()).thenReturn(Mono.just(response));
        Mono<ServerResponse> resp = delegate.getStats();
        Assert.assertEquals(HttpStatus.OK.value(), Objects.requireNonNull(resp.block()).statusCode().value());
    }



}
