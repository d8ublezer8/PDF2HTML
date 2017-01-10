package com.mlb.pdf2html.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.mlb.pdf2html.domain.FileNameSet;

public interface Pdf2HtmlService {

	public ArrayList<String> convertPdf2Html(String pdfPath, String jspPath,ArrayList<FileNameSet> fileNameSetDaos);

	public ArrayList<FileNameSet> pdfUpload(MultipartFile[] multipartFiles, String pdfDir);
}
