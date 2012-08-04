package br.com.chutaum.api;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Entity;


import br.com.chutaum.model.Action;
import br.com.chutaum.model.App;
import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;
import br.com.chutaum.politician.PoliticianController;
 
@Controller
@RequestMapping("/")
public class ApiController {
	
	@RequestMapping( value =  "action/{name:.+}", method = RequestMethod.GET)
	public @ResponseBody Action getAction(@PathVariable String name) {
			return PoliticianController.findAction(name);
	}
	
	@RequestMapping( value =  "action/{name:.+}/destroy", method = RequestMethod.DELETE)
	public @ResponseBody Boolean destroyAction(@PathVariable String name, HttpServletRequest  req) {	
			return PoliticianController.findAction(name).destroy();	
	}
	
	@RequestMapping( value =  "action/{name:.+}/update", method = RequestMethod.POST)
	public @ResponseBody Boolean updateAction(@PathVariable String name, HttpServletRequest  req) throws ParseException {
		Action action = getAction(req);
		return action.save();	
	}
	
	@RequestMapping( value =  "action/new", method = RequestMethod.POST)
	public @ResponseBody Boolean newAction(@PathVariable String name, HttpServletRequest  req) throws ParseException {
		Action action = getAction(req);
		return action.save();
	}
 
	@RequestMapping( value =  "politician/{id}", method = RequestMethod.GET)
	public @ResponseBody Politician getPolitician(@PathVariable int id) throws Exception {
			return PoliticianController.findPolitician(id);
	}
	
	@RequestMapping( value =  "politician/{id}/followers", method = RequestMethod.GET)
	public @ResponseBody List<User> PoliticianFollowers(@PathVariable int id) {
		return PoliticianController.findPolitician(id).getFollowers();
	}
	
	
	@RequestMapping( value =  "politicians/list", method = RequestMethod.GET)
	public @ResponseBody Iterable<Entity> listPoliticians() {
		return PoliticianController.allPoliticians();
	}
	
	private Action getAction(HttpServletRequest req) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		String id = req.getParameter("id");
		String app = req.getParameter("app");
		String token = req.getParameter("token");
		String politician = req.getParameter("politician");
		String text = req.getParameter("text");
		Date date = format.parse(req.getParameter("date"));
		return new Action(id,text,date,getApp(app,token),Integer.parseInt(politician));
	}
	
	private App getApp(String app, String token) {
		App currentApp = new App(Integer.parseInt(app));
		if (token == currentApp.getToken()){
			return currentApp;
		}
		else return null;
	}
	

	
	

	
 
}