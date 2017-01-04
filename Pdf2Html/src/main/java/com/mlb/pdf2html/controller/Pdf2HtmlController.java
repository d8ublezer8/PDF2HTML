package com.mlb.pdf2html.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.mlb.pdf2html.service.Pdf2HtmlService;
import com.mlb.pdf2html.util.FileUpload;

@Controller
public class Pdf2HtmlController {
	private static final Logger logger = LoggerFactory
			.getLogger(Pdf2HtmlController.class);

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
		return "minimap";
	}

	@RequestMapping(value = "/WEB-INF/views/htmllist/{filename}", method = RequestMethod.GET)
	public String htmlList(Model model, @PathVariable String filename) {

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
	public String fileUploadAjax(HttpSession session,
			@RequestParam(value = "file") MultipartFile file,
			MultipartRequest multipartRequest, HttpServletRequest request)
			throws IOException {
		int i = 1;
		// MultipartFile file = multipartRequest.getFile("file"); //酉곗뿉�꽌
		// form�쑝濡� �꽆�뼱�삱 �븣 name�뿉 �쟻�뼱以� �씠由꾩엯�땲�떎.��
		List<String> writtenFileNames = new LinkedList<String>();
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);
		String fileType = fileName.substring(fileName.lastIndexOf("."),
				fileName.length());

		// String replaceName = fileName ; ��

		if (writtenFileNames.contains(fileName)) {
			fileName = fileName.substring(0, fileName.lastIndexOf(".") - 1);
			System.out.println(fileName);
			fileName = fileName + i;
			fileName = fileName + fileType;

			i++;
		}
		writtenFileNames.add(fileName);

		String path = session.getServletContext().getRealPath("pdflist");
		System.out.println(path);
		// String path = "C:/"; //�젣 諛뷀깢�솕硫댁쓽 upload �뤃�뜑�씪�뒗 寃쎈줈�엯�땲�떎. �옄�떊�쓽
		// 寃쎈줈瑜� �벐�꽭�슂.��
		System.out.println("test");
		File f = new File(path);
		File[] fList = f.listFiles();
		FileUpload.fileUpload(file, path, fileName);

		return "redirect:/convert";
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
