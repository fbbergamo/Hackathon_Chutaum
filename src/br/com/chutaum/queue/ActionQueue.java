package br.com.chutaum.queue;


import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVReader;
import br.com.chutaum.json.JSONArray;
import br.com.chutaum.json.JSONException;
import br.com.chutaum.json.JSONObject;
import br.com.chutaum.model.Action;
import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;
import br.com.chutaum.politician.PoliticianController;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class ActionQueue  extends HttpServlet {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		
		String blobKeyString = req.getParameter("action");
		
		
		BlobKey blobKey = new BlobKey(blobKeyString);
		
		StringReader string = new StringReader(new String(blobstoreService.fetchData(blobKey,0,1015807),"UTF8"));
        
        CSVReader reader=new CSVReader(string,';');
        //título
        reader.readNext();
	    
	    String[] line;
		while((line=reader.readNext())!=null){
			StringBuilder stb = new StringBuilder(400);
	        
			for(int i=0;i<line.length;i++){
	        	stb.append(line[i]);
	        	stb.append(';');   
	        }
			
			//cria a ação através dos dados do arquivos
       		Action action = createActionObject(stb);
       		
       		if (action.getIdPolition() != 0) {
			 	Entity politician = Util.findEntity(KeyFactory.createKey("Politician", action.getIdPolition()));
			 	
			 	if (politician!= null) {
				 	Politician poli = new Politician(politician);
				 	
			 		Entity entity = createActionEntity(action, politician, poli);
			 		
			 		Key actionKey =	Util.persistEntity(entity);
			 			 		
			 		try {
			 			
			 			Iterable<Entity> array = PoliticianController.politicianFollow(poli);
				 		for (Entity en : array) {
				 			
				 			
				 			Entity useraction = createUserAction(action,
										poli, actionKey, en);
						 		
				 			Util.persistEntity(useraction); 
				 		
				 		}
				 		
			 		} catch (JSONException e) {
					//tratar caso o json venha zuado 	
					}
			 	}
		 	}
		}
    }

	private Entity createUserAction(Action action, Politician poli, Key actionKey, Entity en) throws JSONException {
			
			Entity useraction = new Entity("UserAction", en.getProperty("User").toString());
			Text text = new Text(action.getContent());
			useraction.setProperty("Content", text);
			useraction.setProperty("Date",action.getDate());
			useraction.setProperty("DateMs", action.getDateMs());
			useraction.setProperty("Kind",action.getKind());
			useraction.setProperty("UniqueActions",actionKey);
			useraction.setProperty("IdPolitician",poli.getId());
			useraction.setProperty("NamePolitician",poli.getName());
			useraction.setProperty("Party",poli.getParty());
			useraction.setProperty("Photo",poli.getPhoto());
		return useraction;
	}

	private Entity createActionEntity(Action action, Entity politician,
			Politician poli) {
		Entity entity = new Entity("Action", politician.getKey());
		Text text = new Text(action.getContent());
		entity.setProperty("Content", text);
		entity.setProperty("Date",action.getDate());
		entity.setProperty("DateMs", action.getDateMs());
		entity.setProperty("Kind",action.getKind());
		entity.setProperty("IdPolitician",poli.getId());
		entity.setProperty("NamePolitician",poli.getName());
		entity.setProperty("Party",poli.getParty());
		return entity;
	}

	private Action createActionObject(StringBuilder stb) {
		
		String [] input = new String(stb).split(";"); 
		Action action = new Action();
		action.setIdPolition(Integer.parseInt(input[0]));
		action.setContent(input[1]);
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
			Date date = format.parse(input[2]);
			action.setDate(date);
			action.setDateMs(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		action.setKind(input[3]);
		return action;
	}
	
}
