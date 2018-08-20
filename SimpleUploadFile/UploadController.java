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

import com.ku.TestSystem.Service.FileHelper;

@Controller
public class UploadController {
    @RequestMapping(value = "/upload_multiple", method = RequestMethod.POST)
    public  String uploadMultipleFileHandler(Model model, @RequestParam("file_multiple") List<MultipartFile> multiPartFileList) {
    	
    	model.addAttribute("filename", "Upload FAIL");
        model.addAttribute("Name", "No name");
        model.addAttribute("Type", "No type");
        
        List<String> paths = new ArrayList<String>();
        for(int i = 0; i < multiPartFileList.size(); i++) {
        	
        	MultipartFile fileName = multiPartFileList.get(i);
        	String uuid = UUID.randomUUID().toString().replaceAll("-","");
        	if(FileHelper.FileStore(fileName, uuid, FileHelper.DataResoprosity))
        	{
            	model.addAttribute("filename", fileName.getOriginalFilename());
                model.addAttribute("Name", fileName.getName());
                model.addAttribute("Type", fileName.getContentType());
                model.addAttribute("Size", fileName.getSize());
                
                String virtualPath = FileHelper.VirtualRoot + FileHelper.FileNameWithExtension(fileName, uuid);
                paths.add(virtualPath);
                System.out.println(FileHelper.DataResoprosity + File.separator + fileName.getOriginalFilename());
                System.out.println(fileName.getName());
        	}
        }
        model.addAttribute("src_lists", paths);
        return  "sucess";
    }
    
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	private String fildUpload(Model model , @RequestParam(value="file",required=false) MultipartFile file)throws Exception{

        List<String> paths = new ArrayList<String>();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
		if (FileHelper.FileStore(file, uuid, FileHelper.DataResoprosity)) {
			model.addAttribute("filename", file.getOriginalFilename());
			model.addAttribute("Name", file.getName());
			model.addAttribute("Type", file.getContentType());
			model.addAttribute("Size", file.getSize());

			String virtualPath = FileHelper.VirtualRoot + FileHelper.FileNameWithExtension(file, uuid);
			paths.add(virtualPath);
			System.out.println(FileHelper.DataResoprosity + File.separator + file.getOriginalFilename());
			System.out.println(file.getName());
		}

        model.addAttribute("src_lists", paths);
		return "sucess";
	}
    
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(Model model) {
    	return "signup";
    }
    

}
