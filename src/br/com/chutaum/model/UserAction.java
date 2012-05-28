package br.com.chutaum.model;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class UserAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getUniqueActions() {
		return uniqueActions;
	}

	public void setUniqueActions(String uniqueActions) {
		this.uniqueActions = uniqueActions;
	}

	private String party;
	private String photo;
	private String uniqueActions;
	


	public UserAction(Entity en) {
		this.setPhoto(en.getProperty("Photo").toString());
		this.setParty(en.getProperty("Party").toString());
		this.setKey(en.getKey().toString());
	 	this.setId(en.getKey().getId());
		this.setContent((Text) en.getProperty("Content"));
		this.setKind(en.getProperty("Kind").toString());
		this.setDate((Date) en.getProperty("Date"));
		this.setDateMs((Long) en.getProperty("DateMs"));
		this.setIdPolition(Integer.parseInt((en.getProperty("IdPolitician").toString())));
		this.setNamePolitician(en.getProperty("NamePolitician").toString());
		this.setUniqueActions(en.getProperty("UniqueActions").toString());
	}
}
