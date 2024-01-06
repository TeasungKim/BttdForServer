package com.finalproject.bttd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class BttdApplication {


	public static void main(String[] args) {


		SpringApplication.run(BttdApplication.class, args);
	//	String dateString = "2023-12-20 14:00";
	//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	//	LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

	}

}
