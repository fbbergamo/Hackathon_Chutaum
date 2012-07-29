package br.com.chutaum.queue;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVReader;

import br.com.chutaum.model.Action;
import br.com.chutaum.model.App;
import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;
import br.com.chutaum.politician.PoliticianController;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

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
        
       //header 
       reader.readNext();
      
       
	   String[] line;
	    
	   while((line=reader.readNext())!=null) {
			
       		Action action = createActionObject(line);
       		
       		if (action.getIdPolition() != 0) {
			 	Entity politician = Util.findEntityAndAddCache(KeyFactory.createKey("Politician", action.getIdPolition()));
			 	
			 	if (politician!= null) {
			 		
				 	Politician poli = new Politician(politician);
				 	Entity entity = action.createActionEntity(poli);
			 		Util.persistEntity(entity);
			 		
			 			Iterable<Entity> array = PoliticianController.politicianFollow(poli);
				 		for (Entity en : array) {
					 		Entity userAction =	action.createUserActionEntity(new User(en.getProperty("User").toString()));
					 		Util.persistEntity(userAction);	
				 		}
			 	}
		 	}
		}
	   Util.clearAllCache();
    }
	

	private Action createActionObject(String[] input) {
		Action action= new Action();
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
			
			Long idPolitician = Long.parseLong(input[0]);
			String idAction = input[1];
			String content = input[2];
			Date date = format.parse(input[3]);
			App app = new App(Integer.parseInt(input[4]));
			action = new Action(idAction, content, date, app,idPolitician);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return action;
	}
	
}
