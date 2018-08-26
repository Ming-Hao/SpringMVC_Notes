package com.ku.TestSystem.Service;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ku.TestSystem.DataBeans.ImageFileInfo;


@Controller
public class ShowController {
	@RequestMapping(value = "/myalbum", method = RequestMethod.GET)
    public  String uploadMultipleFileHandler(Model model) {
    	XmlHandler h = new XmlHandler();
    	List<ImageFileInfo> paths= h.ReadXml(FileHelper.DataResoprosity + "Default.xml");
    	model.addAttribute("src_lists", paths);
        return "album";
	}
	@RequestMapping(value = "/myalbum", method = RequestMethod.POST)
    public  String uploadMultipleFileHandler(Model model,  @RequestParam(value="filepath",required=false) String omit_file) {
    	System.out.println("11111111111111111111111"+omit_file);
		
		XmlHandler h = new XmlHandler();
    	List<ImageFileInfo> paths = h.ReadXml(FileHelper.DataResoprosity + "Default.xml", omit_file);
    	
    	model.addAttribute("src_lists", paths);
        return "album";
	}
}
