package com.mlb.pdf2html.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mlb.pdf2html.dao.FileNameSetDao;

@Service
public class Pdf2HtmlServiceImp implements Pdf2HtmlService {

	@Override
	public ArrayList<String> convertPdf2Html(String pdfPath, String jspPath,
			ArrayList<FileNameSetDao> fileNameSetDaos) {
		System.out.println("=======Service========");
		PDDocument document = null;
		Writer output = null;
		ArrayList<String> outFileList = new ArrayList<>();
		for (FileNameSetDao filename : fileNameSetDaos) {
			if (filename.getSfilename().toLowerCase().endsWith(".pdf")) {
				filename.setSfilename(filename.getSfilename().substring(0, filename.getSfilename().length() - 4));

				outFileList.add(filename.getSfilename());
				document = PDDocument.load(new File(pdfPath, outfile + ".pdf"));
				PDFDomTreeConfig config = PDFDomTreeConfig.createDefaultConfig();
				PDFDomTree parser = new PDFDomTree(config);
				output = new PrintWriter(jspPath + outfile + ".jsp", "UTF-8");
				parser.writeText(document, output);
			}
		}

		return outFileList;
	}

	@Override
	public ArrayList<FileNameSetDao> pdfUpload(MultipartFile[] multipartFiles, String pdfDir) {
		int size = 0;
		File[] sysfiles = null;

		ArrayList<FileNameSetDao> filenames = new ArrayList<>();
		if (multipartFiles != null) {
			size = multipartFiles.length;
			sysfiles = new File[size];

			String rfilename = null;
			String sfilename = null;
			for (int i = 0; i < size; i++) {
				rfilename = multipartFiles[i].getOriginalFilename();
				sfilename = "pdf" + System.currentTimeMillis() + "_" + i + ".pdf";
				FileNameSetDao fileNameSetDao = new FileNameSetDao();
				fileNameSetDao.setRfilename(rfilename);
				fileNameSetDao.setSfilename(sfilename);
				filenames.add(fileNameSetDao);

				System.out.println(pdfDir + "/" + sfilename);
				sysfiles[i] = new File(pdfDir + "/" + sfilename);
				try {
					multipartFiles[i].transferTo(sysfiles[i]);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filenames;
	}

}
