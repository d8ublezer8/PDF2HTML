package com.mlb.pdflist.dao;

import java.util.ArrayList;

import com.mlb.pdflist.vo.PdfFileVo;

public interface PdfListDao {

	public ArrayList<PdfFileVo> getPdfList(String path);

	public void setPdfList(String path,ArrayList<PdfFileVo> pdfFileVos);
	
	public void insertPdfList(String path, PdfFileVo pdfFileVo);
	
	public void deletePdfList(String path, PdfFileVo pdfFileVo);
	
	public boolean checkPdfItem(String path,PdfFileVo pfv);

}
