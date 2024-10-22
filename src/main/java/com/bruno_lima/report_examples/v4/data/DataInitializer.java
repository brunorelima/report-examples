package com.bruno_lima.report_examples.v4.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void run(String... args) throws Exception {
        countryRepository.save(new Country(1, "Portugal"));
        countryRepository.save(new Country(2, "Italy"));
        countryRepository.save(new Country(3, "Croatia"));
    }
}
