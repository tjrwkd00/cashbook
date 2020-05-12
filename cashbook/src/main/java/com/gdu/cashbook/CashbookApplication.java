package com.gdu.cashbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.gdu.cashbook") == @SpringBootApplication
public class CashbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}

}
