<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>minimap</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="resources/bootstrap-3.3.2-dist/css/bootstrap.min.js"></script>
<link rel="stylesheet" href="resources/minimap/minimap.min.css" />
<script src="resources/minimap/minimap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('body').minimap({
			heightRatio : 0.3,
			widthRatio : 0.25,
			offsetHeightRatio : 0.0,
			offsetWidthRatio : 0.0,
			position : "right",
			touch : true,
			smoothScroll : true,
			smoothScrollDelay : 200,
			onPreviewChange : function() {
			}
		});
	})
</script>
<style type="text/css">
#title1 {
	margin-top: 200px;
}

.h1 {
	font-family: "¸¼Àº °íµñ";
	font-size: 80px;
	font-weight: bold;
}

.mainButton {
	background-color: lightblue;
	color: white;
	border: none;
	width: 500px;
	height: 70px;
	display: block;
	text-align: center;
	font-size: 40px;
	font-family: ¸¼Àº °íµñ;
	font-weight: bold;
	margin: 10px;
	border: 0;
	outline: 0;
}

.mainButton:hover {
	background-color: white;
	color: lightblue;
	border: none;
	width: 500px;
	height: 70px;
	display: block;
	text-align: center;
	font-size: 40px;
	font-family: ¸¼Àº °íµñ;
	font-weight: bold;
	margin: 10px;
}

.mainButton:active {
	border: none;
}
</style>
</head>
<body>
	<div style="text-align: center;">
		<form method="POST" id="frm" action="">
			<h1 id="title1" class="h1">PDF 2 HTML</h1>

		</form>
	</div>
	<div class="view_body">
		<c:import url="/htmllist/${view}"></c:import>
	</div>
</body>
</html>
