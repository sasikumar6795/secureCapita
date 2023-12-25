package com.sasicodes.securecapita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SecurecapitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurecapitaApplication.class, args);
	}

}
