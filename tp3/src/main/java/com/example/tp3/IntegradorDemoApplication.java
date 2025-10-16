package com.example.tp3;

import com.example.tp3.utils.CSVLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntegradorDemoApplication implements CommandLineRunner {

    @Autowired
    private CSVLoaderService csvLoaderService;

    public static void main(String[] args) {
        SpringApplication.run(IntegradorDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        csvLoaderService.populateDB();
    }
}
