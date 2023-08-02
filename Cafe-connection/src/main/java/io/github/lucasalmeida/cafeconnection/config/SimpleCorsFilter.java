package io.github.lucasalmeida.cafeconnection.config;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

public class SimpleCorsFilter extends CorsFilter {

    public SimpleCorsFilter() {
        super(configurationSource());
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
