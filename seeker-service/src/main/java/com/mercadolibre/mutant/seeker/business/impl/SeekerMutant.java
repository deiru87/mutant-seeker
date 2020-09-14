package com.mercadolibre.mutant.seeker.business.impl;

import com.mercadolibre.mutant.seeker.business.MutantSeeker;
import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class That contain business logic to validate if a dna sequences is mutant.
 * It's use for V2 of services mutant
 * @Author: Deimer Ballesteros
 */
@Component
public class SeekerMutant implements MutantSeeker {

    private final static Logger log = LoggerFactory.getLogger(SeekerMutant.class);

    @Value("${regex.pattern.word.dna}")
    private String regex;


    /**
     * Method that validate if an array of string correspond with a mutant
     *
     * @param seekerServiceRequest - Request with sequence dna
     * @return boolean that represent if sequence is mutant or not
     */
    @Override
    public Mono<Boolean> validateMutant(Mono<SeekerServiceRequest> seekerServiceRequest) {
        return seekerServiceRequest.map(request -> {
            String[] dna = new String[request.getDna().size()];
            dna = request.getDna().toArray(dna);
            log.debug("checking if  dna sequence {} is mutant", Arrays.toString(dna));
            return isMutant(dna);
        });
    }


    /**
     * Method that search in a matrixNxN represented by an array of string if
     * there is mutant sequence. This method search each row, columns and diagonal directs and inverse in the matrix
     * and compare with a regex pattern to establish if there is more than one sequence repeat.
     * @param dna
     * @return boolean - mean if is mutant or not
     */
    private boolean isMutant(String[] dna){
        int amountDnaMutant = 0;
        log.debug("initial amount of sequence mutant found is {}", amountDnaMutant);
        amountDnaMutant = horizontalSearch(dna);
        amountDnaMutant += verticalSearch(dna, amountDnaMutant);
        amountDnaMutant += diagonalSearch(dna, amountDnaMutant);

        return amountDnaMutant > 1;
    }


    /**
     * Method to search in all rows sequence repeat
     * @param dna
     * @return int - amount o sequences found
     */
    private int horizontalSearch(String[] dna){
        return searchPattern(dna);
    }


    /**
     * Method to search in all Columns sequence repeat
     * also validate if the amount o sequence was reached
     * @param dna
     * @param amountDnaMutant - accumulated account
     * @return int - amount o sequences found
     */
    private int verticalSearch(String[] dna, int amountDnaMutant){
        log.debug("amount of sequence mutant found is {}", amountDnaMutant);
        if(amountDnaMutant > 1)
            return amountDnaMutant;
        String[] newDna = new String[dna.length];
        Stream.iterate(0, n -> n+1).limit(dna.length).forEach(idx -> {
            newDna[idx] =  Arrays.stream(dna)
                    .map(s -> s.charAt(idx))
                    .collect(Collectors.toList())
                    .stream()
                    .collect(Collector.of(
                            StringBuilder::new,
                            StringBuilder::append,
                            StringBuilder::append,
                            StringBuilder::toString));
        });

        return searchPattern(newDna);
    }

    /**
     * Method to search in all Diagonals, sequence repeat
     * also validate if the amount o sequence was reached
     * @param dna
     * @param amountDnaMutant - accumulated account
     * @return int - amount o sequences found
     */
    private int diagonalSearch(String[] dna, int amountDnaMutant){
        log.debug("amount of sequence mutant found is {}", amountDnaMutant);
        if(amountDnaMutant > 1)
            return amountDnaMutant;
        String[] d1 = extractAllInvDiagonal(dna);
        String[] d2 = extractAllDiagonal(dna);
        String[] newDna = new String[d1.length + d2.length];
        System.arraycopy(d1, 0, newDna, 0, d1.length);
        System.arraycopy(d2, 0, newDna, d1.length, d2.length);
        return searchPattern(newDna);
    }


    /**
     * Main method to search through regex pattern if an array String contains
     * sequence that allow establish if is mutant
     *
     * @param dna
     * @return int - amount o sequences found
     */
    private int searchPattern(String[] dna){
        Pattern p = Pattern.compile(regex);
        StringBuilder dd = new StringBuilder("");
        int amountFound = 0;

        Arrays.stream(dna).forEach(s -> {
            if(s != null){
                Matcher m = p.matcher(s);
                while(m.find()){
                    dd.append(m.group());
                }

            }

        });

        if(dd.length()==4){
            amountFound = 1;
        }else if(dd.length() > 4){
            amountFound = dd.length() / 4;
        }

        return amountFound;
    }


    /**
     * Method to extract all diagonal in a matrix
     * @param dna
     * @return String[] - sequence of values in diagonal of matrix
     */
    private String[] extractAllDiagonal(String[] dna) {
        int i=0;
        int j=0;
        List<String> lsDg = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int k=0; k <= dna.length-1; k++){
            i=k;
            j=dna.length-1;
            while(i>=0){
                sb.append(dna[i].charAt(j));
                i -= 1;
                j -= 1;
            }
            lsDg.add(sb.toString());
            sb.setLength(0);

        }

        for (int k=dna.length -2; k >= 0 ; k--){
            i=dna.length-1;
            j=k;
            while(j>=0){
                sb.append(dna[i].charAt(j));
                i -= 1;
                j -= 1;
            }
            lsDg.add(sb.toString());
            sb.setLength(0);

        }

        String[] itemsArray = new String[lsDg.size()];

        return lsDg.stream()
                .filter(s -> s.length()>=4).collect(Collectors.toList()).toArray(itemsArray);

    }


    /**
     * Method to extract all diagonal invert in a matrix
     * @param dna
     * @return String[] - sequence of values in diagonal of matrix
     */
    private String[] extractAllInvDiagonal(String[] dna) {
        int i=0;
        int j=0;
        List<String> lsDg = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int k=0; k <= dna.length-1; k++){
            i=k;
            j=0;
            while(i>=0){
                sb.append(dna[i].charAt(j));
                i -= 1;
                j += 1;
            }
            lsDg.add(sb.toString());
            sb.setLength(0);

        }

        for (int k=1; k <= dna.length -1; k++){
            i=dna.length-1;
            j=k;
            while(j<=dna.length-1){
                sb.append(dna[i].charAt(j));
                i -= 1;
                j += 1;
            }
            lsDg.add(sb.toString());
            sb.setLength(0);

        }

        String[] itemsArray = new String[lsDg.size()];

        return lsDg.stream()
                .filter(s -> s.length()>=4).collect(Collectors.toList()).toArray(itemsArray);

    }



}
