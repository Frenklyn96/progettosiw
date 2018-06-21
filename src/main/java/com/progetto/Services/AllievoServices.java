package com.progetto.Services;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Attivita;
import com.progetto.Enity.Centro;
import com.progetto.Enity.User;
import com.progetto.Repository.AllievoRepository;

@Service
public class AllievoServices{
	@Autowired
	private  AllievoRepository allievoRepository;
	
	
	public boolean AddElem (String nome, String cognome, String luogo_nascita, String data, String mail,Centro centro, String password, String telefono,String username) {
		try {allievoRepository.save(new Allievo(password,nome,cognome,username,mail,telefono,luogo_nascita,data,centro));
	}catch (Exception e) {
		System.out.println("Error in AttivitaServices: "+e);
		return false;
	}
	return true;
	}
	
	public List<User> getAllievoNome(String string) {
		return Collections.singletonList(allievoRepository.findByNome(string).get());
	}
	
	public Optional<User> getAllievoUsername(String string) {
		return  allievoRepository.findByUsername(string);
	}
	
	public boolean esisteMail(String string){
		return allievoRepository.findByMail(string).isPresent();
	}
	
	public boolean esisteUsername(String username) {
		return allievoRepository.findByUsername(username)!=null;
	}
	
	public Collection <Allievo> RestituisciTutti (){
		return ((Collection<Allievo>)allievoRepository.findAll());
	}
	
	
	public Allievo Restituisci (String name) {
		//TODO
		return null;
	}
	
	public void setAttivita(Attivita a, Allievo allievo) {
		//TODO
		
	}

	public List<Allievo> getAllievi() {
		return (List<Allievo>) allievoRepository.findAll();
	}

	public Centro getCentro(String username) {
		Allievo a = (Allievo) allievoRepository.findByUsername(username).get();
		return a.getCentro();
	}

	public Allievo getAttivita(String name) {
		return (Allievo) allievoRepository.findByUsername(name).get();
	}

	public void save(Allievo allievo) {
		allievoRepository.save(allievo);
	}
	
	public List<Allievo> restituisciAllievicentro (Centro centro){
		
		return allievoRepository.findBycentro_id (centro.getId());
	}

	
	
}