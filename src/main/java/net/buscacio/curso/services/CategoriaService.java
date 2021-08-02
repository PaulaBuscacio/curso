package net.buscacio.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.buscacio.curso.domain.Categoria;
import net.buscacio.curso.repositories.CategoriaRepository;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		
	Optional<Categoria> cat = repo.findById(id);
	
	return cat.orElseThrow(() -> new ObjectNotFoundException(
			"Categoria não encontrada! Id: " + id + ", tipo: " + Categoria.class.getName()));
		
	}

}
