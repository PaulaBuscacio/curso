package net.buscacio.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.buscacio.curso.domain.Pedido;
import net.buscacio.curso.repositories.PedidoRepository;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	public Pedido find(Integer id) {
		
	Optional<Pedido> p = pedidoRepo.findById(id);
	
	return p.orElseThrow(() -> new ObjectNotFoundException(
			"Pedido não encontrado! Id: " + id + ", tipo: " + Pedido.class.getName()));
		
	}

}
