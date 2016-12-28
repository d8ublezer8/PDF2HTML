package com.mlb.pdflist.dao;

import java.util.ArrayList;

import com.mlb.pdflist.vo.PdfFileVo;

public interface PdfFileDao {
	public ArrayList<PdfFileVo> getPdfFiles(String path);

	public void setPdfFileName(String path, String name);
	
	public void removePdfFile(String path, String name);

	public boolean checkPdfFile(String path, String name);
}
