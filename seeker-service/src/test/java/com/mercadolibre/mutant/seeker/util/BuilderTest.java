package com.mercadolibre.mutant.seeker.util;

import com.mercadolibre.shared.dto.Dna;
import com.mercadolibre.shared.dto.SeekerServiceRequest;
import com.mercadolibre.shared.dto.StatsResponse;
import io.r2dbc.spi.Row;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BuilderTest {

    /**
     * Method to test return of builder when row in query is empty
     */
    @Test
    public void toStatsResponseWithEmptyRow(){
        Row row = null;
        Builder builder = new Builder();
        StatsResponse statsResponse = builder.toStatsResponse(row);
        Assert.assertEquals(null, statsResponse);

    }

    /**
     * Method to test return of builder when row in query return values
     */
    @Test
    public void toStatsResponse(){
        Builder builder = new Builder();

        Row row = Mockito.mock(Row.class);
        Mockito.when(row.get(Mockito.isA(String.class), Mockito.eq(Integer.class))).thenReturn(1);

        StatsResponse statsResponse = builder.toStatsResponse(row);
        Assert.assertEquals(1, statsResponse.getCountMutantDna().intValue());
        Assert.assertEquals(1, statsResponse.getCountHumanDna().intValue());
        Assert.assertEquals(1.0, statsResponse.getRatio().floatValue(), 0);

    }

    /**
     * Method to test return of builder when row in query return values in 0
     */
    @Test
    public void toStatsResponseWithValuesInZero(){
        Builder builder = new Builder();

        Row row = Mockito.mock(Row.class);
        Mockito.when(row.get(Mockito.isA(String.class), Mockito.eq(Integer.class))).thenReturn(0);

        StatsResponse statsResponse = builder.toStatsResponse(row);
        Assert.assertEquals(0, statsResponse.getCountMutantDna().intValue());
        Assert.assertEquals(0, statsResponse.getCountHumanDna().intValue());
        Assert.assertEquals(0.0, statsResponse.getRatio().floatValue(), 0);

    }

    /**
     * Method to test return of builder when build Dna object
     * in Request case null
     */
    @Test
    public void toDnaWithRequestNull(){
        Builder builder = new Builder();
        Dna dna = builder.toDna(null, true);
        Assert.assertEquals(null, dna);

    }


    /**
     * Method to test return of builder when build Dna object
     * in  normal case with flag mutant true
     */
    @Test
    public void toDnaWithFlagTrue(){
        Builder builder = new Builder();
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = new ArrayList<String>();
        lsStr.add("TEST");
        seekerServiceRequest.setDna(lsStr);
        Dna dna = builder.toDna(seekerServiceRequest, true);
        Assert.assertEquals(1, dna.getDna().size());
        Assert.assertEquals("TEST", dna.getDna().get(0));
        Assert.assertEquals(true, dna.getIsMutant());

    }

    /**
     * Method to test return of builder when build Dna object
     * in  normal case with flag mutant false
     */
    @Test
    public void toDnaWithFlagFalse(){
        Builder builder = new Builder();
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = new ArrayList<String>();
        lsStr.add("TEST1");
        seekerServiceRequest.setDna(lsStr);
        Dna dna = builder.toDna(seekerServiceRequest, false);
        Assert.assertEquals(1, dna.getDna().size());
        Assert.assertEquals("TEST1", dna.getDna().get(0));
        Assert.assertEquals(false, dna.getIsMutant());

    }

}
