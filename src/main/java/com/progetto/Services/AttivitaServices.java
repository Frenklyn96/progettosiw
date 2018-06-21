package com.progetto.Services;

import java.sql.Time;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Attivita;
import com.progetto.Enity.Centro;
import com.progetto.Repository.AttivitaRepository;
import com.progetto.Repository.CentroRepository;

@Service
public class AttivitaServices {
	@Autowired
	private  AttivitaRepository attivitaRepository;
	@Autowired
	private  CentroRepository centroRepository;
	@Autowired
	private CentroServices centroServices;


	public boolean AddElem (String nome, String data, Time ora, Centro centro) {
		try {
			attivitaRepository.save(new Attivita(nome, data, ora, centro));
		}catch (Exception e) {
			System.out.println( "Error in AttivitaServices: "+ e );
			return false;
		}
		return true;
	}

	public Attivita getAttivita(String string) {
		return attivitaRepository.findByNome(string);
	}


	public Attivita Restituisci (String name) {
		return attivitaRepository.findByNome(name);
	}
	
	public  Iterable<Attivita> RestituisciTutti (){
		return attivitaRepository.findAll();
	}

	public void setAllievo(Allievo a, Attivita attivita) {
		//TODO
	}


	public List<Attivita> getAttvitaCentro(Centro centro) {
		return attivitaRepository.findByCentro_id(centro.getId());
	}

	public boolean esisteAttivita(String nome, String nomeCentro, String citta, String data) {
		return attivitaRepository.findByNomeAndCentroAndData(nome, centroRepository.findByNomeAndCitta(nomeCentro, citta), data)!=null;
	}

	public Attivita findById(Long id) {
		return attivitaRepository.findById(id).get();
	}

	public void save(Attivita attivita) {
		attivitaRepository.save(attivita);
		
	}


}
