package br.com.chutaum.model;


public class Politician {
	private int id;
	private String name;
	private String description;
	private String email;
	private String telefone;
	private String party;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
