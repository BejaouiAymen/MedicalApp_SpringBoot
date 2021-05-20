package com.userservice.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class UserServiceApplication {


	@GetMapping("/")
	public String login(){
		return "authenticated successfully" ;
	}

	@GetMapping("/getUsers")
	

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
