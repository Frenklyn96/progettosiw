package com.progetto.Enity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Centro {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String mail;
	@NotBlank
	private String citta;
	@NotNull
	private Integer telefono;
	@NotNull
	private Integer max_allievi;
	
	@OneToMany (cascade= {CascadeType.ALL},mappedBy="centro")
	private Set <Attivita> attivita;
	
	@OneToMany (cascade= {CascadeType.ALL},mappedBy="centro")
	private Set <Allievo> allievi;
	
	
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Integer getMax_allievi() {
		return max_allievi;
	}

	public void setMax_allievi(Integer max_allievi) {
		this.max_allievi = max_allievi;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Set<Allievo> getAllievi() {
		return allievi;
	}

	public void setAllievi(Set<Allievo> allievi) {
		this.allievi = allievi;
	}
	
	public Set<Attivita> getAttivita() {
		return attivita;
	}

	public void setAttivita(Set<Attivita> attivita) {
		this.attivita = (Set<Attivita>) attivita;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return mail;
	}

	public void setEmail(String email) {
		this.mail = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getMax_alievi() {
		return max_allievi;
	}

	public void setMax_alievi(int max_alievi) {
		this.max_allievi = max_alievi;
	}

	@Override
	public String toString() {
		return "Centro [id=" + id + ", nome=" + nome + ", mail=" + mail + ", telefono=" + telefono + ", max_allievi="
				+ max_allievi + "]";
	}

	public Centro() {}

	public Centro(String nome, String mail, String citta, Integer telefono, Integer max_allievi) {
		this.nome = nome;
		this.mail = mail;
		this.citta = citta;
		this.telefono = telefono;
		this.max_allievi = max_allievi;
		this.attivita= new HashSet<>();
		this.allievi= new HashSet<>();
	}
	
	
	

}
