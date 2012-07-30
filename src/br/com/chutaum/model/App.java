package br.com.chutaum.model;

import com.google.appengine.api.datastore.Entity;

public class App {
	public static final App Votacao = new App(1, "votação", "black", "/img/app/icon/1.png");
	public static final App Projeto = new App(2, "projeto", "green", "/img/app/icon/2.png");

	private int ID;
	private String Name;
	private String Color;
	private String Icon;
	
	public App(int id, String name, String color, String icon) {
		this.ID = id;
		this.Icon = icon;
		this.Name= name;
		this.Color = color;
	}
	
	public App(int id) {
		if (id==1) {
			this.ID = Votacao.getID();
			this.Color = Votacao.getColor();
			this.Name = Votacao.getName();
			this.Icon = Votacao.getIcon();
		}
		else if (id==2) {
			this.ID = Projeto.getID();
			this.Color = Projeto.getColor();
			this.Name = Projeto.getName();
			this.Icon = Projeto.getIcon();	
		}
		
		else {
			//procurar na base e carregar em cache, fazer so no futuro daqui uns 5 anos. 
		}
		
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

}

