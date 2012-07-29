package br.com.chutaum.politician;

import java.util.ArrayList;
import java.util.List;

import br.com.chutaum.model.Action;
import br.com.chutaum.model.Entitys;
import br.com.chutaum.model.Politician;
import br.com.chutaum.model.UserAction;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;

//aqui aonde fica td a regra de negocio de politicos  aonde vamos salvar e etc

public class PoliticianController {
	
	public static Politician findPolitician(int id) {
	
		Entity en = Util.findEntity(KeyFactory.createKey("Politician", id));
		if (en!=null) {
			Politician politician = new Politician(en);
			return politician;  
		}
		return null;
	}
	
	public	 static Iterable<Entity> allPoliticians() {
		return Util.listEntities("Politician",null,"");
	}
	

	public static List<Action> politicianActions(Politician politician, int sizepage, int offset) {
		Key key = KeyFactory.createKey(Entitys.Politician, politician.getId() );
		List<Entity>  list =  Util.listChildrenAndOrderby(Entitys.PoliticianAction, key, sizepage, offset,  "DateMs", SortDirection.DESCENDING, true);
		List<Action> actions = new ArrayList<Action>();  
		for (Entity tmp : list) {
			actions.add(new Action(tmp));
		}
		return actions;
	}
	
	public static Iterable<Entity>  politicianFollow(Politician politician) {
		Key ancestorKey = KeyFactory.createKey("Politician", politician.getId());
    	 return Util.listChildren("PoliticanFollow", ancestorKey);
	}
	
	
	public static Action findAction(String id){
		com.google.appengine.api.datastore.Key parent = new KeyFactory.Builder(Entitys.Politician, Integer.parseInt(id.split("\\.")[1])).addChild(Entitys.PoliticianAction, id).getKey();	
		return new Action(Util.findEntityAndAddCache(parent));
	}
	
	
	
	
	
}
