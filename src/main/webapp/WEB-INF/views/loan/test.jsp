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

ul{
	width : 35%;
	
}
.hidden{
	display: none;
}
button{
margin : 10px;
}
#borrow{
	position : absolute;
	left : 220px;
	top : -5px;
	width : 70px;
	height : 50px;
}
.name{
text-align: left;
}
.balance{
text-align: right;
}

.table {
    width:60%; 
    margin-left:15%; 
    margin-right:15%;
    text-align: center;

}
</style>
<script src="//code.jquery.com/jquery-3.6.0.js"></script>
</head>
<body>
 
 
 
 
	<h3>${test[0].free_name} 님의 계좌 (단위 10,000)</h3><Br> 
	<h5><span class="isloan">  </span> </h5>
	<a class="hidden" name="getLoan" href=""> 대출 하기 </a>
	<ul class="list-group-item">
	
	<c:forEach items="${test}" var="i">
  	<li class="list-group-item">
  	 <span class=name> 계좌 <i>  ${i.free_serial}</i> </span> 
  	 <div class="balance" data-balance="${i.free_balance}" > <b> ${i.free_balance} </b> </div>
  	</li>
  </c:forEach>	
</ul>



	
	
	<form class="hidden" name="getLoan" action="" method="post">
	<div class="form-floating mb-3">
	
	<input type="hidden" name="loan_name" value="${test[0].free_name}">
	</div>
	<div class="form-floating mb-3">
	
	<input type="number" name="loan_fee" class="form-control" id="floatingInput" >
	
	<label for="loan_fee"> 대출 금 (단위 10,000) </label>
	<button type="button" id="borrow" class="btn btn-outline-primary"> 대출 </button>
	
</div>



  

 
</form>
	
	<table class="table hidden loanTbl">
  <thead>
    <tr>
      <th scope="col">대출 가능 총 금액</th>
      <th scope="col">대출 금</th>
      <th scope="col">대출 가능 잔액</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${list}" var="i">
    <tr>
      <td> ${i.loan_total}</td>
      <td>${i.loan_fee}</td>
      <td>${i.loan_balance}</td>
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
		str += sum>500? " 대출 가능 합니다 <p> 대출 가능  총 금액 " + (sum*10)  +"만원 입니다  " : " 대출 불가능 합니다 " 
		if(sum>500) $('a[name=getLoan]').removeClass("hidden")
		$('.isloan').append(str)
		
		
		$('a[name=getLoan]').click(function(e){
			$('.list-group-item').addClass('hidden')
			$('.loanTbl').removeClass("hidden")
			e.preventDefault()
			console.log("clicked")
			$('form[name=getLoan]').removeClass("hidden")
			var loan = {
				loan_name : "${test[0].free_name}",
				loan_total : sum *10
			}
			console.log(name)
			$.getJSON("/loan/getLoan", loan, ()=> console.log("success") ) 
		})//click
		
		$('#borrow').click(function(e){
			$('form[name=getLoan]').addClass("hidden")
			// $('.loanTbl').addClass("hidden")
			
			console.log('button borrow')
			var loanVo = {
				loan_name : $('input[name=loan_name]').val(),
				loan_fee  : $('input[name=loan_fee]').val()
			}
			borrow(loanVo, ( list ) => { 
				location.reload()
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