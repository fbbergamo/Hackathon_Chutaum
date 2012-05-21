package br.com.chutaum.model;

import java.util.Date;
import java.util.List;

//colocar atributos dos usuarios email, nomes, list de politicos q ela segue 
public class User {
	private String nome;
	private String email;
	private List<Politician> folling;
	private Date registrationDate;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Politician> getFolling() {
		return folling;
	}
	public void setFolling(List<Politician> folling) {
		this.folling = folling;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	
	
}