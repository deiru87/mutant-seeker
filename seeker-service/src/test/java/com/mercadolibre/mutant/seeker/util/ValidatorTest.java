package com.mercadolibre.mutant.seeker.util;

import com.mercadolibre.shared.dto.SeekerServiceRequest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ValidatorTest {

    /**
     * Method to test validation methods when request matrix is null
     *
     */
    @Test
    public void validateMatrixNull(){
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = null;
        Validator validator = new Validator();
        List lstErrors = validator.validate(seekerServiceRequest);
        Assert.assertEquals(1, lstErrors.size());

    }

    /**
     * Method to test validation methods when request matrix is empty
     *
     */
    @Test
    public void validateMatrixEmpty(){
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = new ArrayList<>();
        Validator validator = new Validator();
        seekerServiceRequest.setDna(lsStr);
        List lstErrors = validator.validate(seekerServiceRequest);
        Assert.assertEquals(1, lstErrors.size());

    }

    /**
     * Method to test validation methods when request matrix NxN is Wrong
     *
     */
    @Test
    public void validateMatrixNxNWrong() throws NoSuchFieldException, IllegalAccessException {
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = new ArrayList<>();
        lsStr.add("TG");
        seekerServiceRequest.setDna(lsStr);
        Validator validator = new Validator();
        Field fieldRegex = validator.getClass().getDeclaredField("regex");
        fieldRegex.setAccessible(true);
        fieldRegex.set(validator, "(A|C|T|G)+");
        List lstErrors = validator.validate(seekerServiceRequest);
        Assert.assertEquals(1, lstErrors.size());

    }

    /**
     * Method to test validation methods when request matrix NxN is Wrong with more data
     *
     */
    @Test
    public void validateMatrixNxNWrongMore() throws NoSuchFieldException, IllegalAccessException {
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = new ArrayList<>();
        lsStr.add("TGAA");
        lsStr.add("TGA");
        lsStr.add("TGTT");
        lsStr.add("TGTT");
        seekerServiceRequest.setDna(lsStr);
        Validator validator = new Validator();
        Field fieldRegex = validator.getClass().getDeclaredField("regex");
        fieldRegex.setAccessible(true);
        fieldRegex.set(validator, "(A|C|T|G)+");
        List lstErrors = validator.validate(seekerServiceRequest);
        Assert.assertEquals(1, lstErrors.size());

    }

    /**
     * Method to test validation methods when request matrix NxN is Ok
     *
     */
    @Test
    public void validateMatrixNxN() throws NoSuchFieldException, IllegalAccessException {
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = new ArrayList<>();
        lsStr.add("TTGG");
        lsStr.add("AACC");
        lsStr.add("TTTT");
        lsStr.add("TTAA");
        seekerServiceRequest.setDna(lsStr);
        Validator validator = new Validator();
        Field fieldRegex = validator.getClass().getDeclaredField("regex");
        fieldRegex.setAccessible(true);
        fieldRegex.set(validator, "(A|C|T|G)+");
        List lstErrors = validator.validate(seekerServiceRequest);
        Assert.assertEquals(0, lstErrors.size());

    }

    /**
     * Method to test validation methods when request matrix NxN that
     * contains wrong letters
     *
     */
    @Test
    public void validateMatrixNxNWithLettersWrong() throws NoSuchFieldException, IllegalAccessException {
        SeekerServiceRequest seekerServiceRequest = new SeekerServiceRequest();
        List<String> lsStr = new ArrayList<>();
        lsStr.add("TTHH");
        lsStr.add("AAKK");
        lsStr.add("LLTT");
        lsStr.add("TTAA");
        seekerServiceRequest.setDna(lsStr);
        Validator validator = new Validator();
        Field fieldRegex = validator.getClass().getDeclaredField("regex");
        fieldRegex.setAccessible(true);
        fieldRegex.set(validator, "(A|C|T|G)+");
        List lstErrors = validator.validate(seekerServiceRequest);
        Assert.assertEquals(1, lstErrors.size());

    }


}
