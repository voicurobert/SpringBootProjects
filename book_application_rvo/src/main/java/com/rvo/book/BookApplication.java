package com.rvo.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;


@SpringBootApplication
@Controller
public class BookApplication{
	public static void main( String[] args ){
		SpringApplication.run( BookApplication.class, args );
	}
}
