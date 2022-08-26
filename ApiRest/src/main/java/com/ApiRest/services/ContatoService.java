package com.ApiRest.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ApiRest.entidades.Contato;
import com.ApiRest.repository.ContatoRepository;
import com.ApiRest.services.dto.ContatoDTO;
import com.ApiRest.services.excptions.ValidacaoException;


@Service
public class ContatoService {
	
	@Autowired
	ContatoRepository repo;
	
	public ContatoDTO salvar(Contato contato) {
		/*if(contato.getFone().length()!=14) {
			throw new ValidacaoException ("Telefone invalido");
		}
		
		if(!contato.getEmail().contains("@")) {
			throw new ValidacaoException ("E-mail invalido");
		}*/
		
		
		Contato ct = repo.save(contato);
		ContatoDTO contatoDTO = new ContatoDTO(ct);
		return contatoDTO;
	}
	
	public List<ContatoDTO> consultarContatos() {
		List<Contato> contatos = repo.findAll();
		List<ContatoDTO> contatosDTO = new ArrayList();
		for(Contato ct : contatos) {
			contatosDTO.add(new ContatoDTO(ct));
		}
		return contatosDTO;
	}
	
	public ContatoDTO consultarContatoPorId(Long idcontato) {
		java.util.Optional<Contato> opcontato = repo.findById(idcontato);
		Contato ct = opcontato.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
		return new ContatoDTO(ct);
	}
	
	public Contato consultarContato(Long idcontato) {
		java.util.Optional<Contato> opcontato = repo.findById(idcontato);
		Contato ct = opcontato.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
		return (ct);
	}
	
	
	
	public void excluirContato(Long idcontato) {
		//Contato ct = consultarContato(idcontato);
		repo.deleteById(idcontato);
	}
	
	public ContatoDTO alterarContato(Long idcontato, Contato contato) {
		Contato contatoSalvo = consultarContato(idcontato);
		contatoSalvo.setNome(contato.getNome());
		contatoSalvo.setEmail(contato.getEmail());
		contatoSalvo.setFone(contato.getFone());
		return new ContatoDTO (repo.save(contatoSalvo));
	}
	
	public List<ContatoDTO> consultarContatoPorEmail(String email){
		return ContatoDTO.converteParaDTO(repo.findByEmail(email));
	}
	
}
