package com.herve.intergiciel.mygateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class MygatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MygatewayApplication.class, args);
	}

}
