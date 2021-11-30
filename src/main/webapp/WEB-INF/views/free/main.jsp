<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<!DOCTYPE html>
<html>
  
<head>
<style>

body{
    background-color: #f6f9fc;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; 
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 50px 0;
}
.form-select{
	margin : 10px
}

.hidden{
	display :none
}

.table {
    width:70%; 
    margin-left:15%; 
    margin-right:15%;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-3.6.0.js"></script>
</head>


<body>
<%-- 	<ul>
	<c:forEach items="${list}" var="list">
		<li> <a href="account?name=${list.free_name}"> ${list.free_name}</a></li>
	
	</ul> --%>
	
	<div class="greeting">
	<h5> 안녕하세요 <b>${name}</b> 님! </h5> 
	<br>
	<form action="/loan/test">
		<input type="hidden" name="name" value="${name}">
		<button  class="btn btn-outline-primary" type='submit'> 대출심사 </button>
		
		<button class="btn btn-outline-primary getNew"> 새로운 계좌 생성 </button>
	</form>
	
	
	<select class="form-select" aria-label="Default select example">
 		<option selected>${name} 님의 계좌 를 선택하세요</option>
 		<c:forEach items="${serial}" var="serial">
  		<option value="${serial.free_serial}" class="serial" >${serial.free_serial} 총액 ${serial.free_balance} 원 
  		</option>
  		</c:forEach>
	</select>
	</div>
	<button class="btn btn-outline-primary register" >거래등록</button>
	
	
	
	<table class="table hidden" >
  	<thead>
	    <tr>
	      <th scope="col">#</th>
	      <th scope="col">잔액</th>
	      <th scope="col">입금</th>
	      <th scope="col">출금</th>
	    </tr>
  	</thead>
  <tbody>
   
  </tbody>
</table>



	
	
<form method="post" action="/free/transaction" class="test hidden">
<div class="form-floating mb-3">
	
	<input type="text" name="free_name" class="form-control" id="floatingInput" value='${name}' readonly>
	<label for="free_name"> 이름 </label>
</div>
<div class="form-floating mb-3">
<input type="hidden" name="free_password" value="">	
</div>
<div class="form-floating mb-3">
	
	<input type="text"  name="free_serial"  value=0 class="form-control" id="floatingInput"  readonly>
	<label for="free_serial"> 계좌번호 </label>
</div>
<div class="form-floating mb-3">
	
	<input type="text"  name="free_deposit" value=0 class="form-control" id="floatingInput" >
	<label for="free_deposit"> 입금 </label>
</div>
<div class="form-floating mb-3">
	
	<input type="text" name="free_withdrawal" value=0 class="form-control" id="floatingInput">
	<label for="free_withdrawal"> 출금 </label>
</div>



<div>
  <button type="button" class="btn btn-outline-primary submit"> 등록 </button>
  
  </div>
</form> 
	

	

	
	<script>
	
	$(document).ready(function(e){
		var btn = $('.register')
		console.log("${serial}" )
		$('.form-select').change(function(){
			let free_serial =$(this).val()
			$('.table').removeClass('hidden');
			$('.register').data("serial", free_serial)

			console.log(free_serial)
			$.getJSON("/free/getAccountBySerial", {free_serial}, function(list){
					console.log("=================", list)
					var statement = list.reduce( (acc, i, index) => 
						acc += "<tr><th scope='row'>"+(index+1)+"</th><td>" 
						+ i.free_balance + "</td>" + "<td>" + i.free_deposit + "</td>" 
						+ "<td>" + i.free_withdrawal + "</td>" + "</tr>"
					, "")//reduce
					
					console.log(statement)
					$('tbody').html(statement)
				})//getJSON func
		})
		

		$('.register').click(function(e){
			$('.table').addClass('hidden');
			$('.test').removeClass('hidden');
			
			let free_serial =$(this).data("serial")
			console.log(" ============ 데이터 속성 잘 바뀌니")
			console.log($(this).data("serial"))
			
			var deposit = $('input[name=free_deposit]')
			var withdrawal = $('input[name=free_withdrawal]')
			console.log(" button clicked ")
			
			$('input[name=free_serial]').val(free_serial)
			$('input[name=free_password]').val("${serial[0].free_password}")
			deposit.click(() => deposit.val(""))
			withdrawal.click(()=> withdrawal.val(""))
		})// btn click 
		
		$('.submit').click(function(e){
			if(!$('input[name=free_deposit]').val()) $('input[name=free_deposit]').val(0)
			if(!$('input[name=free_withdrawal]').val()) $('input[name=free_withdrawal]').val(0)
			console.log('button submit ')
			var transaction ={
				free_name : $('input[name=free_name]').val(),
				free_password : $('input[name=free_password]').val(),
				free_serial : $('input[name=free_serial]').val(),
				free_deposit : $('input[name=free_deposit]').val(),
				free_withdrawal : $('input[name=free_withdrawal]').val()
			}
			
			add(transaction, () => { 
				$('.test').addClass('hidden')
				location.reload()
				
			}, (err) => alert(err))
		})// submit
		
		const add =( transaction, callback, error) => {
			$.ajax({
				type:'post',
				url : '/register/transaction',
				data : JSON.stringify(transaction),
				contentType : "application/json; charset=utf-8",
				success: function(result, status, xhr){
					if(callback) callback(result)
				},
				error : function(xhr, status, er){
					if(error) error(er)
				}
			})
			
		}//add
		
		$('.loanTest').click(function(e){
			
			$(location).attr('href','/loan/test');
		})
		
		
		$('.getNew').click(function(e){
			console.log("button clicked")

			var signUpInfo ={
				free_name : $('input[name=free_name]').val(),
				free_password :"${serial[0].free_password}",
			}
			
			newAcc(signUpInfo, (result) =>{
				
				alert('계좌 등록')
				location.reload()
				}, (err) => alert(err))
		})// submit
		
		const newAcc =( signUpInfo, callback, error) => {
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
		}//NewAcc

	})// docu
	
	</script>

</body>
</html>