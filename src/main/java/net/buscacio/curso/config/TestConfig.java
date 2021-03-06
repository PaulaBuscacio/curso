package net.buscacio.curso.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.buscacio.curso.services.DBService;
import net.buscacio.curso.services.EmailService;
import net.buscacio.curso.services.MockEmailService;

@Configuration
@Profile("teste")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean 
	public EmailService emailService() {
		return new MockEmailService();
		
	}

}
