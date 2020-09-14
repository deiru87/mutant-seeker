package com.mercadolibre.mutant.seeker.util;

import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class component Bean Spring to do validations in object request
 * @Author: Deimer Ballesteros
 */
@Component
public class Validator {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    @Value("${regex.letters.wrong}")
    private String regex;


    /**
     * Main method where is trigger all validation in the request object
     *
     * @param request
     * @return List - when is not empty mean validation fails
     */
    public List validate(SeekerServiceRequest request){
        log.debug("Validating request input");
        List<String> lsErrors = new ArrayList<String>();
        if(request == null || request.getDna() == null || request.getDna().isEmpty()) {
            lsErrors.add("a");
            return lsErrors;
        }
        String[] dna = new String[request.getDna().size()];
        dna = request.getDna().toArray(dna);
        lsErrors.add(validateEmptyMatrix(dna) ? "a" : "");
        lsErrors.add(validateLetters(dna) ? "a" : "");
        lsErrors.add(validateNxNMatrix(dna) ? "a" : "");
        log.debug("Finish process to validate request input");
        return lsErrors.stream().filter(o -> !o.isEmpty()).collect(Collectors.toList());

    }


    /**
     * Method used to validate when array String that conform matrix is empty
     * @param dna
     * @return boolean - represent error when true
     */
    private boolean validateEmptyMatrix(String[] dna){
        return dna.length <=0;
    }

    /**
     * Method used to validate when the array String contains letters not allow
     * @param dna
     * @return boolean - represent error when true
     */
    private boolean validateLetters(String[] dna){

        StringBuilder sb = new StringBuilder();
        Pattern pat = Pattern.compile(regex);

        Arrays.stream(dna).forEach(s -> {
            Matcher mat = pat.matcher(s);
            if(!mat.matches()){
                sb.append("a");
            }
        });

        return sb.length() > 0;
    }

    /**
     * Method used to validate if an array of String represent a matrix NxN
     * @param dna
     * @return  boolean - represent error when true
     */
    private boolean validateNxNMatrix(String[] dna){
        int n = dna.length;
        StringBuilder sb = new StringBuilder();
        Arrays.stream(dna).forEach(s -> {
            if(s.length() != n){
                sb.append("a");
            }
        });

        return sb.length() > 0;
    }

}
