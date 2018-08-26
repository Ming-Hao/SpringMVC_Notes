package com.ku.TestSystem.Service;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileHelper {

	static public String DataResoprosity = "D:\\~tmp\\tmpFiles\\";
	static public String VirtualRoot = "/tmp_pic/";
	static public Boolean FileStore(MultipartFile file, String uniqueID, String saveTo) {
		
		if (!file.isEmpty()) {
            try {
                File dir = new File(saveTo);
                if (!dir.exists())
                    return false;
                
                String path = FileNameWithExtension(file, uniqueID);
    			
    			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(saveTo + path));
                System.out.println("File has stored");
                return true;
                
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            	return false;
            }
        } else {
        	System.out.println("File not exist");
        	return false;
        }
        
	}
	static public String FileNameWithExtension(MultipartFile file, String prefix) {
		String contentType = file.getContentType();
		String extensionType = contentType.substring(contentType.indexOf("/")+1);    			
		String path = prefix + "." + extensionType;
		return path;
	}
}
