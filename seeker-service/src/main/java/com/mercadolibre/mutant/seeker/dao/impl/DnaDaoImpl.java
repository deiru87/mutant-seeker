package com.mercadolibre.mutant.seeker.dao.impl;


import com.mercadolibre.mutant.seeker.dao.DnaDao;
import com.mercadolibre.mutant.seeker.util.Builder;
import com.mercadolibre.shared.dto.StatsResponse;
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * Class of type Bean - Repository in Spring Framework.
 * This class allow to connected to Databases and the rest operations
 * necessaries to extract data.
 *
 * @Author: Deimer Ballesteros
 */
@Repository
public class DnaDaoImpl implements DnaDao {

    private static final Logger logger = LoggerFactory.getLogger(DnaDaoImpl.class);

    private static final String SELECT_STATS = "select amount_mutant, amount_human from consolidate_dna";


    private ConnectionFactory connectionFactory;
    private DatabaseClient databaseClient;
    private Builder builder;

    public DnaDaoImpl(@Qualifier("connectionDna")ConnectionFactory connectionFactory, Builder builder) {
        this.connectionFactory = connectionFactory;
        this.builder = builder;
    }

    @PostConstruct
    public void setUp(){
        databaseClient = DatabaseClient.create(connectionFactory);
    }


    /**
     * Method that allow get information for the services stats.
     * @return Mono<StatsResponse> - Info Statistic of processed dna
     */
    @Override
    public Mono<StatsResponse> getStats() {
        return databaseClient.execute().sql(SELECT_STATS)
                .map((row, rowMetadata) -> {
                    final StatsResponse statsResponse = builder.toStatsResponse(row);
                    return Mono.just(statsResponse);
                }).first()
                  .flatMap(statsResponseMono -> statsResponseMono)
                  .doOnError(throwable -> logger.error("Error getting stats: {}", throwable.getMessage()));
    }
}
