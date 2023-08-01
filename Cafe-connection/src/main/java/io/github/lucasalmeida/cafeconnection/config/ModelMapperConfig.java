package io.github.lucasalmeida.cafeconnection.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addConverter(context -> {
            if (context.getSource() == null) {
                return null;
            }
            java.time.LocalDate date = (java.time.LocalDate) context.getSource();
            return date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        });

        return modelMapper;
    }
}
