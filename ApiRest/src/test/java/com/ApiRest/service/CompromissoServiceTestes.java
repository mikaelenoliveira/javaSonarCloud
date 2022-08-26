package com.ApiRest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ApiRest.entidades.Compromisso;
import com.ApiRest.entidades.Contato;
import com.ApiRest.repository.CompromissoRepository;
import com.ApiRest.services.CompromissoService;

@ExtendWith(SpringExtension.class)
public class CompromissoServiceTestes {
	
	private Long idExistente;
	private Long idNaoExistente;
	private Compromisso compromissoValido;
	private Compromisso compromissoInvalido;
	private Contato contatoValido;
	private Contato contatoInvalido;
	
	List<Compromisso> listaCompromisso;
	
	@BeforeEach
	void setup() {
		idExistente = 1l;
		idNaoExistente = 1000l;
		contatoValido = new Contato("Mikaelen", "mika@gmail.com", "(65) 992820129");
		contatoInvalido = new Contato("Mikaelen", "mikagmail.com", "(65)992820129");
		compromissoValido = new Compromisso();
		
		compromissoInvalido = new Compromisso();
		compromissoInvalido.setContato(contatoInvalido);
		
		listaCompromisso = new ArrayList<>();
		
		Mockito.when(repository.save(compromissoValido)).thenReturn(compromissoValido);
		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(compromissoInvalido);
		
		Mockito.when(repository.findAll()).thenReturn(listaCompromisso);
		
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(new Compromisso()));
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(idNaoExistente);
		
		Mockito.doNothing().when(repository).deleteById(idExistente);


	}
	
	@InjectMocks
	CompromissoService service;
	
	@Mock
	CompromissoRepository repository;
	
	//@Test
	public void retornaCompromissoQuandoSalvarComSucesso() {
		Compromisso compromisso = service.salvarCompromisso(compromissoValido);
		Assertions.assertNotNull(compromisso);
		Mockito.verify(repository).save(compromissoValido);

	}
	
	//@Test
	public void retornaExcecaoQuandoNaoCadastraCompromisso() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvarCompromisso(compromissoInvalido));
		Mockito.verify(repository, Mockito.times(1)).save(compromissoInvalido);
	}
	
	//@Test
	public void retornaTodosOsContatosCadastrados() {
		List<Compromisso> compromissos = service.consultarCompromissos();
		Assertions.assertNotNull(compromissos);
		Mockito.verify(repository).findAll();
	}
	
	//@Test
	public void retornaCompromissoComIdExistente() {
		Compromisso compromisso = service.consultarCompromissoPorId(idExistente);
		Assertions.assertNotNull(compromisso);
		Mockito.verify(repository).findById(idExistente);
		
	}
	
	//@Test
	public void lancaEntidadeNaoEncontradaAoConsultarCompromissoComIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.consultarCompromissoPorId(idNaoExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).findById(idNaoExistente);
	}
	
	
	//@Test
	public void retornaNadaAoExcluirCompromissoComIdExistente() {
		Assertions.assertDoesNotThrow(()-> {
			service.excluirCompromisso(idExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
	}
	
	
	
	
	
	
	
}
