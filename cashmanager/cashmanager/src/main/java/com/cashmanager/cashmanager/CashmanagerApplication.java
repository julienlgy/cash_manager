package com.cashmanager.cashmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CashmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashmanagerApplication.class, args);
	}

}
