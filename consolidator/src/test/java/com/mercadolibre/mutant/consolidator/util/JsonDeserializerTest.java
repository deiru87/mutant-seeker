package com.mercadolibre.mutant.consolidator.util;

import com.mercadolibre.shared.dto.Dna;
import org.junit.Assert;
import org.junit.Test;


public class JsonDeserializerTest {


    private final String  json = "{\n" +
            "  \"dna\":  [\"AATTTG\"],\n" +
            "  \"isMutant\":  true\n" +
            "}";

    private final String  json1 = "{\n" +
            "  \"dna\":  [\"AATTTG\"],\n" +
            "  \"isMutant\":  true,\n" +
            "}";

    /**
     * Method to validate correct deserializer behavioral
     */
    @Test
    public void deserialize(){
        JsonDeSerializer jsonDeSerializer = new JsonDeSerializer();
        Dna dna = (Dna) jsonDeSerializer.deserialize(json, json.getBytes());
        Assert.assertEquals(1, dna.getDna().size());
        Assert.assertEquals(true, dna.getIsMutant());

    }

    /**
     * Method to validate deserializer behavioral when json is wrong
     */
    @Test
    public void deserializeWithWrongJSon(){
        JsonDeSerializer jsonDeSerializer = new JsonDeSerializer();
        Dna dna = (Dna) jsonDeSerializer.deserialize(json1, json1.getBytes());
        Assert.assertNull(dna);

    }


    /**
     * Method to validate deserializer behavioral when json is Empty
     */
    @Test
    public void deserializeWithEmptyJSon(){
        JsonDeSerializer jsonDeSerializer = new JsonDeSerializer();
        Dna dna = (Dna) jsonDeSerializer.deserialize("", "".getBytes());
        Assert.assertNull(dna);

    }

}
