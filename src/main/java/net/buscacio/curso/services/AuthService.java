package net.buscacio.curso.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.buscacio.curso.domain.Cliente;
import net.buscacio.curso.repositories.ClienteRepository;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(char v : vet) {
			v = randomChar();
		}
		return new String(vet);
		
	}
	
	private char randomChar() {
		int opt = rand.nextInt(3); //0, 1 ou 2
		
		if(opt == 0) { //com base na tabela unicode 
			return (char) (rand.nextInt(10) + 48);  //gera digito
		}
		else if(opt == 1) { // letra maiuscula
			return (char) (rand.nextInt(26) + 65);			
		}
		else { //letra minuscula
			return (char) (rand.nextInt(26) + 97);			
		}
	}

}
