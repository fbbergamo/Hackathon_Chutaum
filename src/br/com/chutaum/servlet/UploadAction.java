package br.com.chutaum.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVReader;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.blobstore.BlobInfo;
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
	       
	        PrintWriter out = res.getWriter();
	        

	        StringReader string = new StringReader(new String(blobstoreService.fetchData(blobKey,0,1015807)));
	        
	        CSVReader reader=new CSVReader(string,';');
	       
	        
	        if (blobKey == null) {
	            res.sendRedirect("/");
	        } else {
			    String[] line;
			    reader.readNext();
	    		while((line=reader.readNext())!=null){
    			StringBuilder stb=new StringBuilder(400);
		        for(int i=0;i<line.length;i++){
		        	stb.append(line[i]);
		        	stb.append(';');
		            
		        }
    			com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getQueue("ActionQueue");
    			TaskOptions taskOptions = TaskOptions.Builder.withUrl("/action-queue")
	        		  	                          .param("action", stb.toString())
	        		  	                          .method(Method.POST)
	        		  	                          .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("action-backend"));
    			queue.add(taskOptions);
	    			
	        	}
	        	
	        }

	    }
}
