package br.com.chutaum.model;

import com.google.appengine.api.datastore.Entity;

public class Pagination {
	
	private Iterable<Entity> entitys;
	private  int size;
	
	public Iterable<Entity> getEntitys() {
		return entitys;
	}
	public void setEntitys(Iterable<Entity> entitys) {
		this.entitys = entitys;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}


}
