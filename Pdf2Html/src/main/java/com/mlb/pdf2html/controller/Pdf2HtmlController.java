package com.mlb.pdf2html.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mlb.pdf2html.dao.FileNameSetDao;
import com.mlb.pdf2html.service.Pdf2HtmlService;

@Controller
public class Pdf2HtmlController {
	private static final Logger logger = LoggerFactory.getLogger(Pdf2HtmlController.class);

	@Autowired
	private Pdf2HtmlService pdf2HtmlService;

	@RequestMapping(value = "/convert", method = RequestMethod.GET)
	public String convert(HttpSession session, Model model) {
		System.out.println("===========convert===========");
		@SuppressWarnings("unchecked")
		ArrayList<FileNameSetDao> fileList = (ArrayList<FileNameSetDao>) session.getAttribute("fileList");
		for(FileNameSetDao a: fileList){
			System.out.println(a);
		}
		String pdfdir = session.getServletContext().getRealPath("pdflist/");

		String testUrl = pdf2HtmlService.convertPdf2Html(path, filename);
		System.out.println("URL:" + testUrl);
		model.addAttribute("view", testUrl);
		return "minimap";
	}

	@RequestMapping(value = "htmllist/{filename}", method = RequestMethod.GET)
	public String htmlList(Model model, @PathVariable String filename) {
		System.out.println(filename);
		return "htmllist/" + filename;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		return "home";
	}

	@RequestMapping(value = "/viewer", method = RequestMethod.GET)
	public String viewer(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		return "viewer";
	}

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String fileUploadAjax(HttpSession session, @RequestParam(value = "file") MultipartFile[] file) {
		System.out.println("========fileupload============");
		ArrayList<FileNameSetDao> fileList = new ArrayList<>();
		System.out.println(file.length);
		String pdfdir = session.getServletContext().getRealPath("pdflist/");
		System.out.println(pdfdir);
		fileList = pdf2HtmlService.pdfUpload(file, pdfdir);
		session.setAttribute("fileList", fileList);
		System.out.println("========fileupload============");
		return "redirect:convert";
	}

	@RequestMapping(value = "/minimap", method = RequestMethod.GET)
	public String minimap(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		return "minimap";
	}

	@RequestMapping(value = "/loading", method = RequestMethod.GET)
	public String loading(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");
		return "loading";
	}
}
