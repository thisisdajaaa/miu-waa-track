package com.daja.waa_server_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WaaServerLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaaServerLabApplication.class, args);
	}

}
