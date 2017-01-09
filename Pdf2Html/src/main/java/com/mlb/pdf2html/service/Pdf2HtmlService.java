package com.mlb.pdf2html.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.mlb.pdf2html.dao.FileNameSetDao;

public interface Pdf2HtmlService {

	public String convertPdf2Html(String pdfPath, String jspPath,ArrayList<FileNameSetDao> fileNameSetDaos);

	public ArrayList<FileNameSetDao> pdfUpload(MultipartFile[] multipartFiles, String pdfDir);
}
