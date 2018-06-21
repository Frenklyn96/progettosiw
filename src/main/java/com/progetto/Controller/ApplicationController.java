package com.progetto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import com.progetto.Enity.Allievo;
import com.progetto.Enity.Attivita;
import com.progetto.Enity.Centro;
import com.progetto.Enity.Dipendente;
import com.progetto.Services.AllievoServices;
import com.progetto.Services.AttivitaServices;
import com.progetto.Services.CentroServices;
import com.progetto.Services.DipendenteService;
import com.progetto.Services.UtenteServices;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;


@Controller
public class ApplicationController{
	@Autowired
	private AllievoServices allievoServices;
	@Autowired
	private AttivitaServices attivitaServices;
	@Autowired
	private CentroServices centroServices;
	@Autowired
	private UtenteServices utenteServices;
	@Autowired
	private DipendenteService dipendenteService;


	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/admin")
	public String admin (Authentication aut,Model model) {
		aut.getName();
		model.addAttribute("dipendente",(Dipendente) dipendenteService.getDipendenteUsername(aut.getName()).get());
		return ("/admin.html");
	} 


	@RequestMapping("/login")
	public String restituisciaccesso () {
		return ("login.html");
	}


	//ricerca attivita che l'alievo può fare nel centro, l'id è quello della pagina dell'allievo che lo identifica
	@GetMapping(value = "/listaAttivita")
	public String getAttivitaCentro(Model model,Authentication aut) {
		model.addAttribute("attivita",allievoServices.getCentro (aut.getName()).getAttivita());
		return ("corsiinformatica");
	}

	@RequestMapping("/addAllievo")
	public String vai(Model model)
	{
		String err = "";
		model.addAttribute("errore",err);
		return "addAllievo.html";
	}

	@RequestMapping("/addAllievoErr")
	public String vai2(Model model)
	{
		String err = "errore";
		model.addAttribute("errore",err);
		return "allieviRegistrati";
	}

	


	//restituisce tutti gli allievi
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/allAllievo")
	public String AllAllievi(Model model) {
		List<Allievo> allievi = allievoServices.getAllievi();
		model.addAttribute("allievi", allievi);
		return "allAllievo.html";
	}

	
	@RequestMapping("/inizializza")
	public String inizializza(Model model)
	{
		Centro centro = new Centro("SF united","sfCentro@mopp.it", "roma", 33578953, 100);
		centroServices.save(centro);
		dipendenteService.save(new Dipendente("pass", "lorenzo", "pratico", "amministratore", centro));
		attivitaServices.AddElem("matematica", "12/12/18", java.sql.Time.valueOf("12:00:00"), centro);
		attivitaServices.AddElem("italiano", "12/12/18", java.sql.Time.valueOf("13:00:00"), centro);
		allievoServices.AddElem("luca", "monaco", "roma", "12/12/96", "luke@gmail.com",
				centro, "pass", "3347995318", "luke");
		
		List<Allievo> alliev = (List<Allievo>) allievoServices.RestituisciTutti();
		List<Centro> centr = (List<Centro>) centroServices.RestituisciTutti();
		List<Attivita> attivit = (List<Attivita>) attivitaServices.RestituisciTutti();
		model.addAttribute("allievi",alliev);
		model.addAttribute("centri",centr);
		model.addAttribute("attivita",attivit);
		return "index";
	}	
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/ricerca")
	public String vaiaricaerca () {
		return ("ricerca.html");
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/aggiungi")
	public String vaiadAggiungi ()
	{
		return ("aggiungi.html");
	}	
	
	@RequestMapping("/user")
	public String user (Authentication aut,Model model) {
		//info allievo
		aut.getName();
		Allievo allievoCorrente = (Allievo)allievoServices.getAllievoUsername(aut.getName()).get();
		model.addAttribute("allievo",allievoCorrente);
		
		return ("/user.html");
	} 
	
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping ("/IscirvitiAttvita")
	public String iscrizioneattvitauser (Model model, Authentication aut) {
		
		List<Attivita> attivitaIscritte = allievoServices.getAttivita(aut.getName()).getAttivita();

		//attivita a cui iscriversi
				List<SimpleEntry<String, String>> info = new ArrayList<SimpleEntry<String, String>>();
				for(Attivita attivita : allievoServices.getCentro(aut.getName()).getAttivita()) {
					Long id = attivita.getId();
					boolean b = true;
					for(Attivita attivitaIscritta : attivitaIscritte) {
						if(attivitaIscritta.getId()==id) {
							b=false;
						}
					}
					if(b) {
					String value = attivita.getNome()
							+ " alle " + attivita.getOra().toString() + " il " + attivita.getData();
					info.add(new SimpleEntry<String, String>(id.toString(),value));
					}
				}
				model.addAttribute("iscriviti",info);

				
				return ("IscrizioneAttivita.html");
	}
	
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping ("/ElencoAttivita")
	public String attvitaueser (Authentication aut,Model model) {
		System.out.println(aut.getName());
		//attivita a cui si e iscritti
		List<Attivita> attivitaIscritte = allievoServices.getAttivita(aut.getName()).getAttivita();
		model.addAttribute("attivita",attivitaIscritte);
		//centro dell'allievo
				model.addAttribute("centro",(Allievo)allievoServices.getAllievoUsername(aut.getName()).get());	
		return ("ElencoAttivita.html");
	}
	
	@RequestMapping("/offertaformativa")
	public String offerta()
	{
		return "offertaformativa.html";
	}

	
	//tutte le attività di tutti i centri
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping ("/tutteleattivitaadmin")
	public String getTutteLeAttivita (Model model, Authentication aut) {
		model.addAttribute("attivita", attivitaServices.RestituisciTutti());
		return "tutteleattivitaadmin";
	}
	
	
	//tutte le attività di tutti i centri
		@RequestMapping ("/tutteleattivita")
		public String getTutteLeAttivita (Model model) {
			model.addAttribute("attivita", attivitaServices.RestituisciTutti());
			return "corsi";
		}
		
		@RequestMapping("/chisiamo")
		public String chisiamo()
		{
			return "chisiamo.html";
		}
		
		@RequestMapping("/doveandare")
		public String doveandare(Authentication aut)
		{
			boolean trovato = false;
			List<Allievo> allievi = allievoServices.getAllievi();
			for(Allievo x : allievi)
			{
				if(x.getUsername().equals(aut.getName()))
					return "redirect:/user";
			}
			return "redirect:/admin";
		}
		
		@RequestMapping("/ricercatuttiicentri")
		public String allieviPerCentro(Model model)
		{
			model.addAttribute("centri",centroServices.findAll());
			return "ricercatuttiicentri.html";
		}
		
		
		
		@RequestMapping("/allievipercentro")
		public String stampa(
				@RequestParam ("centro") String nome,
				@RequestParam ("citta") String citta,
				Model model
				)
		{
			if(centroServices.getCentro(nome, citta)==null)
				{
				model.addAttribute("errorecittacentro", "il centro non è presente in quella citta");
				return "redirect:/ricercatuttiicentri";
				}
			model.addAttribute("allievi",allievoServices.restituisciAllievicentro(centroServices.getCentro(nome, citta)));
			model.addAttribute("centro",centroServices.getCentro(nome, citta));
			return "allievipercentro";
		}


}