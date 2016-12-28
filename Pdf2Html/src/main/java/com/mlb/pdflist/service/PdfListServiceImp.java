package com.mlb.pdflist.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlb.pdflist.dao.PdfListDao;
import com.mlb.pdflist.vo.PdfFileVo;

@Service
public class PdfListServiceImp implements PdfListService {

	@Autowired
	private PdfListDao pdfListDao;

	public ArrayList<PdfFileVo> getPdfFileList(String path) {
		// TODO Auto-generated method stub
		ArrayList<PdfFileVo> pdfFileVos = new ArrayList<>();
		File dirFile = new File(path);
		File[] fileList = dirFile.listFiles();
		for (File tempFile : fileList) {
			if (tempFile.isFile()) {
				String tempFileName = tempFile.getName();
				System.out.println(tempFileName);
			}
		}
		return pdfFileVos;
	}

	public boolean setPdfFileName(String path, String name) {
		// TODO Auto-generated method stub
		File file = new File(path + "/" + name);
		File changeFile = new File(
				path + "/" + name.substring(0, name.length()-4) + "-U" + System.currentTimeMillis() + ".pdf");
		if (!file.renameTo(changeFile)) {
			return false;
		}
		return true;
	}

}
