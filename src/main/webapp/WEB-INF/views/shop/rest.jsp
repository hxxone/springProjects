<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-3.6.0.js"></script>
</head>
<body>	



<form >
	<input name="product_name">
	<input name="product_price">
	<button> x </button>
	<button> se </button>
	<button> json </button>
</form>

<div>

</div>
</body>
<script>

	$(document).ready(function(e){
		$('button').eq(0).click((e) => {
			e.preventDefault()
			let product = {
				product_name: "yeah",
					product_price : 1000}
			e.preventDefault();
			$.ajax({
				url : "/shop/",
				type:"POST",
				data : product,
				success : (data)=>$('div').text(data),
				error : () => console.log("error")
			})
			
		})
		$('button').eq(1).click((e)=>{
			console.log( $('form').serialize())
			e.preventDefault();
			$.ajax({
				url : "/shop/",
				type:"POST",
				data : $('form').serialize(),
				success : (data)=>$('div').text(data),
				error : () => console.log("error")
			})
		})
		$('button').eq(2).click((e)=>{
			e.preventDefault();
			let data = $('form').serialize()
			$.ajax({
				url : ("/shop/json"),
				type : 'post',
				data : JSON.stringify(data),
				contentType : "application/json; charset=ustf-8;",
				dataType : "json",
				success : (result) => console.log(result),
				error : (a, b, c) => console.log("a" + a+ ", b" + b+", c " +c)
			})
		})
		
		
	})
	
</script>
</html>