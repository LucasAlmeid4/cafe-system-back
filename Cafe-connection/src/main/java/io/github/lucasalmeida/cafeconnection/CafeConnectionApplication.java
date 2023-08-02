package io.github.lucasalmeida.cafeconnection;

import io.github.lucasalmeida.cafeconnection.config.SimpleCorsFilter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@OpenAPIDefinition
public class CafeConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeConnectionApplication.class, args);
    }

    @Bean
    public SimpleCorsFilter corsFilter() {
        return new SimpleCorsFilter();
    }

}
