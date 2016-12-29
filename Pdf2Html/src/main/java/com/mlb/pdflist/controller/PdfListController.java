package com.mlb.pdflist.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mlb.pdflist.dao.PdfListDaoImp;
import com.mlb.pdflist.service.PdfListService;
import com.mlb.pdflist.vo.PdfFileVo;

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
}
