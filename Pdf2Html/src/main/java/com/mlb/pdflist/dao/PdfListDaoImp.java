package com.mlb.pdflist.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlb.pdflist.vo.PdfFileVo;

@Repository
public class PdfListDaoImp implements PdfListDao {

	@Override
	public ArrayList<PdfFileVo> getPdfList(String path) {
		// TODO Auto-generated method stub
		String temp = "";
		String contents = "";
		ArrayList<PdfFileVo> pdfFileVos = new ArrayList<>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		ObjectMapper mapper = new ObjectMapper();

		File file = new File(path + "/pdflist.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);

			while ((temp = br.readLine()) != null) {
				contents += temp;
			}
			contents = contents.replaceAll("\\},\\{", "?");
			String[] content = contents.substring(2, contents.length() - 2).split("\\?");
			for (String pdfFile : content) {
				pdfFileVos.add(mapper.readValue("{" + pdfFile + "}", PdfFileVo.class));
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pdfFileVos;
	}

	@Override
	public void setPdfList(String path, ArrayList<PdfFileVo> pdfFileVos) {
		ObjectMapper mapper = new ObjectMapper();
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		File file = new File(path + "/pdflist.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(osw);
			mapper.writeValue(bw, pdfFileVos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean checkPdfItem(String path, PdfFileVo pfv) {
		// TODO Auto-generated method stub
		for (PdfFileVo pfvlist : getPdfList(path)) {
			if (pfvlist.getTitle().equals(pfv.getTitle()) && pfvlist.getDate().equals(pfv.getDate())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void insertPdfList(String path, PdfFileVo pdfFileVo) {
		// TODO Auto-generated method stub
		ArrayList<PdfFileVo> pdfFileVos = getPdfList(path);
		pdfFileVos.add(pdfFileVo);
		setPdfList(path, pdfFileVos);
	}

	@Override
	public void deletePdfList(String path, PdfFileVo pdfFileVo) {
		// TODO Auto-generated method stub
		ArrayList<PdfFileVo> pdfFileVos = getPdfList(path);
		for (PdfFileVo pfvlist : pdfFileVos) {
			if (pfvlist.getTitle().equals(pdfFileVo.getTitle()) && pfvlist.getDate().equals(pdfFileVo.getDate())) {
				pdfFileVos.remove(pfvlist);
				setPdfList(path, pdfFileVos);
			}
		}
	}

}
