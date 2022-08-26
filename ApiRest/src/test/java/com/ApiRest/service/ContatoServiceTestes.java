package com.ApiRest.service;

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

import com.ApiRest.entidades.Contato;
import com.ApiRest.repository.ContatoRepository;
import com.ApiRest.services.ContatoService;
import com.ApiRest.services.dto.ContatoDTO;

@ExtendWith(SpringExtension.class)
public class ContatoServiceTestes {
	
	private Long idExistente;
	private Long idNaoExistente;
	private Contato contatoValido;
	private Contato contatoInvalido;
		
	@BeforeEach
	void setup() {
		idExistente = 1l;
		idNaoExistente = 1000l;
		contatoValido = new Contato("Mikaelen", "mika@gmail.com", "(65) 992820129");
		contatoInvalido = new Contato("Mikaelen", "mikagmail.com", "(65)992820129");
			
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(idNaoExistente);
		
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(new Contato()));
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(idNaoExistente);
		
		Mockito.when(repository.save(contatoValido)).thenReturn(contatoValido);
		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(contatoInvalido);



	}
	
	@InjectMocks
	ContatoService service;
	
	@Mock
	ContatoRepository repository;
	
	//@Test
	public void retornaNadaAoExcluirComIdExistente() {
		Assertions.assertDoesNotThrow(() -> {
			service.excluirContato(idExistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
	}
	
	//@Test
	public void lancaEntidadeNaoEncontradaAoExcluirIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.excluirContato(idNaoExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(idNaoExistente);
	}
	
	//@Test
	public void retornaContatoComIdExistente() {
		ContatoDTO ct = service.consultarContatoPorId(idExistente);
		Assertions.assertNotNull(ct);
		Mockito.verify(repository).findById(idExistente);
	}
		
		
	//@Test
	public void lancaEntidadeNaoEncontradaAoConsultarIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.consultarContatoPorId(idNaoExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).findById(idNaoExistente);
	}
	
	
	//@Test
	public void retornaContatoDTOQuandoSalvarComSucesso() {
		ContatoDTO contatoDTO = service.salvar(contatoValido);
		Assertions.assertNotNull(contatoDTO);
		Mockito.verify(repository).save(contatoValido);
		

	/*	Contato testecontato = new Contato();
		
		Mockito.when(repository.save(testecontato)).thenReturn(testecontato);
		ContatoDTO resultado = service.salvar(testecontato);
		Assertions.assertNotNull(resultado);
		
		Assertions.assertEquals(payload.getNome(), resultado.getNome());
		Assertions.assertEquals(payload.getId(), resultado.getId());*/
	}
	
	
	//@Test
	public void retornaErroAoCadastrarContatoDTO() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvar(contatoInvalido));
		Mockito.verify(repository, Mockito.times(1)).save(contatoInvalido);
	}
}
