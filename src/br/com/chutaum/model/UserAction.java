package br.com.chutaum.model;



import java.util.Date;

import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;


public class UserAction extends Action {


	public UserAction(Entity entity) {
		com.google.appengine.api.datastore.Key parent = new KeyFactory.Builder(Entitys.Politician, Integer.parseInt(entity.getKey().getName().split("\\.")[1])).addChild(Entitys.PoliticianAction, entity.getKey().getName()).getKey();
		
		Entity en= Util.findEntityAndAddCache(parent);	
		this.setKey(en.getKey().toString());
	 	this.setId(en.getKey().getName());
		this.setContent((Text) en.getProperty("Content"));
		this.setApp(new App(Integer.parseInt((en.getProperty("App").toString()))));		
		this.setDate((Date) en.getProperty("Date"));
		this.setDateMs((Long) en.getProperty("DateMs"));
		this.setIdPolition(Integer.parseInt((en.getProperty("IdPolitician").toString())));
		this.setNamePolitician(en.getProperty("NamePolitician").toString());
	}
	
	

	
	
}
