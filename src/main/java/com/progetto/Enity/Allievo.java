package com.progetto.Enity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;


@Entity
public class Allievo extends User{
	
	@NotBlank
	private String mail;
	@NotBlank
	private String telefono;
	@NotBlank
	private String luogo;
	@NotBlank
	private String dataNascita;

	@ManyToOne
	private Centro centro;
	
	@ManyToMany(mappedBy="allievi")
	private List<Attivita> attivita;
	
	public Allievo() {
	}

	public Allievo( String password, String nome, String cognome, String username,String mail, String telefono, String luogo, String dataNascita, Centro centro)
	{
		super(password, nome, cognome, "USER", username);
		this.mail = mail;
		this.telefono = telefono;
		this.luogo = luogo;
		this.dataNascita = dataNascita;
		this.centro = centro;
		this.attivita = new ArrayList<Attivita>();
	}
	
	
	public Allievo(String password, String nome, String cognome, String ruolo, String username,String mail, String telefono, String luogo, String dataNascita, Centro centro,
			List<Attivita> attivita) {
		super(password, nome, cognome, "USER", username);
		this.mail = mail;
		this.telefono = telefono;
		this.luogo = luogo;
		this.dataNascita = dataNascita;
		this.centro = centro;
		this.attivita = attivita;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public List<Attivita> getAttivita() {
		return attivita;
	}

	public void setAttivita(List<Attivita> attivita) {
		this.attivita = attivita;
	}

	public void addAttivita(Attivita attivita) {
		this.attivita.add(attivita);
	}
	
	
	
	
	

}
