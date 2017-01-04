package com.mlb.pdf2html.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	public static void fileUpload(MultipartFile fileData, String path,
			String fileName) throws IOException {
		// String originalFileName = fileData.getOriginalFilename();
		// String contentType = fileData.getContentType();
		long fileSize = fileData.getSize();
		/*
		 * System.out.println("file Info"); System.out.println("fileName " +
		 * fileName); System.out.println("originalFileName :" +
		 * originalFileName); System.out.println("contentType :" + contentType);
		 * System.out.println("fileSize :" + fileSize);
		 */
		InputStream is = null;
		OutputStream out = null;
		try {
			if (fileSize > 0) {
				is = fileData.getInputStream();
				File realUploadDir = new File(path);

				if (!realUploadDir.exists()) { // ��ο� ������ �������� ������
												// �����մϴ�.
					System.out.println("���� ���� ����");
					realUploadDir.mkdirs();
				}

				out = new FileOutputStream(path + "/" + fileName);
				FileCopyUtils.copy(is, out); // InputStream���� �� ������
				// outputStream���� ����
			} else {
				new IOException("�߸��� ������ ���ε� �ϼ̽��ϴ�.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			new IOException("���� ���ε忡 �����Ͽ����ϴ�.");
		} finally {
			if (out != null) {
				out.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}
}