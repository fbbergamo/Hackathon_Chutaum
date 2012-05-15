package br.com.chutaum.model;

import java.util.Date;

public class Action implements java.io.Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private Date date;
	private String content;
	private int idPolition;
	private String kind;
	
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
	
	public String writeJson() {
		
		StringBuilder sb = new StringBuilder();
		    sb.append("{\"data\": [");
		    
		    
		      sb.append("{");
		   
		        sb.append("\"IdPolition\" : \"" + this.idPolition + "\",");
		   
		        sb.append("\"Content\" : \"" + this.content + "\",");
		        sb.append("\"kind\" : \"" + this.content + "\",");
		        sb.append("\"Date\" : \"" + this.date);
		        
		  
		      sb.append("}");
		      sb.append("]}");	
			return sb.toString();
			
	}
	
	public void methooooodo() {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}
	
	
}
