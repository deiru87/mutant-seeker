package com.mercadolibre.mutant.consolidator.dao.impl;


import com.mercadolibre.mutant.consolidator.dao.DnaDao;
import com.mercadolibre.shared.dto.Dna;
import io.r2dbc.spi.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * Class for operation save information of dna process
 * @Author: Deimer Ballesteros
 */
@Repository
public class DnaDaoImpl implements DnaDao {

    private static final Logger logger = LoggerFactory.getLogger(DnaDaoImpl.class);

    private static final String INSERT_DNA = "insert into dna( dna_code, mutant, human" +
            ") values (:dna, :mutant, :human)" +
            " on conflict(dna_code) do nothing";


    private ConnectionFactory connectionFactory;
    private DatabaseClient databaseClient;

    public DnaDaoImpl(@Qualifier("connectionConsolidator")ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void setUp(){
        databaseClient = DatabaseClient.create(connectionFactory);
    }

    /**
     * Method that save information related to dna processed
     * @param dna
     * @return
     */
    @Override
    public Mono<Void> saveDna(Dna dna) {

        String dnaCode = StringUtils.join(dna.getDna(), "");

         return databaseClient.execute().sql(INSERT_DNA)
                .bind("dna", dnaCode)
                .bind("mutant", dna.getIsMutant())
                .bind("human", !dna.getIsMutant()).then().doOnError(throwable -> {
             logger.error("Error inserting DNA table: {}", throwable.getMessage());
         }).doOnSuccess(aVoid -> {logger.info("saving info of dna {}", dnaCode);});

    }
}
