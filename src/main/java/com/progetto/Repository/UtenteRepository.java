package com.progetto.Repository;

import org.springframework.data.repository.CrudRepository;

import com.progetto.Enity.Dipendente;

public interface UtenteRepository extends CrudRepository<Dipendente, Long> {
	
	Dipendente findByNome (String nome);

	Dipendente save(Dipendente utente);

}
