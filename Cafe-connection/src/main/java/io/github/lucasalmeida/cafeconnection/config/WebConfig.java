package io.github.lucasalmeida.cafeconnection.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, dateFormatter);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return dateFormatter.format(object);
            }
        });
    }
}
