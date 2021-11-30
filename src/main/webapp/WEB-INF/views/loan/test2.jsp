<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<style>
 .getLoan{
 	display:none;
 }

</style>
<script src="//code.jquery.com/jquery-3.6.0.js"></script>
</head>
<body>
 
 
 
 
	${test[0].free_name} 님의 계좌<Br>
	
<ul class="list-group">
  <li class="list-group-item active" aria-current="true">An active item</li>
  <li class="list-group-item">A second item</li>
  <li class="list-group-item">A third item</li>
  <li class="list-group-item">A fourth item</li>
  <li class="list-group-item">And a fifth one</li>
</ul>
	<c:forEach items="${test}" var="i">
		 ${i.free_serial}의 잔액 <span class="balance" data-balance="${i.free_balance}" >${i.free_balance} </span> <br>
 	</c:forEach>
	<span class="isloan"> </span>
	
	<a class="getLoan" name="getLoan" href=""> 대출 하기 </a>
	<form class="getLoan" name="getLoan" action="" method="post">
		<label for="loan_fee"> 대출 금 (단위 10,000) </label>
		<input type="number" name="loan_fee">
		<input type="text" name="loan_name" value="${test[0].free_name}">
		<button type="button" id="borrow"> 대출 </button>
	</form>
	
	<table>
		<thead>
			<th> 대출 가능 총 금액// </th>
			<th> 대출 금// </th>
			<th> 대출 가능 잔액 //</th>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="i">
			<tr>
			<td> ${i.loan_total}//</td><td>${i.loan_fee}//</td><td>${i.loan_balance}</td> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
	<script>
	$(document).ready(function(e){
		console.log($('.balance'))
		balance = $('.balance')
		let sum =0
		balance.each((i, obj) => 
			sum += parseFloat($(obj).data("balance"))
		)//each
		let str = "총 잔액 " + sum*10000 +" 원,"
		str += sum>500? " 대출 가능 합니다 <p> 대출 가능  총 금액 " + sum * 10 +"만원 입니다  " : " 대출 불가능 합니다 " 
		if(sum>500) $('a[name=getLoan]').removeClass("getLoan")
		$('.isloan').append(str)
		
		$('a[name=getLoan]').click(function(e){
			e.preventDefault()
			console.log("clicked")
			$('form[name=getLoan]').removeClass("getLoan")
			var loan = {
				loan_name : "${test[0].free_name}",
				loan_total : sum*10
			}
			console.log(name)
			$.getJSON("/loan/getLoan", loan, ()=> console.log("success") ) 
		})//click
		$('#borrow').click(function(e){
			
			console.log('button borrow')
			var loanVo = {
				loan_name : $('input[name=loan_name]').val(),
				loan_fee  : $('input[name=loan_fee]').val()
			}
			borrow(loanVo, ( list ) => { 
				console.log('===== success  대출 ')
				list.map( i => console.log(i))
				
			}, (error) => console.log(error))//
			
		})//borrow click
		
		const borrow = ( loanVo, callback, error) =>{
			$.ajax({
				type:'post',
				url : '/register/borrow',
				data : JSON.stringify(loanVo),
				contentType : "application/json; charset=utf-8",
				success: function(result, status, xhr){
					if(callback) callback(result)
				},
				error : function(xhr, status, er){
					if(error) console.log(error)
				}//error func
			})//ajax
			
	
		}//borrow
		
		
		
	})//docu
	
		
	</script>

</html>