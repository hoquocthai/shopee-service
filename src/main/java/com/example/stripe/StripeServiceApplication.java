package com.example.stripe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.stripe.config.StripeProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({ StripeProperties.class })
@EnableJpaRepositories
public class StripeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(StripeServiceApplication.class, args);
	}
}
