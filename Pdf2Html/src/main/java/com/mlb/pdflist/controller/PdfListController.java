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

		ArrayList<PdfFileVo> pd = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			PdfFileVo pdf = new PdfFileVo();
			pdf.setTitle(i + "" + i);
			pdf.setDate(i + "" + i);
			pd.add(pdf);
		}

		PdfListDaoImp dao = new PdfListDaoImp();
		dao.setPdfList(path, pd);

		ArrayList<PdfFileVo> pdfF = dao.getPdfList(path);

		// for(PdfFileVo ppp:pdfF){
		// System.out.println(ppp.toString());
		// }
		String path2 = session.getServletContext().getRealPath("pdf");
	
		return "home";
	}
}
