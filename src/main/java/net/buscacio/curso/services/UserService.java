package net.buscacio.curso.services;

import org.springframework.security.core.context.SecurityContextHolder;

import net.buscacio.curso.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}

}
