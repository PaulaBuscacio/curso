package net.buscacio.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.buscacio.curso.domain.Cliente;
import net.buscacio.curso.repositories.ClienteRepository;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	public Cliente buscar(Integer id) throws ObjectNotFoundException {
		
	Optional<Cliente> cli = clienteRepo.findById(id);
	
	return cli.orElseThrow(() -> new ObjectNotFoundException(
			"Cliente não encontrada! Id: " + id + ", tipo: " + Cliente.class.getName()));
		
	}

}
