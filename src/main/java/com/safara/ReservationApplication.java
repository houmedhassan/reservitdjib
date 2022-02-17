package com.safara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservationApplication {

	
	
	public static void main(String[] args) {
		//System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}");
		SpringApplication.run(ReservationApplication.class, args);
	}

}
