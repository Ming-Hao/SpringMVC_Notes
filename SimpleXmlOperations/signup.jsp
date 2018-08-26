<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
    <head>
       <title>Upload File Request Page</title>
    </head>
    <body>
        <form method="POST" action="/TestSystem/uploadFile" enctype="multipart/form-data" >
           <h3>Single file upload</h3>
           File to upload: <input type="file" name="file">
           <br/>
           <input type="submit" value="Upload"> Press here to upload the file!
        </form>
        <br/>
        <br/>
         <form method="POST" action="/TestSystem/upload_multiple" enctype="multipart/form-data" >
           <h3>Multiple file upload</h3>
           File to upload: <input type="file" name="file_multiple" multiple>
           <br/>
           <input type="submit" value="Upload"> Press here to upload the file!
        </form>
    </body>
</html>