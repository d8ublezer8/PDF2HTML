package com.mlb.pdflist.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
public static void fileUpload(MultipartFile fileData, String path, String fileName) throws IOException {
//String originalFileName = fileData.getOriginalFilename();
//String contentType = fileData.getContentType();
long fileSize = fileData.getSize();
/*
* System.out.println("file Info"); 
* System.out.println("fileName " + fileName); 
* System.out.println("originalFileName :" + originalFileName); 
* System.out.println("contentType :" + contentType);
* System.out.println("fileSize :" + fileSize);
*/
InputStream is = null;
OutputStream out = null;
try {
if (fileSize > 0) {
is = fileData.getInputStream();
File realUploadDir = new File(path);

if (!realUploadDir.exists()) { // 경로에 폴더가 존재하지 않으면 생성합니다.
System.out.println("폴더 존재 안함");
realUploadDir.mkdirs();
}

out = new FileOutputStream(path + "/" + fileName);
FileCopyUtils.copy(is, out); // InputStream에서 온 파일을
// outputStream으로 복사
} else {
new IOException("잘못된 파일을 업로드 하셨습니다.");
}
} catch (IOException e) {
e.printStackTrace();
new IOException("파일 업로드에 실패하였습니다.");
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