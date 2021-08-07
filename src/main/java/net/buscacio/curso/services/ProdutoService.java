package net.buscacio.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import net.buscacio.curso.domain.Categoria;
import net.buscacio.curso.domain.Produto;
import net.buscacio.curso.repositories.CategoriaRepository;
import net.buscacio.curso.repositories.ProdutoRepository;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Produto find(Integer id) {
		
		Optional<Produto> p = produtoRepo.findById(id);
		
		return p.orElseThrow(() -> new ObjectNotFoundException(
				"Produto não encontrado! Id: " + id + ", tipo: " + Produto.class.getName()));
		
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, 
			Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return produtoRepo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}

}
