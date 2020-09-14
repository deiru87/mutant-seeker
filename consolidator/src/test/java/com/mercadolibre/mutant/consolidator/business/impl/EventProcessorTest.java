package com.mercadolibre.mutant.consolidator.business.impl;

import com.mercadolibre.mutant.consolidator.dao.DnaDao;
import com.mercadolibre.mutant.consolidator.util.JsonDeSerializer;
import com.mercadolibre.shared.dto.Dna;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import static org.mockito.Mockito.*;

public class EventProcessorTest {

    private final String  json = "{\n" +
            "\t\"dna\":[\"ATGCGT\",\"CAGTGC\",\"TTATGG\",\"AGAAGG\",\"CCCCAA\",\"TCACTG\"],\n" +
            "\t\"isMutant\": true\n" +
            "}";


    @Test
    public void receive(){
        DnaDao dnaDao = mock(DnaDao.class);
        EventProcessor eventProcessor = new EventProcessor(dnaDao);
        JsonDeSerializer jsonDeSerializer = new JsonDeSerializer();
        Mockito.when(dnaDao.saveDna(isA(Dna.class))).thenReturn(Mono.create(MonoSink::success));
        Dna dna = (Dna) jsonDeSerializer.deserialize(json, json.getBytes());
        eventProcessor.receive(dna);
        verify(dnaDao, times(1)).saveDna(Mockito.isA(Dna.class));

    }



}
