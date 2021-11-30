<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../includes/header.jsp" %>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">게시글 등록</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				게시판 등록 페이지

				<!-- /.panel-heading -->
				<div class="panel-body">
					<form action="/board/register" method="post" role="form">
						<div class="form-group">
							<label for="title"> 제목 </label>
							<input type="text" class="form-control" name="title" />
						</div>
						<div class="form-group">
							<label for="content"> 내용 </label>
							<textarea
								name="content"
								id="content"
								rows="3"
								class="form-control"
							></textarea>
						</div>
						<div class="form-group">
							<label for="writer"> 저자 </label>
							<input type="text" class="form-control" name="writer" />
						</div>
						<button class="btn btn-default" type="submit">등록</button>
						<button class="btn btn-default" type="reset">초기화</button>
					</form>
				</div>
				<!--  .pannel body/ -->
			</div>
			<!--  .pannel/ -->
		</div>
		<!--  .row/ -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">파일 첨부</div>
					<div class="panel-body">
						<div class="form-group uploadDiv">
							<input type="file" name="uploadFile" multiple />
						</div>
						<div class="uploadResult">
							<ul></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>
			var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$"); //정규 표현식
			var maxSize = 5242880; // 5MB 제한
			function checkExtension(fileName, fileSize) {
				//확장자 체크함수 정의
				if (fileSize >= maxSize) {
					alert("파일 사이즈 초과");
					return false;
				}
				if (regex.test(fileName)) {
					alert("해당 종류의 파일은 업로드 할 수 없습니다. ");
					return false;
				}
				return true;
			}
			$(document).ready(function (e) {
				var formObj = $("form[role='form']");
				$("button[type='submit']").click((e) => {
					e.preventDefault();
					console.log("전송버튼 눌림");
				});
				$("input[type='file']").change(function (e) {
					var formData = new FormData();
					var inputFile = $("input[name='uploadFile']");
					var files = inputFile[0].files;
					console.log(files);
					//formData에 파일 목록 추가
					for (let i = 0; i < files.length; i++) {
						if (!checkExtension(files[i].name, files[i].size)) return false;
						formData.append("uploadFile", files[i]);
					}
						$.ajax({
							url: "/board/uploadAjaxAction",
							processData: false,
							contentType: false,
							data: formData,
							type: "POST",
							dataType: "json",
							success: function (result) {
								// 컨트롤러의 반환값이 매개변수로 담김
								
								console.log(result);
								showUploadResult(result);
							},
						});
					
				});

				const showUploadResult = (uploadResultArr) => {
					console.log("show upload result 함 수 호 출 " + uploadResultArr);
					if (!uploadResultArr || uploadResultArr.length == 0) return;
					var uploadUL = $(".uploadResult ul");
					var str = "";
					$(uploadResultArr).each(function (i, obj) {
						if (obj.image) {
							var fileCallPath = encodeURIComponent(
								obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName
							);
							str += "<li><div>";
							str += "<span>" + obj.fileName + "</span>";
							str +=
								"<button type='button' class='btn btn-warning btn-circle'>" +
								"<i class='fa fa-times'></i></button><br>";
							str +=
								"<img src='/board/display?fileName=" + fileCallPath + "'></div></li>";
							console.log(str)
						} else {
							var fileCallPath = encodeURIComponent(
								obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName
							);
							var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
							str += "<li ";
							str += "data-path='"+obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-fileName='" + obj.fileName 
							str += "' data-type='" + obj.image + "'><div>"
							str += "<span>" + obj.fileName + "</span>";
							str +=
								"<button type='button' class='btn btn-warning btn-circle'>" +
								"<i class='fa fa-times'></i></button><br>";
							str += "<img src='/resources/img/attach.jpg'> </a></div></li>";
							console.log(str)
						}
					});
					uploadUL.append(str);
				};
				 $(".uploadResult").on("click", "button", function (e) {
		              console.log("delete file");
		              var targetFile = $(this).data("file");
		              var type = $(this).data("type");

		              var targetLi = $(this).closest("li");
		              $.ajax({
							url:'/board/deleteFile',
							data:{fileName: targetFile,type:type},
							dataType:'text',
							type: 'POST',
							success: (result)=>{
								alert(result);
								targetLi.remove();
							}
						})//$.ajax

		            })
			});//document.ready
		</script>
		<%@ include file="../includes/footer.jsp" %>
	</div>
</div>
