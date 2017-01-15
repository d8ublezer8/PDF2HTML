package com.mlb.pdf2html.service;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public interface Pdf2HtmlService {

	public void convertPdf2Html(String pdfPath, String jspPath,HashMap<String, String> files);

	public HashMap<String, String> pdfUpload(MultipartFile[] multipartFiles, String pdfDir);
}
