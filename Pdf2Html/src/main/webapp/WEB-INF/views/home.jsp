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
 <input type="submit" value = "upload"></form>
 
 
 
</body>
</html>
