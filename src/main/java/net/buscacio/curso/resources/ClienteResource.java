package net.buscacio.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.buscacio.curso.domain.Cliente;
import net.buscacio.curso.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
//	
//	@GetMapping
//	public List<Cliente> listar() {
//		
//
//		return lista;
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Integer id) {
		Cliente cliente = service.buscar(id);
		return ResponseEntity.ok().body(cliente);
	}

}
