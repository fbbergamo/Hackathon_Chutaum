package br.com.chutaum.politician;

import br.com.chutaum.model.Politician;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

//aqui aonde fica td a regra de negocio de politicos  aonde vamos salvar e etc

public class ControllerPolitician {
	
	public static Politician findPolitician(int id) {
		//busca na base e faz cache no servidor. 
		Entity en = Util.findEntityAndAddCache(KeyFactory.createKey("Politician", id));
		if (en!=null) {
			Politician politician = new Politician();
			politician.setName(en.getProperty("Name").toString());
			//politician.setParty(en.getProperty("Party").toString());
			//politician.setEmail(en.getProperty("Email").toString());
			//politician.setDescription(en.getProperty("Description").toString());
			//politician.setTelefone(en.getProperty("Telefone").toString());
			return politician;  
		}
		return null;
	}
	
	public String politicianActions(Politician politician, int sizepage, int offent) {
		Key ancestorKey = KeyFactory.createKey("Politician", politician.getId());
    	return Util.writeJSON(Util.listChildren("Actions", ancestorKey,sizepage,offent));
	}
	
	
	
	
}
