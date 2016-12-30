<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<html>
<head>
<title>Home</title>
<script src="resources/jquery-3.1.1.min.js"></script>
<script src="resources/bootstrap-3.3.2-dist/css/bootstrap.min.js"></script>

 <style type="text/css">
  #title1{
   margin-top : 200px;
  }

  .h1{
  font-family:"맑은 고딕";
  font-size: 80px;
  font-weight:bold;
  }
  
  .mainButton{
  background-color: lightblue;
  color: white;
  border: none;
  width: 500px;
  height: 70px;
  display: block;
  text-align: center;
  font-size:40px;
  font-family: 맑은 고딕;
  font-weight:bold;
  margin: 10px;
  border:0;
  outline: 0;
  }
  .mainButton:hover{
  background-color: white;
  color: lightblue;
  border: none;
  width: 500px;
  height: 70px;
  display: block;
  text-align: center;
  font-size:40px;
  font-family: 맑은 고딕;
  font-weight:bold;
  margin: 10px;
 }
  .mainButton:active{
  border:none;}
 </style>
 <script>
 function fileCheck(frm) {   

	  var file = frm.file.value;
	  var fileExt = file.substring(file.lastIndexOf('.')+1); //파일의 확장자를 구합니다.
	  var bSubmitCheck = true;
	  
	  if( !file ){ 
	    alert( "파일을 선택하여 주세요!");
	    return;
	   }
	   
	   if(fileExt.toUpperCase() != "PDF")
		   {
		   alert("파일 업로드를 시작합니다.");
	   frm.submit();
	   }
	   else
	   {
	   alert(".PDF파일만 업로드 가능합니다");
	   return;
	   }
	}


</script>
 
</head>
<body>
 <div style="text-align:center;">
  <form method="POST" id="frm" action="">
   <h1 id="title1" class = "h1">PDF 2 HTML</h1>

  </form>
 </div>
<div class="butttonspace">
   <div class="span7" align= "center">                     
  <input type="button" class="mainButton" value="Upload" onclick="">
   </div>
 </div>
 
 
 <form action="/fileupload" method="post" enctype = "multipart/form-data" >
 <input type = "file" name="file"multiple accept = ".pdf">
<INPUT name="submitBtn" type="button"  value="전송" onclick="fileCheck(this.form)"></form>
 
 
 
</body>
</html>
