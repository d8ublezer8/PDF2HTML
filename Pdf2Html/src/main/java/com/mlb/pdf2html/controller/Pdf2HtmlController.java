package com.mlb.pdf2html.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mlb.pdf2html.service.Pdf2HtmlService;
import com.mlb.pdflist.controller.PdfListController;

@Controller
public class Pdf2HtmlController {
	private static final Logger logger = LoggerFactory.getLogger(PdfListController.class);

	@Autowired
	private Pdf2HtmlService pdf2HtmlService;
	
	@RequestMapping(value = "/convert", method = RequestMethod.GET)
	public String convert(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		String path = session.getServletContext().getRealPath("");
		System.out.println(path);

		String testUrl = pdf2HtmlService.convertPdf2Html(path, "p5.pdf");
		System.out.println(testUrl);
		model.addAttribute("view", testUrl);
		return "viewresult";
	}
	
	@RequestMapping(value="/WEB-INF/views/htmllist/{filename}",method=RequestMethod.GET)
	public String htmlList(Model model,@PathVariable String filename){
		
		return "htmllist/"+filename;
	}
}
