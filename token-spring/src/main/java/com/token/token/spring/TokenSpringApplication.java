package com.token.token.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//excluide permite utilizar o espring security sem pedir as validações de usuario e senha
@SpringBootApplication
public class TokenSpringApplication {

	public static void main(String[] args) {

		SpringApplication.run(TokenSpringApplication.class, args);
	}

	// ben para ser um componente da aplicação
	@Bean
	public PasswordEncoder getPasswordEncolder(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}


}
