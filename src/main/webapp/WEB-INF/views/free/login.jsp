<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-3.6.0.js"></script>
<style>
	.hidden{
		display: none; 
	}
	
	body{
    background-color: #f6f9fc;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; 
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 50px 0;
}
	button{
		margin: 10px;
	}
	
</style>
</head>

<body>
<button id="signUpBtn" class="btn btn-outline-primary"> 회원가입 </button>



<form action="/free/login" method="post">
<div class="form-floating mb-3">
  <input type="text" name="free_name"  class="form-control" id="floatingInput" placeholder="name">
  <label for="free_name">이름</label>
</div>
<div class="form-floating">
  <input type="password" name="free_password" class="form-control" id="floatingPassword" placeholder="Password">
  <label for="free_password">비밀번호</label>
  <button type="submit" class="btn btn-outline-primary">log in</button>
 </div>
</form> 

	
	
	
	
	<div class="hidden" id="test">
	<form method="post" action="/free/transaction">
	<div class="form-floating mb-3">
  		<input type="text" name="free_name" class="form-control" id="signUpName" placeholder="name">
  		<label for="free_name">이름</label>
	</div>
	<div class="form-floating">
 		<input type="password" name="free_password" class="form-control" id="signUpPsw" placeholder="Password">
  		<label for="free_password">비밀번호</label>
  	<button type="button" id="submit" class="btn btn-outline-primary">회원등록</button>
  
  	</div>
	</form> 
	</div>
	
		
	${error}
	<!-- 
	<div class="hidden">
		<label for="free_serial"> 등록을 원하는 계좌 번호를 입력하세요 </label>
		<input type="text" name="free_serial" >
		<button class="serial" type="submit"> 등록 </button>
		
	</div> -->
	
	<script>
	
	
	
	$(document).ready(function(e){
		
		$('#signUpBtn').click(function(e){
			$('#test').removeClass("hidden")
			$('#signUpName').val("")
			$('#signUpName').val("")
			console.log("button clicked")
			$('#submit').click(function(e){
			
			console.log('button submit ')
			var signUpInfo ={
								free_name : $('#signUpName').val(),
								free_password :$('#signUpName').val()
							}
			$.getJSON("/free/getInfo", function(list){
				list.some(i => i.free_name === signUpInfo.free_name)? 
						alert("이미 등록된 정보입니다 다른 정보를 입력하세요 ")
						: singUp(signUpInfo, (result) =>{
							alert('회원 가입 되었습니다 로그인 하세요')
						}, (err) => alert(err));
						$('#test').addClass("hidden")
						
			})

		})// submit
		
		const singUp =( signUpInfo, callback, error) => {
			$.ajax({
				type:'post',
				url : '/register/sign_up',
				data : JSON.stringify(signUpInfo),
				contentType : "application/json; charset=utf-8",
				success: function(result, status, xhr){
					if(callback) callback(result)
				},
				error : function(xhr, status, er){
					if(error) console.log(error)
				}
			})
			
		}//add
		})	
			
		})// DOCU 
/* 		 $(document).click(function(e){
			 var input = "<input type='text' name='free_serial'>"
			 
			 $('button').click(function(e){
				$('.hidden').attr("class", "show")
				
			 })
			 $('.serial').click(function(e){
				$.Ajax()
			 })
			 
		 })
 */		
	</script>
</body>
</html>