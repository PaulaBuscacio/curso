package net.buscacio.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.buscacio.curso.domain.Pedido;
import net.buscacio.curso.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
//	
//	@GetMapping
//	public List<Categoria> listar() {
//		
//
//		return lista;
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Integer id) {
		Pedido pedido = service.buscar(id);
		return ResponseEntity.ok().body(pedido);
	}

}
