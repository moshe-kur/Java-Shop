package com.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	private static final Logger logger = LoggerFactory.getLogger(ShopApplication.class);


	public static void main(String[] args) {
		logger.info("ddd");

		SpringApplication.run(ShopApplication.class, args);
	}

}
