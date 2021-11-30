<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />

		<title>Insert title here</title>
		<script src="//code.jquery.com/jquery-3.6.0.js"></script>
		<style>
			.hidden {
				display: none;
			}
			body {
				display: flex;
				flex-direction: column;
				align-items: center;
				background-color: #d1d9ff;
			}
			ul {
				list-style: none;
			}
			.liBox {
				background-color: white !important;
				
				display: flex;
				flex-direction: column;
				border-bottom : 1px solid #d0d8ff !important;
			}
			.inline {
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
			.circle {
				display: inline-block;
				border-radius: 50%;
				height: 25px;
				width: 25px;
				background: #6f79a8;
				color: white;
				text-align: center;
				font-weight: 900;
			}
			.inline2 {
				display: flex;
				justify-content: end;
			}
			.container {
				width: 400px;
				background-color: white;
				margin-top: 50px;
				padding: 20px;
				display: flex;
				flex-direction: column;
				justify-content: space-around;
			}
			button {
				border: none;
				width: 80px;
				height: 40px;
				margin: 10px;
				background-color: #6f79a8;
				color: white;
				font-weight: 600;
				font-size: 15px;
			}
			.inputContainer {
				display: flex;
				justify-content: space-between;
				margin: 10px;
				height: 40px;
				align-items: center;
				padding: 0 10px;
				border-bottom: 1px solid #6f79a8;
			}
			.btnContainer {
				display: flex;
				justify-content: center;
			}
			input {
				width: 120px;
				border: none;
				text-align: right;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<div>
				<ul class="cartlist"></ul>
			</div>
			<div class="prod">
				<input type="hidden" name="id" value="${user.user_id}" readonly />

				<div class="inputContainer">
					<label for="name">이름</label>
					<input type="text" name="name" value="${user.user_name}" readonly />
				</div>

				<input type="hidden" name="password" value="${user.user_password}" />

				<div class="inputContainer">
					<label for="name">주소</label>
					<input
						type="text"
						name="address"
						value="${user.user_address}"
						readonly
					/>
				</div>
				<div class="inputContainer">
					<label for="detail_addr">상세주소</label>
					<input
						type="text"
						name="detail_addr"
						value="${user.user_detail_addr}"
						readonly
					/>
				</div>
				<input type="hidden" name="resident" value="${user.user_resident}" />

				<div class="inputContainer">
					<label for="mobile_phone">핸드폰 번호 </label>
					<input
						type="text"
						name="mobile"
						value="${user.mobile_phone}"
						readonly
					/>
				</div>
				<form>
					<div class="inputContainer">
						<label for=""> 상품 </label>
						<select name="product">
							<option>=======</option>
							<c:forEach items="${item}" var="item">
								<option value="${item.product_name}">${item.product_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="inputContainer">
						<label for="price">가격</label>
						<input type="text" name="price" readonly />
					</div>
					<div class="inputContainer">
						<label for="quantity">수량 </label>
						<input type="text" name="quantity" />
					</div>
					<div class="inputContainer">
						<label for="total"> 총금액 </label>
						<input type="text" name="total" readonly />
					</div>
					<div class="inputContainer">
						<label for=""> </label>

						<select name="coupon">
							<option>쿠폰을 선택하세요</option>
							<option value="0.1">10% 쿠폰</option>
							<option value="0.15">15% 쿠폰</option>
							<option value="0.2">20% 쿠폰</option>
						</select>
					</div>
					<div class="inputContainer">
						<label for="coupon_use">쿠폰 사용 </label>
						<input type="text" name="coupon_use" value="N" />
					</div>
					<div class="btnContainer">
						<button class="purchace">구입</button>
						<button class="cart">장바구니</button>
						<button class="test"> 직렬화라는걸 해봐자 </button>
						<button type="reset" class="reset hidden">x</button>
					</div>
				</form>
			</div>
		</div>

		<script>
			$(document).ready(() => {
				$('select[name="product"]').change(function () {
					console.log($(this).val());
					var product = $(this).val();

					$.getJSON("/shop/getProduct", function (list) {
						var price = list.find(
							(item) => item.product_name === product
						).product_price;
						console.log(price);
						$('input[name="price"]').val(price);
					});
				}); //select change
				$('input[name="quantity"]').keyup(function (e) {
					var quantity = Number($(this).val());
					var price = Number($('input[name="price"]').val());
					$('input[name="total"]').val(quantity * price);
				});

				$('select[name="coupon"]').change(function () {
					$('input[name="coupon_use"]').val("Y");
					var saleRate = Number($(this).val());
					var beforPrice =
						Number($('input[name="quantity"]').val()) *
						Number($('input[name="price"]').val());
					var salePrice = beforPrice * (1 - saleRate);
					$('input[name="total"]').val(salePrice);
				});
				$('.test').click(function(e){
					e.preventDefault()
					console.log($('form').serialize())
					
				})
				$(".purchace").click(function (e) {
					e.preventDefault();
					let invoice = {
						invoice_id: $('input[name="id"]').val(),
						invoice_name: $('input[name="name"]').val(),
						invoice_password: $('input[name="password"]').val(),
						invoice_address: $('input[name="address"]').val(),
						invoice_detail_addr: $('input[name="detail_addr"]').val(),
						invoice_mobile_phone: $('input[name="mobile"]').val(),
						product: $("select[name='product'] option:selected").text(),
						price: $('input[name="price"]').val(),
						quantity: $('input[name="quantity"]').val(),
						total: $('input[name="total"]').val(),
						coupon:
							$("select[name='coupon'] option:selected").val() ===
							"쿠폰을 선택하세요"
								? null
								: $("select[name='coupon'] option:selected").val(),
						coupon_use: $('input[name="coupon_use"]').val(),
					}; //invoice
					console.log(invoice);
					$(".reset").trigger("click");
					purchace(
						invoice,
						(result) => alert(`구입 ${result}`),
						(err) => {
							alert(" 구입 실패 ");
							console.log(err);
						}
					);
				}); //purchase click

				$(".cart").click(function (e) {
					e.preventDefault();
					$(".prod").addClass("hidden");
					let id = $('input[name="id"]').val();
					$.getJSON("/shop/getCart", { id }, function (list) {
						console.table(list);
						showCart(list);
						$("button").click((e) => {
							var invoice = $(e.target).data("id");
							console.log(invoice);
							remove( invoice,
								(result)=>{
									alert(" 삭제 되었습니다 ")
									let newList = list.filter( i => i.invoice_number !== invoice)
									showCart(newList) 
								},
								(err) => console.log(err)
							);
							
							
						}); //button
					}); //getJson
				}); //cart

				const purchace = (invoice, callback, error) => {
					$.ajax({
						type: "post",
						url: "/shop/purchace",
						data: JSON.stringify(invoice),
						contentType: "application/json; charset=utf-8",
						success: function (result, status, xhr) {
							if (callback) callback(result);
						},
						error: function (xhr, status, er) {
							if (error) callback(status);
						},
					});
				}; //func signup

				const showCart = (list) => {
					console.log(list);
					str = "";
					list.map((item) => {
						str +="	<li><div class='liBox'><div class='inline'><h3>"+item.product+"</h3> <span>"
						str += Number(item.price)*1000 +"원 <span class='circle'>" + item.quantity + "</span> </span></div>" 
						str += '<div class="inline2"> <h4>' + Number(item.total) *1000 +' 원</h4>' 
						str += "<button class='remove' data-id='" + item.invoice_number+ "'>삭제</button></div>"
					});
			
					$(".cartlist").html(str);
				};

				const remove = (invoice, callback, error) => {
					$.ajax({
						type: "delete",
						url: "/shop/remove/" + invoice,
						success: (result) => {
							if (callback) callback(result);
						},
						error: (xhr, status, er) => {
							if (error) error(er);
						},
					}); //ajax ends
				}; //remove end
			}); //docu
		</script>
	</body>
</html>
