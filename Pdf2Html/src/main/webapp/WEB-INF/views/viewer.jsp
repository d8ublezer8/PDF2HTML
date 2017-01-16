<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
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

		/* $('.view_body').minimap({
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
		}); */

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
	font-family: "¸¼Àº °íµñ";
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
</style>
</head>
<body>
	<div style="text-align: center;">
		<h1 id="title1" class="h1">PDF 2 HTML</h1>
	</div>
	<div id="pageNavi">
		<input type="text" id="pageNum" value="1"><span
			id="totalPages"></span>
		<button id="pageMove">ÀÌµ¿</button>
	</div>
	<div class="view_body">
		<c:forEach items="${fileList}" var="file">
			<c:import url="/jsplist/${file.key}"></c:import>
		</c:forEach>
	</div>
	<div class="footer">
		<p class="logo">
			<img src="resources/image/footer_logo.gif" alt="footer">
		</p>
		<p class="address">
			(06252) ¼­¿ï½Ã °­³²±¸ °­³²´ë·Î 330 5Ãþ (¿ª»ïµ¿, ¿ì´öºôµù) <br> <span>Tel.
				(02) 2051-4151 / Fax. (02) 3454-1100 / <a
				href="mailto: mobileleader@mobileleader.com">Email.
					mobileleader@mobileleader.com </a><br> Copyright ¨Ï 2012
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
