package com.herve.intergiciel.mygateway;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Collections;

import reactor.core.publisher.Mono;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Content-Disposition"));
        config.setMaxAge(3600L);
        config.setAllowCredentials(false); // Must be false when using *

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("*"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setAllowCredentials(false);
                    return config;
                })
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public WebFilter duplicateHeadersFilter() {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.beforeCommit(() -> {
                HttpHeaders headers = response.getHeaders();
                if (headers.getAccessControlAllowOrigin() != null &&
                        headers.getAccessControlAllowOrigin().contains("*")) {
                    headers.setAccessControlAllowOrigin("*"); // Force une seule valeur
                }
                return Mono.empty();
            });
            return chain.filter(exchange);
        };
    }
}