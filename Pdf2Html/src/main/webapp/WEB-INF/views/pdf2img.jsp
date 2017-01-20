<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="resources/bootstrap-3.3.2-dist/css/bootstrap.min.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="resources/bootstrap-3.3.2-dist/js/bootstrap.min.js"></script>
<title>pdf2img</title>
<style type="text/css">
body, html {
	margin: 0 auto;
	padding: 0;
	height: 100%;
	width: 100%;
	min-width: 1024px;
}

#title1 {
	margin-top: 200px;
}

.view_body {
	width: 70%;
	margin: 0 auto;
	margin-top: 20px;
}

.h1 {
	font-family: "맑은 고딕";
	font-size: 80px;
	font-weight: bold;
}

.footer {
	width: 50%;
	height: 15%;;
	margin: 0 auto;
}

.logo {
	width: 96px;
	float: left;
}

.address {
	width: 450px;
	float: left;
}

.logoR {
	float: left;
}

.imgs {
	margin: 0 auto;
}
</style>
</head>
<body>
	<div style="text-align: center;">
		<h1 id="title1" class="h1">PDF 2 HTML</h1>
	</div>
	<div class="view_body">
		<c:forEach items="${fileList}" var="file">
			<c:forEach begin="0" end="${pageInfo.get(file.key)-1}" varStatus="status">
				<img src="/htmlList/${file.key}-${status.index+1}.png" width="100%"
					class="imgs" />
			</c:forEach>
		</c:forEach>
	</div>
	<div class="footer">
		<p class="logo">
			<img src="resources/image/footer_logo.gif" alt="footer">
		</p>
		<p class="address">
			(06252) 서울시 강남구 강남대로 330 5층 (역삼동, 우덕빌딩) <br> <span>Tel.
				(02) 2051-4151 / Fax. (02) 3454-1100 / <a
				href="mailto: mobileleader@mobileleader.com">Email.
					mobileleader@mobileleader.com </a><br> Copyright ⓒ 2012
				Mobileleader. All rights reserved
			</span>
		</p>
		<p class="logoR">
			<a href="http://www.inzisoft.com/" target="_blank"><img
				src="resources/image/footer_logo_inzi.gif" alt="footer"></a><a
				href="http://www.2gram.kr/" target="_blank"
				style="margin-left: 30px"><img
				src="resources/image/footer_logo_2gram.gif" alt="footer"></a>
		</p>
	</div>
</body>
</html>