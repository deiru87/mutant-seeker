package com.mercadolibre.mutant.consolidator.dao;


import com.mercadolibre.shared.dto.Dna;
import reactor.core.publisher.Mono;

/**
 * Interface for operation save information of dna process
 * @Author: Deimer Ballesteros
 */
public interface DnaDao {

    /**
     * Method that save information related to dna processed
     * @param dna
     * @return
     */
    Mono<Void> saveDna(Dna dna);

}
