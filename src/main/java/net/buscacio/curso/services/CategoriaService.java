package net.buscacio.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import net.buscacio.curso.domain.Categoria;
import net.buscacio.curso.domain.Cliente;
import net.buscacio.curso.dto.CategoriaDTO;
import net.buscacio.curso.repositories.CategoriaRepository;
import net.buscacio.curso.services.exceptions.DataIntegrityException;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> findAll() {
		
		return repo.findAll();	
	}
	
	public Categoria find(Integer id) {		
	Optional<Categoria> cat = repo.findById(id);
	
	return cat.orElseThrow(() -> new ObjectNotFoundException(
			"Categoria não encontrada! Id: " + id + ", tipo: " + Categoria.class.getName()));		
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
		
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);	
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	
	private void updateData(Categoria newObj, Categoria obj) {		
		newObj.setNome(obj.getNome());
	
	}

}
