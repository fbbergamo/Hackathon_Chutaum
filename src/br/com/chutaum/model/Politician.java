package br.com.chutaum.model;

import com.google.appengine.api.datastore.Entity;


public class Politician {
	private long id;
	private String name;
	private String description;
	private String email;
	private String telefone;
	private String party;
	private String photo;
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Politician(Entity en) {
		 	this.setId(en.getKey().getId());
			this.setName(en.getProperty("Name").toString());
			//this.setParty(en.getProperty("Party").toString());
			//this.setEmail(en.getProperty("Email").toString());
			//this.setDescription(en.getProperty("Description").toString());
			//this.setTelefone(en.getProperty("Telefone").toString());
		
	}
	
	public Politician() {
	
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	
	
	

}
