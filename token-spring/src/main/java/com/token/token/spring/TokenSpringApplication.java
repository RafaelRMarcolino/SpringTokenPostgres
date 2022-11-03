package com.token.token.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//excluide permite utilizar o espring security sem pedir as validações de usuario e senha
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TokenSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenSpringApplication.class, args);
	}

}
