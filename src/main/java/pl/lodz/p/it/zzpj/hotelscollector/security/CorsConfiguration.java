package pl.lodz.p.it.zzpj.hotelscollector.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer(@Value("${cors.allowed-origin-patterns}") Set<String> allowedOriginPatterns) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns(allowedOriginPatterns.toArray(String[]::new))
                        .allowedHeaders("*")
                        .allowedMethods("GET", "PUT", "PATCH", "POST")
                        .exposedHeaders("*");
            }
        };
    }
}