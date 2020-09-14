package com.mercadolibre.mutant.seeker.business.impl;

import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class FinderMutantTest {




    /**
     * Method to validate that FinderMutant not found a mutant in a matrix with
     * only a sequence of 4 letters
     */
    @Test
    public void validateMutantWhenMatrixOnlyHaveOneSequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("ATCG");
        lstDna.add("TAGC");
        lstDna.add("GTAC");
        lstDna.add("GTGA");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(false, resp.block());

    }


    /**
     * Method to validate that FinderMutant not found a mutant in a matrix with
     * none sequence of 4 letters
     */
    @Test
    public void validateMutantWhenMatrixNoneSequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("ATCG");
        lstDna.add("TAGC");
        lstDna.add("GTAC");
        lstDna.add("GTGT");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(false, resp.block());

    }


    /**
     * Method to validate that FinderMutant  found a mutant in a matrix with
     * with two sequence of 4 letters
     */
    @Test
    public void validateMutantWhenMatrixWithTwoSequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("AAAA");
        lstDna.add("TAGC");
        lstDna.add("GTAC");
        lstDna.add("GTGA");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(true, resp.block());

    }


    /**
     * Method to validate that FinderMutant  found a mutant in a matrix with
     * with multiples sequence of 4 letters
     */
    @Test
    public void validateMutantWhenMatrixWithSequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("ATGCGA");
        lstDna.add("CAGTGC");
        lstDna.add("TTATGT");
        lstDna.add("AGAAGG");
        lstDna.add("CCCCTA");
        lstDna.add("TCACTG");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(true, resp.block());

    }


    /**
     * Method to validate that FinderMutant Not found a mutant in a matrix with
     * only a sequence of 4 letters diagonal
     */
    @Test
    public void validateMutantWhenMatrixWithOneSequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("ATGCGA");
        lstDna.add("CAGTGC");
        lstDna.add("TTATAT");
        lstDna.add("AGAAGG");
        lstDna.add("CCTCAA");
        lstDna.add("TCACTG");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(false, resp.block());

    }


    /**
     * Method to validate that FinderMutant Not found a mutant in a matrix with
     * only a sequence of 4 letters diagonal inverse
     */
    @Test
    public void validateMutantWhenMatrixWith1Sequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("ATGCGA");
        lstDna.add("CAGAGC");
        lstDna.add("TTATAT");
        lstDna.add("AAACGG");
        lstDna.add("CCTCAA");
        lstDna.add("TCACTG");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(false, resp.block());

    }


    /**
     * Method to validate that FinderMutant Not found a mutant in a matrix with
     * multiples sequences of 4 letters in horizontal way
     */
    @Test
    public void validateMutantWhenMatrixWith3Sequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("AAAAGA");
        lstDna.add("CAGAGC");
        lstDna.add("AATTTT");
        lstDna.add("AAACGG");
        lstDna.add("CCCCAA");
        lstDna.add("TCACTG");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(true, resp.block());

    }


    /**
     * Method to validate that FinderMutant Not found a mutant in a matrix with
     * multiples sequences of 4 letters in vertical way
     */
    @Test
    public void validateMutantWhenMatrixVerticalSequence(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("AATAGA");
        lstDna.add("AATAGC");
        lstDna.add("AATTGT");
        lstDna.add("AATCGG");
        lstDna.add("CCCCAA");
        lstDna.add("TCACTG");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(true, resp.block());

    }

    /**
     * Method to validate that FinderMutant Not found a mutant in a matrix with
     * multiples sequences of 4 letters same letter
     */
    @Test
    public void validateMutantWhenMatrixEqual(){
        FinderMutant finderMutant = new FinderMutant();
        SeekerServiceRequest request = new SeekerServiceRequest();
        List<String> lstDna = new ArrayList<>();
        lstDna.add("AAAAAA");
        lstDna.add("AAAAAA");
        lstDna.add("AAAAAA");
        lstDna.add("AAAAAA");
        lstDna.add("AAAAAA");
        lstDna.add("AAAAAA");
        request.setDna(lstDna);
        Mono<SeekerServiceRequest> seekerServiceRequest = Mono.just(request);

        Mono<Boolean> resp = finderMutant.validateMutant(seekerServiceRequest);
        Assert.assertEquals(true, resp.block());

    }

}
