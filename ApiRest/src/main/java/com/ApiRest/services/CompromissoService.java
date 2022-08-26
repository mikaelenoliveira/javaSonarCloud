package com.ApiRest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ApiRest.entidades.Compromisso;
import com.ApiRest.repository.CompromissoRepository;

public class CompromissoService {
	
	@Autowired
	CompromissoRepository repo;
	
	public Compromisso salvarCompromisso(Compromisso compromisso) {
		return repo.save(compromisso);
	}
	
	public  List<Compromisso> consultarCompromissos() {
		List<Compromisso> compromisso = repo.findAll();
		return compromisso;
	}
	
	public Compromisso consultarCompromissoPorId(Long idcompromisso) {
		Compromisso compromisso = repo.findById(idcompromisso).get();
		return compromisso;
	}

	public void excluirCompromisso(Long idcompromisso) {
		//Compromisso compromisso = consultarCompromissoPorId(idcompromisso);
		repo.deleteById(idcompromisso);
	}

	public Compromisso editarCompromisso(Long idcompromisso, Compromisso compromisso) {
		Compromisso comp = consultarCompromissoPorId(idcompromisso);
		comp.setLocal(compromisso.getLocal());
		comp.setData(compromisso.getData());
		comp.setContato(compromisso.getContato());
		return repo.save(comp);
	}


}
