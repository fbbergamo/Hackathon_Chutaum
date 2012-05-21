package br.com.chutaum.model;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class Action implements java.io.Serializable  {
	
	public Action(Entity en) {
		this.setKey(en.getKey().toString());
	 	this.setId(en.getKey().getId());
		this.setContent((Text) en.getProperty("Content"));
		this.setKind(en.getProperty("Kind").toString());
		this.setDate((Date) en.getProperty("Date"));
		this.setDateMs((Long) en.getProperty("DateMs"));
		this.setIdPolition(Integer.parseInt((en.getProperty("IdPolitician").toString())));
	}
	
	public Action() {}
	
	private String Key;
	private static final long serialVersionUID = 1L;
	private long id;
	private Date date;
	private String content;
	private int idPolition;
	private String kind;
	private long dateMs;
	
	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(Text content) {
		this.content = content.getValue();
	}
	
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getIdPolition() {
		return idPolition;
	}
	
	public void setIdPolition(int idPolition) {
		this.idPolition = idPolition;
	}
	
	public String getKind() {
		return kind;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	


	public long getDateMs() {
		return dateMs;
	}

	public void setDateMs(long dateMs) {
		this.dateMs = dateMs;
	}
	
	
}
