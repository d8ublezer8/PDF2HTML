package com.mlb.pdf2html.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mlb.pdf2html.util.PDFDomTreeExt;

@Service
public class Pdf2HtmlServiceImp implements Pdf2HtmlService {

	@Override
	public void convertPdf2Html(String pdfPath, String jspPath, HashMap<String, String> files) {
		System.out.println("=======Service : Convert========");

		for (Entry<String, String> file : files.entrySet()) {
			PDDocument document = null;
			Writer output = null;
			try {
				document = PDDocument.load(new File(pdfPath + "/" + file.getKey()));
				PDFDomTreeConfig config = PDFDomTreeConfig.createDefaultConfig();
				 PDFDomTreeExt parser = new PDFDomTreeExt(config);
				//PDFDomTree parser = new PDFDomTree(config);
				output = new PrintWriter(jspPath + file.getKey(), "UTF-8");
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
		System.out.println("=====Service : Convert End======");
	}

	@Override
	public HashMap<String, String> pdfUpload(MultipartFile[] multipartFiles, String pdfDir) {
		System.out.println("=====Service : fileUpload End======");
		int size = 0;
		File[] sysfiles = null;

		HashMap<String, String> files = new HashMap<>();
		if (multipartFiles != null) {
			size = multipartFiles.length;
			sysfiles = new File[size];

			String rfilename = null;
			String sfilename = null;
			for (int i = 0; i < size; i++) {
				rfilename = multipartFiles[i].getOriginalFilename();
				sfilename = "pdf" + System.currentTimeMillis() + "_" + i + ".jsp";
				files.put(sfilename, rfilename);

				sysfiles[i] = new File(pdfDir + "/" + sfilename);
				try {
					multipartFiles[i].transferTo(sysfiles[i]);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("=====Service : fileUpload End======");
		return files;
	}

}
