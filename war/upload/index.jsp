
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Action CSV</title>
    </head>
    <body>
    <h1>Insira o CSV da Action, limite de 1MB</h1>
        <form action="<%= blobstoreService.createUploadUrl("/upload-action") %>" method="post" enctype="multipart/form-data" accept-charset="utf-8">
            <input type="text" name="foo">
            <input type="file" name="myFile">
            <input type="submit" value="Submit">
        </form>
        
    </br>
    <h1>Insira o CSV do Vereador, limite de 1MB</h1>
        <form action="<%= blobstoreService.createUploadUrl("/upload-politician") %>" method="post" enctype="multipart/form-data" accept-charset="utf-8">
            <input type="text" name="foo">
            <input type="file" name="myFile">
            <input type="submit" value="Submit">
        </form>    
    </body>
</html>