package com.progetto.Controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Attivita;
import com.progetto.Services.AllievoServices;
import com.progetto.Services.AttivitaServices;
import com.progetto.Services.CentroServices;

@Controller
public class AllievoController{
	@Autowired
	private AllievoServices allievoServices;
	@Autowired
	private AttivitaServices attivitaServices;
	@Autowired
	private CentroServices centroServices;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/registraAllievo")
	public String FormAggiungiAllievo(Model model)
	{
		model.addAttribute("centri",centroServices.getCentri());
		return "registraAllievo";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/AggiungiAllievo")
	public String addAllievo(
			@RequestParam("nome") String nome,
			@RequestParam("cognome") String cognome,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("mail") String mail,
			@RequestParam("luogo") String luogo,
			@RequestParam("data") String data,
			@RequestParam("telefono") String telefono,
			@RequestParam("centro") String centro,
			@RequestParam("citta") String citta,
			Model model
			)
	{
		boolean e = false;


		if (!centroServices.esisteCentro(centro, citta)){
			model.addAttribute("erroreCentro", "errore il centro non esiste");
			e=true;
		}
		if(mail.isEmpty()) {
			model.addAttribute("erroreMail", "inserire mail");
			e=true;
		}
		if(allievoServices.esisteMail(mail)) {
			model.addAttribute("erroreMail", "utente gia presente nel sistema");
			e=true;
		}
		if(nome.isEmpty()) {
			model.addAttribute("erroreNome", "non lasciare vuoto questo campo");
			e=true;
		}
		if(cognome.isEmpty()) {
			model.addAttribute("erroreCognome", "non lasciare vuoto questo campo");
			e=true;
		}
		if(allievoServices.esisteUsername(username)) {
			model.addAttribute("erroreUsername", "username gia' esistente");
		}
		if(username.isEmpty()) {
			model.addAttribute("erroreUsername", "non lasciare vuoto questo campo");
			e=true;
		}
		if(password.isEmpty()) {
			model.addAttribute("errorepassword", "non lasciare vuoto questo campo");
			e=true;
		}
		if(luogo.isEmpty()) {
			model.addAttribute("erroreLuogo", "non lasciare vuoto questo campo");
			e=true;
		}
		if(data.isEmpty()) {
			model.addAttribute("erroreData", "non lasciare vuoto questo campo");
			e=true;
		}
		if(telefono.isEmpty()) {
			model.addAttribute("erroreTelefono", "non lasciare vuoto questo campo");
			e=true;
		}
		if(centro.isEmpty()) {
			model.addAttribute("erroreCentro", "non lasciare vuoto questo campo");
			e=true;
		}
		if(citta.isEmpty()) {
			model.addAttribute("erroreCitta", "non lasciare vuoto questo campo");
			e=true;
		}

		if(e) {
			model.addAttribute("errore", "impossibile aggiungere l'allievo");
			return "registraAllievo";
		}

		allievoServices.AddElem(nome, cognome, luogo, data, mail, centroServices.getCentro(centro,citta), password, telefono, username);
		centroServices.setAllievo( (Allievo) allievoServices.getAllievoNome(nome).get(0), centroServices.getCentro(centro,citta));
		return "redirect:/allAllievo";
	}

	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping("/AllievoAttivita")
	public String FormAggiungiAttivita(Model model, Authentication authentication) {
		List<SimpleEntry<String, String>> info = new ArrayList<SimpleEntry<String, String>>();
		for(Attivita attivita : allievoServices.getCentro(authentication.getName()).getAttivita()) {
			info.add(new SimpleEntry<String, String>(attivita.getId().toString(),attivita.getNome()
					+ " alle " + attivita.getOra().toString() + " il " + attivita.getData()));
		}
		model.addAttribute("attivita",info);
		return "allievoAttivita";
	}

	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping("/CollegaAllievoAttivita/{id}")
	public String collegaAllievoAttivita(@PathVariable("id") Long id, Model model, Authentication authentication) {
		Allievo allievoCorrente = (Allievo) allievoServices.getAllievoUsername(authentication.getName()).get();
		allievoCorrente.addAttivita(attivitaServices.findById(id));
		Attivita attivita = attivitaServices.findById(id);
		attivita.addAllievo(allievoCorrente);
		attivitaServices.save(attivita);
		allievoServices.save(allievoCorrente);
		return "redirect:/ElencoAttivita";
	}

}