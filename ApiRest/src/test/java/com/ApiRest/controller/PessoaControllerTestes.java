package com.ApiRest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.ApiRest.entidades.Pessoa;
import com.ApiRest.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTestes {
	
	private Long idExistente;
	private Long idNaoExistente;
	
	private Pessoa pExistente;
	private Pessoa pNovo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PessoaService service;
	
	@Autowired
		private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		 idExistente = 1l;
		 idNaoExistente = 100l;
		 pNovo = new Pessoa();
		 pExistente = new Pessoa("Mikaelen Oliveira");
		 pExistente.setId(1l);
		 Mockito.when(service.consultar(idExistente)).thenReturn(pExistente);
		 Mockito.doThrow(EntityNotFoundException.class).when(service).consultar(idNaoExistente);
		 
		 Mockito.when(service.salvar(any())).thenReturn(pExistente);
		 
		 Mockito.when(service.alterar(eq(idExistente), any())).thenReturn(pExistente);
		 Mockito.when(service.alterar(eq(idNaoExistente), any())).thenThrow(EntityNotFoundException.class);
		 
	}
	
	@Test
	public void deveRetornarPessoaQuandoConsultaIdExistente() throws Exception {
		ResultActions result = mockMvc.perform(get("/pessoa/{idpessoa}", idExistente)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}
	
	@Test
	public void deveRetornar404QuandoConsultaIdNaoExistente() throws Exception {
		ResultActions result = mockMvc.perform(get("/pessoa/{idpessoa}", idNaoExistente)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deveRetornar204AoSalvarComSucesso() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(pNovo);
		ResultActions result = mockMvc.perform(post("/pessoa")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isCreated());
	}
	
	@Test
	public void retornaOkQuandoAltera() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(pExistente);
		ResultActions result = mockMvc.perform(put("/pessoa/{idpessoa}", idExistente).content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}
	
	@Test
	public void retorna404QuandoAlteraInexistente() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(pNovo);
		ResultActions result = mockMvc.perform(put("/pessoa/{idpessoa}", idNaoExistente).content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void retornaListaConsultaTodos() throws Exception {
		ResultActions result = mockMvc.perform(get("/pessoa"));
		result.andExpect(status().isOk());
		Mockito.when(service.consultarTodos()).thenReturn(new ArrayList<>());
	}


}
