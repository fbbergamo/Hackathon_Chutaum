package br.com.chutaum.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;


public class Politician {
	@JsonIgnore
	private long id;
	private String name;
	private String description;
	private String email;
	private String telefone;
	
	@JsonIgnore
	private String party;
	
	@JsonIgnore
	private String photo;
	
	@JsonIgnore
	private Key Key;
	
	public Key getKey() {
		return Key;
	}

	public void setKey(Key key) {
		Key = key;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Politician(Entity en) {
			this.setKey(en.getKey());
		 	this.setId(en.getKey().getId());
			this.setName(en.getProperty("Name").toString());
			this.setParty(en.getProperty("Party").toString());
			this.setPhoto(en.getProperty("Photo").toString());
			//this.setEmail(en.getProperty("Email").toString());
			//this.setDescription(en.getProperty("Description").toString());
			//this.setTelefone(en.getProperty("Telefone").toString());
		
	}
	
	public Politician() {
	
	}
	
	public Politician(Integer id) {
		this.setId(id);
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
