package com.ApiRest.controllers.exception;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ApiRest.services.excptions.OperacaoNaoPermitidaException;


@ControllerAdvice
public class ManipuladorErros {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErroPadrao> entidadeNãoEncontrada (EntityNotFoundException e, HttpServletRequest req){
		ErroPadrao erro = new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.NOT_FOUND.value());
		erro.setError("Recurso não encontrado");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(OperacaoNaoPermitidaException.class)
	public ResponseEntity<ErroPadrao> minhaExcecao (OperacaoNaoPermitidaException e, HttpServletRequest req){
		ErroPadrao erro = new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		erro.setError("Sem conteudo");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(erro);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<ErroPadrao> minhaExcecao (EmptyResultDataAccessException e, HttpServletRequest req){
		ErroPadrao erro = new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.NOT_FOUND.value());
		erro.setError("Recurso não existe");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	/*@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<ErroPadrao> minhaExcecao (ValidacaoException e, 
			HttpServletRequest req){
		ErroPadrao erro = new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setError("Existe dados com formato invalido");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}*/
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroValidacao> minhaExcecaoValidacao (MethodArgumentNotValidException e, 
			HttpServletRequest req){
		ErroValidacao erro = new ErroValidacao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setMessage(e.toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

}
