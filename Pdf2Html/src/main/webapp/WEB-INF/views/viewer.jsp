<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<title>viewer</title>
<link rel="stylesheet" href="resources/minimap/minimap.min.css" />
<link rel="stylesheet"
	href="resources/bootstrap-3.3.2-dist/css/bootstrap.min.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="resources/bootstrap-3.3.2-dist/js/bootstrap.min.js"></script>
<script src="resources/minimap/minimap.min.js"></script>
<script type="text/javascript">
	$(function() {

		$('.view_body').minimap({
			heightRatio : 1.0,
			widthRatio : 0.15,
			offsetHeightRatio : 0.3,
			offsetWidthRatio : 0.0,
			position : "left",
			touch : true,
			smoothScroll : true,
			smoothScrollDelay : 200,
			onPreviewChange : function() {
			}
		});

		var page = $('body>div:nth-child(3)>.page')
		$('#totalPages').html('/' + page.length);
		for (var i = 0; i < page.length; i++) {
			page[i].id = 'page_' + (i + 1);
		}
		$('#pageMove').click(function() {
			if (parseInt($('#pageNum').val()) <= 1) {
				location.href = '#page_1';
				$('#pageNum').val(1);
			} else if (parseInt($('#pageNum').val()) >= page.length) {
				location.href = '#page_' + page.length;
				$('#pageNum').val(page.length);
			} else {
				location.href = '#page_' + (parseInt($('#pageNum').val()));

			}
		});
	})
</script>
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

.h1 {
	font-family: "맑은 고딕";
	font-size: 80px;
	font-weight: bold;
}

.view_body {
	width: 70%;
	margin: 0 auto;
	margin-top: 20px;
}

.page {
	margin: 0 auto !important;
}

#pageNum {
	width: 30px;
}

#pageNavi {
	position: fixed;
	left: 5%;
	top: 150px;
}
</style>
</head>
<body>
	<div style="text-align: center;">
		<h1 id="title1" class="h1">PDF 2 HTML</h1>
	</div>
	<div id="pageNavi">
		<input type="text" id="pageNum" value="1"><span
			id="totalPages"></span>
		<button id="pageMove">이동</button>
	</div>
	<div class="view_body">
		<c:forEach items="${fileList}" var="file">
			<c:import url="/jsplist/${file.key}.html" charEncoding="UTF-8"></c:import>
		</c:forEach>
	</div>
</body>
</html>
