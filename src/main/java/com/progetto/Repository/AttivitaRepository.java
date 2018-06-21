package com.progetto.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progetto.Enity.Attivita;
import com.progetto.Enity.Centro;

public interface AttivitaRepository extends CrudRepository<Attivita, Long>{
	
	Attivita findByNome (String nome);
	Attivita findByNomeAndCentroAndData(String nome, Centro centro, String data);

	List<Attivita> findByCentro_id(Long id);

}
