package com.henrique.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henrique.cursomc.domain.Cidade;
import com.henrique.cursomc.domain.Cliente;
import com.henrique.cursomc.domain.Endereco;
import com.henrique.cursomc.domain.enums.TipoCliente;
import com.henrique.cursomc.dto.ClienteDTO;
import com.henrique.cursomc.dto.ClienteNewDTO;
import com.henrique.cursomc.repositories.ClienteRepository;
import com.henrique.cursomc.repositories.EnderecoRepository;
import com.henrique.cursomc.services.exception.DataIntegrityException;
import com.henrique.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! "+"id:"+id+", Tipo: "+Cliente.class.getName()));
		
	}
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);

		obj = repo.save(obj);

		enderecoRepository.saveAll(obj.getEnderecos());

		return obj;
		
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj);
		
		return repo.save(newObj);	
	}

	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		}catch( DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que seja relacionado em outras entidades");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	
	/**
	 * converte a classe ClienteDTO para Cliente já instanciando e iniciando a classe 
	 */
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
		
	}
	//sobrecarga de método
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		// TODO Auto-generated method stub
		Cliente cli = new Cliente(null,objDTO.getNome(),objDTO.getEmail(),objDTO.getCpfOuCnpj(),TipoCliente.toEnum(objDTO.getTipoCliente()));
		Cidade cid = new Cidade(objDTO.getCidadeId(),null,null);
		Endereco end = new Endereco(null,objDTO.getLogradouro(),objDTO.getNumero(),objDTO.getComplemento(),objDTO.getBairro(),objDTO.getCep(),cli,cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
