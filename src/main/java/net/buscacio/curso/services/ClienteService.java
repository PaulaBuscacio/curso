package net.buscacio.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.buscacio.curso.domain.Cidade;
import net.buscacio.curso.domain.Cliente;
import net.buscacio.curso.domain.Endereco;
import net.buscacio.curso.domain.enums.Perfil;
import net.buscacio.curso.domain.enums.TipoCliente;
import net.buscacio.curso.dto.ClienteDTO;
import net.buscacio.curso.dto.ClienteNewDTO;
import net.buscacio.curso.repositories.ClienteRepository;
import net.buscacio.curso.repositories.EnderecoRepository;
import net.buscacio.curso.security.UserSS;
import net.buscacio.curso.services.exceptions.AuthorizationException;
import net.buscacio.curso.services.exceptions.DataIntegrityException;
import net.buscacio.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if (user==null || !(user.hasRole(Perfil.ADMIN) || id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}
		
		
		Optional<Cliente> cli = clienteRepo.findById(id);
		
		return cli.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName()));
		
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	

}
