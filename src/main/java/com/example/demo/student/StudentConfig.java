package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student luigi = new Student("Luigi",
                    "greenguy@nin.com",
                    LocalDate.of(1985, Month.AUGUST, 8));

            Student daisy = new Student("Diasy",
                    "daisy@nin.com",
                    LocalDate.of(1995, Month.JULY, 18));

            repository.saveAll(List.of(luigi, daisy));  // saving students
        };
    }
}
