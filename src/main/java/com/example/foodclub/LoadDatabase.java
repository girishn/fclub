package com.example.foodclub;

import com.example.foodclub.service.FoodClubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(FoodClubService foodClubService) {

        return args -> {
            log.info("Preloading data...");
            foodClubService.initData();
        };
    }
}