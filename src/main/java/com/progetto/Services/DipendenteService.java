package com.progetto.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Dipendente;
import com.progetto.Enity.User;
import com.progetto.Repository.DipendenteRepository;

@Service
public class DipendenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;

	public Optional<User> getDipendenteUsername(String name) {
		return dipendenteRepository.findByUsername(name);
	}

	public void save(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
		
	}
	
	
}
