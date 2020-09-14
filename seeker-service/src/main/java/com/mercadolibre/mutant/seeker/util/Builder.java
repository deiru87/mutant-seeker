package com.mercadolibre.mutant.seeker.util;

import com.mercadolibre.shared.dto.Dna;
import com.mercadolibre.shared.dto.SeekerServiceRequest;
import com.mercadolibre.shared.dto.StatsResponse;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Component;

/**
 * Class of type Bean - Component in Spring Framework.
 * This class allow build distinct objects that are necessaries
 *
 * @Author: Deimer Ballesteros
 */
@Component
public class Builder {


    /**
     * Method for build object StatsResponse taking the data come from the dataBase query
     * @param row - Row got from databases
     * @return StatsResponse
     */
    public StatsResponse toStatsResponse(Row row){
        StatsResponse statsResponse = null;
        if(row != null){
            statsResponse = new StatsResponse();
            statsResponse.setCountMutantDna(row.get("amount_mutant", Integer.class));
            statsResponse.setCountHumanDna(row.get("amount_human", Integer.class));
            statsResponse.setRatio(statsResponse.getCountHumanDna() > 0 ?
                                     ((float)statsResponse.getCountMutantDna()/statsResponse.getCountHumanDna()) :
                                      0.0);

        }

        return statsResponse;

    }


    /**
     * Method for build object Dna taking the data come from the Object Request and value boolean
     *  got of searcher mutant.
     * @param request - Object come from request for the mutant service
     * @param isMutant - Boolean value
     * @return Dna
     */
    public Dna toDna(SeekerServiceRequest request, boolean isMutant){
        Dna dna = null;
        if(request != null){
            dna = new Dna();
            dna.setDna(request.getDna());
            dna.setIsMutant(isMutant);
        }

        return dna;
    }





}
