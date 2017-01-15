$(document).ready(function() {

	$('#myModal').modal('hide');
	$('.file_input_button').click(function() {
		$('.file_input_div >input:nth-child(2)').trigger('click');
	});

});
function fileSend(frm) {

	var file = frm.file.value;
	var fileExt = file.substring(file.lastIndexOf('.') + 1); // 파일의 확장자를
	// 구합니다.
	var bSubmitCheck = true;
	if (!file) {
		alert("파일을 선택하여 주세요!");
		return;
	}
	if (fileExt.toUpperCase() == "PDF") {
		$('#fileForm').ajaxForm({
			url : "/fileupload",
			enctype : "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는
			// 부분이며 multipart로 지정해주지 않으면
			// controller로 파일을 보낼 수 없음
			async : true,
			beforeSend : function() {
				$('#myModal').modal('show');
			},
			complete : function() {
				location.href = '/convert';
			}
		});
		// 여기까지는 ajax와 같다. 하지만 아래의 submit명령을 추가하지 않으면 백날 실행해봤자 액션이 실행되지 않는다.
		$('#fileForm').submit();
	} else {
		alert(".PDF파일만 업로드 가능합니다");
		frm.submit();
	}
}