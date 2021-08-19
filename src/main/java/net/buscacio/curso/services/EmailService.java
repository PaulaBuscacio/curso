package net.buscacio.curso.services;

import org.springframework.mail.SimpleMailMessage;

import net.buscacio.curso.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
		
	
}
