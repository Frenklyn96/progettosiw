package com.progetto.Services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Dipendente;
import com.progetto.Repository.UtenteRepository;

@Service
public class UtenteServices {
	@Autowired
	private UtenteRepository utenteRepository;
	
	public boolean AddElem (String nome, String cognome, String password) {
		//TODO
		return true;
	}
	
	
	public Dipendente getUtente (String nome) {
		return utenteRepository.findByNome(nome);
	}
	
	public Collection <Dipendente> RestituisciTutti(){
		return ((Collection<Dipendente>) utenteRepository.findAll());
	}
	

}
