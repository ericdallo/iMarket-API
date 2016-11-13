package br.com.imarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class IMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(IMarketApplication.class, args);
	}
}
