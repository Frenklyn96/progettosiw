package com.progetto.Controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.progetto.Enity.Attivita;
import com.progetto.Enity.Centro;
import com.progetto.Repository.AttivitaRepository;
import com.progetto.Services.AllievoServices;
import com.progetto.Services.AttivitaServices;
import com.progetto.Services.CentroServices;

@Controller
public class AttivitaController {

	@Autowired
	private AllievoServices allievoServices;
	@Autowired
	private AttivitaServices attivitaServices;
	@Autowired
	private CentroServices centroServices;
	@Autowired 
	private AttivitaRepository attivitaRepository;


	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/registraAttivita")
	public String FormAggiungtiCentro(Model model) {
		model.addAttribute("centri",centroServices.getCentri());

		return "registraAttivita";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/AggiungiAttivita")
	public String RegistraCentro(
			@RequestParam("nome") String nome,
			@RequestParam("data") String data,
			@RequestParam("ora") String ora,
			@RequestParam("centro") String nomeCentro,
			@RequestParam("citta") String citta,
			Model model) {


		boolean e = false;
		if(!centroServices.esisteCentro(nomeCentro, citta)) {
			model.addAttribute("erroreNome", "centro non presente in questa citta'");
			e=true;
		}
		else {
			if(attivitaServices.esisteAttivita(nome,nomeCentro,citta,data)) {
				model.addAttribute("erroreNome", "centro gia' presente in questa citta'");
				e=true;
			}	else {
				if(nome.isEmpty()) { 
					model.addAttribute("erroreNome", "necessario specificare il nome");
					e=true;
				}
				if(citta.isEmpty()) { 
					model.addAttribute("erroreCitta", "necessario specificare la citta'");
					e=true;
				}
				if(data.isEmpty()) { 
					model.addAttribute("erroreData", "necessario specificare la data");
					e=true;
				}
				if(nomeCentro.isEmpty()) { 
					model.addAttribute("erroreCentro", "necessario specificare il nome del centro");
					e=true;
				}
			}

			if((ora.isEmpty())||(ora.length()<4)) { 
				model.addAttribute("erroreOra","necessario specificare l'orario");
				e=true;
			}
		}

		if(e) {
			model.addAttribute("errore", "Impossibile aggiungere centro");
			return "registraAttivita";
		}
		attivitaServices.AddElem(nome, data, java.sql.Time.valueOf(ora.substring(0, 2)+":"+ora.substring(2, 4)+":00"), centroServices.getCentro(nomeCentro, citta));
		return "redirect:/tutteleattivitaadmin";

	}

}

