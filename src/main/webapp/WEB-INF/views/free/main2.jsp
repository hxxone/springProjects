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
	안녕하세요 ${name} 님! 
	
	</div>
	
	<select class="form-select" aria-label="Default select example">
 		<option selected>Open this select menu</option>
 		<c:forEach items="${serial}" var="serial">
  		<option value="${serial.free_serial}">${serial.free_serial} 총액 ${serial.free_balance} 원 </option>
  		</c:forEach>
	</select>
	
	
	
		
	<c:forEach items="${serial}" var="serial">
		<div class="serial" data-serial="${serial.free_serial}">
		 ${serial.free_serial} 총액 ${serial.free_balance} 원
		</div>
		<button data-serial="${serial.free_serial}"class="register"> 거래등록 </button>
	</c:forEach>
	
	
	<table>
		<thead>
			<th> #</th>
			<th> 잔액 </th> 
			<th> 입금  </th>
			<th> 출금 </th>
			<th> 대출가능 </th>
 		</thead>
		<tbody>
			
		</tbody>
	</table>
	</div>
	
	<form method="post" action="/free/transaction" class="test">
		<div>
			<label for="free_name"> 이름 </label>
			<input type="text" name="free_name" value='${name}' readonly>
			
		</div>
		<div>
			<label for="free_password"> 비밀번호 </label>
			<input type="password" name="free_password" value="">
			
		</div>
		<div>
			<label for="free_serial"> 계좌번호 </label>
			<input type="text" name="free_serial" readonly>
		</div>
		<div>
			<label for="free_deposit"> 입금 </label>
			<input type="text" name="free_deposit" value=0>
		</div>
		<div>
			<label for="free_withdrawal"> 출금 </label>
			<input type="text" name="free_withdrawal" value=0>
		</div>
		
		<button type="button" class="submit"> 거래 등록 </button>
		</form>
	
	
	
	<form action="/loan/test">
		<input type="hidden" name="name" value="${name}">
		<button type='submit'> 대출심사 </button>
	</form>
	<button class="getNew"> 새로운 계좌 생성 </button>

	
	<script>
	
	$(document).ready(function(e){
		
		console.log("${serial}" )
		$('.list-group-item').each((i, account)=>{
			console.log($(account).data("serial"))
			$(account).click(function(){
				let free_serial =$(account).data("serial")
				$.getJSON("/free/getAccountBySerial", {free_serial}, function(list){
					console.log("=================", list)
					var statement = list.reduce( (acc, i) => 
						acc += "<tr><td>"+ i.free_no+"<td><td>" + i.free_balance + "</td>" + "<td>" + i.free_deposit + "</td>" 
						+ "<td>" + i.free_withdrawal + "</td>" + "<td>" + i.loan + "</td></tr>"
					, "")//reduce
					
					console.log(statement)
					$('tbody').html(statement)
				})//getJSON func
			})//click
		})//each
		
		
		
		
		
		
		$('.register').click(function(e){
			let free_serial =$(this).data("serial")
			
			var deposit = $('input[name=free_deposit]')
			var withdrawal = $('input[name=free_withdrawal]')
			console.log(" button clicked ")
			
			$('input[name=free_serial]').val(free_serial)
			$('input[name=free_password]').val("${serial[0].free_password}")
			deposit.click(() => deposit.val(""))
			withdrawal.click(()=> withdrawal.val(""))
		})// btn click
		
		$('.submit').click(function(e){
			
			console.log('button submit ')
			var transaction ={
				free_name : $('input[name=free_name]').val(),
				free_password : $('input[name=free_password]').val(),
				free_serial : $('input[name=free_serial]').val(),
				free_deposit : $('input[name=free_deposit]').val(),
				free_withdrawal : $('input[name=free_withdrawal]').val()
			}
			
			add(transaction, (result) => location.reload(), (err) => alert(err))
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