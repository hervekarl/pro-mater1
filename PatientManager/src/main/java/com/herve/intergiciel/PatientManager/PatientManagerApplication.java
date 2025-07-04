package com.herve.intergiciel.PatientManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class PatientManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientManagerApplication.class, args);
	}

}
