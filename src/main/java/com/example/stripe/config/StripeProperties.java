package com.example.stripe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix="application", ignoreInvalidFields=true)
@Getter
@Setter
public class StripeProperties{
    private final JWT jwt = new JWT();
    private final CorsConfiguration cors = new CorsConfiguration();

    @Getter
    @Setter
    public static class JWT {
        private String base64Secret;
        private long tokenValidityInSeconds;
    }
}
