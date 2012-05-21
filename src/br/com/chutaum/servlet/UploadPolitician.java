package br.com.chutaum.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class UploadPolitician extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	    public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
	    	    Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		        BlobKey blobKey = blobs.get("myFile");
		        Scanner data = new Scanner( new String(blobstoreService.fetchData(blobKey,0,1015807),"UTF8"));
		        
		        if (blobKey == null) {
		            res.sendRedirect("/");
		        } else {
		        	//titulo
		        	data.nextLine();
		        	
		        	while (data.hasNextLine()) {
		        			String line = data.nextLine();
		        		  com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getQueue("PoliticianQueue");
		        		  TaskOptions taskOptions = TaskOptions.Builder.withUrl("/politician-queue")
		        		  	                          .param("politician", line)
		        		  	                          .method(Method.POST)
		        		  	                          .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("action-backend"));
		        		  queue.add(taskOptions);    	
		        	}
		        }
	    }
	    
}