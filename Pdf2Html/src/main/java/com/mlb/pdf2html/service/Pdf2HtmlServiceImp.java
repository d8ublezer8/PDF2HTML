package com.mlb.pdf2html.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.springframework.stereotype.Service;

@Service
public class Pdf2HtmlServiceImp implements Pdf2HtmlService {

	@Override
	public String convertPdf2Html(String path, String filename) {
		PDDocument document = null;
		Writer output = null;
		if (filename.toLowerCase().endsWith(".pdf")) {
			filename = filename.substring(0, filename.length() - 4);
		}
		String outfile = filename + ".jsp";
		try {
			document = PDDocument.load(new File(path + "pdflist/" + filename+".pdf"));
			PDFDomTreeConfig config = PDFDomTreeConfig.createDefaultConfig();
			PDFDomTree parser = new PDFDomTree(config);
			output = new PrintWriter(path+"WEB-INF/views/htmllist/"+outfile,"UTF-8");
			parser.writeText(document, output);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}finally {
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(document!=null){
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return "htmllist/"+outfile;
	}

}
