package com.mlb.pdflist.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.mlb.pdflist.service.FileUpload;
import com.mlb.pdflist.service.PdfListService;

@Controller
public class PdfListController {

	private static final Logger logger = LoggerFactory.getLogger(PdfListController.class);

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

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String fileUploadAjax(HttpSession session, MultipartRequest multipartRequest, HttpServletRequest request)
			throws IOException {

		MultipartFile file = multipartRequest.getFile("file"); // 酉곗뿉�꽌 form�쑝濡�
																// �꽆�뼱�삱 �븣
																// name�뿉 �쟻�뼱以�
																// �씠由꾩엯�땲�떎.��

		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		String replaceName = fileName + fileType; // �뙆�씪 �씠由꾩쓽 以묐났�쓣 留됯린 �쐞�빐�꽌
													// �씠由꾩쓣 �옱�꽕�젙�빀�땲�떎.��

		String path = session.getServletContext().getRealPath("pdflist");
		System.out.println(path);
		// String path = "C:/"; //�젣 諛뷀깢�솕硫댁쓽 upload �뤃�뜑�씪�뒗 寃쎈줈�엯�땲�떎. �옄�떊�쓽
		// 寃쎈줈瑜� �벐�꽭�슂.��
		System.out.println("test");
		File f = new File(path);
		File[] fList = f.listFiles();

		FileUpload.fileUpload(file, path, replaceName);

		return "home";
	}


	@RequestMapping(value="/minimap",method=RequestMethod.GET)
	public String minimap(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");

		String path = session.getServletContext().getRealPath("pdflist");
		// System.out.println(path);

		return "minimap";
	}

	@RequestMapping(value = "/loading", method = RequestMethod.GET)
	public String loading(HttpSession session, Model model) {
		logger.info(System.currentTimeMillis() + "");
		return "loading";
	}
		

}
