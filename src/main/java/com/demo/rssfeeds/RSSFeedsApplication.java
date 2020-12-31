package com.demo.rssfeeds;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * A Spring boot Application to handle our RSSFeeds exercise
 */
@SpringBootApplication
public class RSSFeedsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(RSSFeedsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
		return args -> {
			System.out.println(
					"The RSS Feeds Demo application is running. Access the Swagger Documentation with your favorite internet browser at http://localhost:8080/swagger-ui/");
		};
	}
}
