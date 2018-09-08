package com.ku.TestSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ku.TestSystem.DataBeans.ImageFileInfo;
import com.ku.TestSystem.Service.FileHelper;
import com.ku.TestSystem.Service.XmlHandler;


@Controller
public class UploadController {
    @RequestMapping(value = "/upload_multiple", method = RequestMethod.POST)
    public  String uploadMultipleFileHandler(Model model, @RequestParam("file_multiple") List<MultipartFile> multiPartFileList) {
    	
    	model.addAttribute("filename", "Upload FAIL");
        model.addAttribute("Name", "No name");
        model.addAttribute("Type", "No type");
        
        List<ImageFileInfo> paths = new ArrayList<ImageFileInfo>();
        for(int i = 0; i < multiPartFileList.size(); i++) {
        	
        	MultipartFile fileName = multiPartFileList.get(i);
        	String uuid = UUID.randomUUID().toString().replaceAll("-","");
        	if(FileHelper.FileStore(fileName, uuid, FileHelper.DataResoprosity))
        	{
        		ImageFileInfo img = new ImageFileInfo();

    			String uniqueFileName = FileHelper.FileNameWithExtension(fileName, uuid);
    			String virtualPath = FileHelper.VirtualRoot + uniqueFileName;
    			
        		img.setFilename(fileName.getOriginalFilename());
        		img.setName(uniqueFileName);
        		img.setType(fileName.getContentType());
        		img.setPath(virtualPath);
        		img.setSize(fileName.getSize());
        		
                paths.add(img);
                
                System.out.println(FileHelper.DataResoprosity + File.separator + fileName.getOriginalFilename());
                System.out.println(fileName.getName());
        	}
        }
        model.addAttribute("src_lists", paths);
        XmlHandler h = new XmlHandler();
        h.AppendXml(FileHelper.DataResoprosity + "Default.xml", paths);
        return  "sucess";
    }
    
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	private String fildUpload(Model model , @RequestParam(value="file",required=false) MultipartFile file)throws Exception{

        List<ImageFileInfo> paths = new ArrayList<ImageFileInfo>();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
		if (FileHelper.FileStore(file, uuid, FileHelper.DataResoprosity)) {
			
			String uniqueFileName = FileHelper.FileNameWithExtension(file, uuid);
			String virtualPath = FileHelper.VirtualRoot + uniqueFileName;
			
			ImageFileInfo img = new ImageFileInfo();
			
    		img.setFilename(file.getOriginalFilename());
    		img.setName(uniqueFileName);
    		img.setType(file.getContentType());
    		img.setPath(virtualPath);
    		img.setSize(file.getSize());
    		
            paths.add(img);
            
			System.out.println(FileHelper.DataResoprosity + File.separator + file.getOriginalFilename());
			System.out.println(file.getName());
		}
        XmlHandler h = new XmlHandler();
        h.AppendXml(FileHelper.DataResoprosity + "Default.xml", paths);
        model.addAttribute("src_lists", paths);
		return "sucess";
	}
    
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(Model model) {
    	return "signup";
    }
    

}
