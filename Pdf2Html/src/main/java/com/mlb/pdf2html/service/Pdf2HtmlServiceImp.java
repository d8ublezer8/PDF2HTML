package com.mlb.pdf2html.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mlb.pdf2html.util.PDFDomTreeExt;

@Service
public class Pdf2HtmlServiceImp implements Pdf2HtmlService {

	@Override
	public HashMap<String, Integer> convertPdf2Html(String pdfPath, String jspPath, HashMap<String, String> files) {
		HashMap<String, Integer> pageInfo = new HashMap<>();
		for (Entry<String, String> file : files.entrySet()) {
			PDDocument document = null;
			Writer output = null;
			try {
				document = PDDocument.load(new File(pdfPath + "/" + file.getKey() + ".pdf"));
				int totalPage = document.getNumberOfPages();
				pageInfo.put(file.getKey(), totalPage);
				PDFRenderer pdfRenderer = new PDFRenderer(document);
				for (int page = 0; page < totalPage; ++page) {
					BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

					// suffix in filename will be used as the file format
					ImageIOUtil.writeImage(bim, jspPath + "/" + file.getKey() + "-" + (page + 1) + ".png",
							300);
				}
				PDFDomTreeConfig config = PDFDomTreeConfig.createDefaultConfig();
				PDFDomTreeExt parser = new PDFDomTreeExt(config);
				// PDFDomTree parser = new PDFDomTree(config);
				output = new PrintWriter(jspPath + file.getKey() + ".html", "UTF-8");
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
		return pageInfo;
	}

	@Override
	public HashMap<String, String> pdfUpload(MultipartFile[] multipartFiles, String pdfDir) {
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
				sfilename = "pdf" + System.currentTimeMillis() + "_" + i;

				sysfiles[i] = new File(pdfDir + "/" + sfilename + ".pdf");

				files.put(sfilename, rfilename);
				try {
					multipartFiles[i].transferTo(sysfiles[i]);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		return files;
	}

}
