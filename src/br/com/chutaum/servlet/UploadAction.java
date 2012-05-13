package br.com.chutaum.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.utils.Util;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class UploadAction extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	    public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {

	        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
	        BlobKey blobKey = blobs.get("myFile");
	        Scanner data = new Scanner( new String(blobstoreService.fetchData(blobKey,0,1015807)));
	        
	        if (blobKey == null) {
	            res.sendRedirect("/");
	        } else {
	        	//titulo
	        	data.nextLine();
	        	while (data.hasNextLine()) {
	        		  String line = data.nextLine();
	        		  com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getQueue("ActionQueue");
	        		  TaskOptions taskOptions = TaskOptions.Builder.withUrl("/action-queue")
	        		  	                          .param("action", line)
	        		  	                          .method(Method.POST)
	        		  	                          .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("action-backend"));
	        		  queue.add(taskOptions);
	        		}
	        }
	    }

	    //exemplo depois remover
	    public void doGet(HttpServletRequest req, HttpServletResponse res)
		      throws ServletException, IOException {
	    	PrintWriter out = res.getWriter();
	    	Key ancestorKey = KeyFactory.createKey("Politician",Integer.parseInt(req.getParameter("vereador")));
	    	Entity e = Util.findEntity(ancestorKey);
	    	Map<String, Object> properties = e.getProperties();
	    	 for (String key : properties.keySet()) {
	    	        out.print("\"" + key + "\" : \"" + properties.get(key) + "\",");
	    	      }
	    	out.print("</br>");
	    	
	    	out.print(Util.writeJSON(Util.listChildren("Action", ancestorKey)));
	    }
}
