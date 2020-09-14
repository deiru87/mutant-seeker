package com.mercadolibre.mutant.consolidator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.shared.dto.Dna;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Class to deserialize JSON that travel in topic Kafka
 * @param <T>
 * @Author: Deimer Ballesteros
 */
public class JsonDeSerializer<T> implements Deserializer<T> {

    private static Logger logger = LoggerFactory.getLogger(JsonDeSerializer.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // default implementation ignored
    }

    /**
     * Method to desearialize json in object
     * @param s
     * @param bytes
     * @return
     */
    @Override
    public T deserialize(String s, byte[] bytes) {
        Dna dna = null;
        try {
            dna = mapper.readValue(bytes, Dna.class);
        } catch (Exception e) {
            logger.error("Error des-serialized EventInQueue");
        }
        return (T) dna;

    }

    @Override
    public void close() {
        // default implementation ignored
    }

}
