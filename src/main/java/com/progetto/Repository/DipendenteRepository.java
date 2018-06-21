package com.progetto.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progetto.Enity.Dipendente;
import com.progetto.Enity.User;

public interface DipendenteRepository extends JpaRepository<Dipendente, Integer>{
	Optional<User> findByUsername(String username);

}
