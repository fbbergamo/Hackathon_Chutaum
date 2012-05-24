package br.com.chutaum.politician;

import java.util.List;

import br.com.chutaum.json.JSONArray;
import br.com.chutaum.json.JSONException;
import br.com.chutaum.json.JSONObject;
import br.com.chutaum.model.Politician;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

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
	
	public static Iterable<Entity> allPoliticians() {
		return Util.listEntities("Politician",null,"");
	}
	

	public static JSONArray politicianActions(Politician politician, int sizepage, int offent) throws JSONException {
		Key ancestorKey = KeyFactory.createKey("Politician", politician.getId());
    	return new JSONArray(Util.listChildren("Actions", ancestorKey,sizepage,offent));	
	}
	
	public static Iterable<Entity>  politicianFollow(Politician politician) throws JSONException {
		Key ancestorKey = KeyFactory.createKey("Politician", politician.getId());
    	 return Util.listChildren("PoliticanFollow", ancestorKey);
	}
	
	
	
	
	
}
