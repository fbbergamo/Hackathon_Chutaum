package br.com.chutaum.api;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Entity;


import br.com.chutaum.model.Action;
import br.com.chutaum.model.Politician;
import br.com.chutaum.politician.PoliticianController;
 
@Controller
@RequestMapping("/")
public class ApiController {
 
	//DI via Spring
	String message;
 
	
	@RequestMapping( value =  "action/{name:.+}", method = RequestMethod.GET)
	public @ResponseBody Action getAction(@PathVariable String name) {
			return PoliticianController.findAction(name);
	}
	
	@RequestMapping( value =  "action/{name:.+}/destroy", method = RequestMethod.DELETE)
	public @ResponseBody Boolean destroyAction(@PathVariable String name) {
			return false;	
	}
	
	@RequestMapping( value =  "action/{name:.+}/update", method = RequestMethod.POST)
	public @ResponseBody Boolean updateAction(@PathVariable String name, Action action) {
			return false;	
	}
	
	@RequestMapping( value =  "action/new", method = RequestMethod.POST)
	public @ResponseBody Boolean newAction(@PathVariable String name, Action action) {
		return false;	
	}
 
	@RequestMapping( value =  "politician/{id}", method = RequestMethod.GET)
	public @ResponseBody Politician getPolitician(@PathVariable int id) throws Exception {
		
			return PoliticianController.findPolitician(id);
	
	
	}
	
	@RequestMapping( value =  "politicians/list", method = RequestMethod.GET)
	public @ResponseBody Iterable<Entity> listPoliticians() {
		return PoliticianController.allPoliticians();
	}
	
 
}