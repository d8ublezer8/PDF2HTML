package com.mlb.pdf2html.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mlb.pdf2html.domain.FileNameSet;

@Service
public class Pdf2HtmlServiceImp implements Pdf2HtmlService {

	@Override
	public ArrayList<String> convertPdf2Html(String pdfPath, String jspPath, ArrayList<FileNameSet> fileNameSetDaos) {
		System.out.println("=======Service========");

		ArrayList<String> outFileList = new ArrayList<>();
		for (FileNameSet filename : fileNameSetDaos) {
			if (filename.getSfilename().toLowerCase().endsWith(".pdf")) {

				filename.setSfilename(filename.getSfilename().substring(0, filename.getSfilename().length() - 4));
				outFileList.add(filename.getSfilename());

			}
		}
		for (String file : outFileList) {
			PDDocument document = null;
			Writer output = null;
			try {
				System.out.println(pdfPath + file + ".pdf");
				document = PDDocument.load(new File(pdfPath + "/" + file + ".pdf"));
				System.out.println("file name : "+ file);
				PDFDomTreeConfig config = PDFDomTreeConfig.createDefaultConfig();
				PDFDomTree parser = new PDFDomTree(config);
				output = new PrintWriter(jspPath + file + ".jsp", "UTF-8");
				parser.writeText(document, output);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (document != null) {
					try {
						document.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return outFileList;
	}

	@Override
	public ArrayList<FileNameSet> pdfUpload(MultipartFile[] multipartFiles, String pdfDir) {
		int size = 0;
		File[] sysfiles = null;

		ArrayList<FileNameSet> filenames = new ArrayList<>();
		if (multipartFiles != null) {
			size = multipartFiles.length;
			sysfiles = new File[size];

			String rfilename = null;
			String sfilename = null;
			for (int i = 0; i < size; i++) {
				rfilename = multipartFiles[i].getOriginalFilename();
				sfilename = "pdf" + System.currentTimeMillis() + "_" + i + ".pdf";
				FileNameSet fileNameSetDao = new FileNameSet();
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
