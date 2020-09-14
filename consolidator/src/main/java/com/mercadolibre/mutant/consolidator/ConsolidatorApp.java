package com.mercadolibre.mutant.consolidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsolidatorApp implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ConsolidatorApp.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsolidatorApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("\n\n\nreceiving messages for smishing analysis...\n\n\n");
    }
}
