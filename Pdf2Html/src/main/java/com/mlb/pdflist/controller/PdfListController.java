package com.mlb.pdflist.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.mlb.pdflist.service.FileUpload;
import com.mlb.pdflist.service.PdfListService;

@Controller
public class PdfListController {

	private static final Logger logger = LoggerFactory.getLogger(PdfListController.class);
	private List<String> writtenFileNames = new LinkedList<String>();
	 int i = 1;

	@Autowired
	private PdfListService pdfListService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		String path = session.getServletContext().getRealPath("pdflist");
		// System.out.println(path);

		return "home";
	}
	@RequestMapping(value = "/viewer", method = RequestMethod.GET)
	public String viewer(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		String path = session.getServletContext().getRealPath("pdflist");
		// System.out.println(path);

		return "viewer";
	}
	
	@RequestMapping(value = "/fileupload",method = RequestMethod.POST)
	
	public String fileUploadAjax(HttpSession session,MultipartRequest multipartRequest, HttpServletRequest request) throws IOException{

	MultipartFile file = multipartRequest.getFile("file");  //뷰에서 form으로 넘어올 때 name에 적어준 이름입니다.​

	String fileName = file.getOriginalFilename();
	System.out.println(fileName);
	String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length() );
	
	//String replaceName = fileName ; ​
	
	
	if(writtenFileNames.contains(fileName))
	{
		 fileName = fileName.substring(0, fileName.lastIndexOf(".")-1);
		 System.out.println(fileName);
		fileName = fileName+i;
		fileName = fileName+fileType;
		
		i++;
	}
	writtenFileNames.add(fileName);
	
	String path = session.getServletContext().getRealPath("pdflist");
	System.out.println(path);
	//String path = "C:/";  //제 바탕화면의 upload 폴더라는 경로입니다. 자신의 경로를 쓰세요.​
    System.out.println("test");
	File f = new File(path);
	File[] fList = f.listFiles();
	FileUpload.fileUpload(file, path, fileName);
	
	

	
	
	
	return "home";
	}
	
	
	
	@RequestMapping(value = "/loading", method = RequestMethod.GET)
	public String loading(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		String path = session.getServletContext().getRealPath("pdflist");
		// System.out.println(path);

		return "loading";
	}
	

}
