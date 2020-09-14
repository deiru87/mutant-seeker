package com.mercadolibre.mutant.seeker.dao;


import com.mercadolibre.shared.dto.StatsResponse;
import reactor.core.publisher.Mono;

/**
 * Interface with method to get statistic information of dna processed
 * @Author: Deimer Ballesteros
 */
public interface DnaDao {

    /**
     * Method that allow get information for the services stats.
     * @return Mono<StatsResponse> - Info Statistic of processed dna
     */
    Mono<StatsResponse> getStats();

}
