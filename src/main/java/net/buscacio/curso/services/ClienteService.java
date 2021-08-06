package net.buscacio.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.buscacio.curso.domain.Cidade;
import net.buscacio.curso.domain.Cliente;
import net.buscacio.curso.domain.Endereco;
import net.buscacio.curso.domain.enums.TipoCliente;
import net.buscacio.curso.dto.ClienteDTO;
import net.buscacio.curso.dto.ClienteNewDTO;
import net.buscacio.curso.repositories.ClienteRepository;
import net.buscacio.curso.repositories.EnderecoRepository;
import net.buscacio.curso.services.exceptions.DataIntegrityException;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		
	Optional<Cliente> cli = clienteRepo.findById(id);
	
	return cli.orElseThrow(() -> new ObjectNotFoundException(
			"Cliente não encontrada! Id: " + id + ", tipo: " + Cliente.class.getName()));
		
	}
	

	public List<Cliente> findAll() {
		
		return clienteRepo.findAll();	
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepo.save(obj);
		enderecoRepo.saveAll(obj.getEnderecos());
		return obj;
		
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepo.save(newObj);
	}
	
	public void delete(Integer id) {
		try {
			clienteRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
		}
		
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null,objDTO.getNome(), objDTO.getEmail(), 
				objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), 
					objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(),cli, cidade);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	

}
