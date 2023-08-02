package io.github.lucasalmeida.cafeconnection;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition
public class CafeConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeConnectionApplication.class, args);
    }



}
