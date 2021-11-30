<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<script src="//code.jquery.com/jquery-3.6.0.js"></script>
		<link
			rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"
		/>
		<link
			rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"
		/>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
		<title>Insert title here</title>
		<style>
			.hidden {
				display: none !important;
			}
			body {
				display: flex;
				flex-direction: column;
				align-items: center;
				background-color: #d1d9ff;
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
			.btnContainer {
				display: flex;
				justify-content: center;
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
			.inline {
				display: flex;
				margin-left: 10px;
			}
			.inline input {
				width: 40px !important;
				margin: 0px 10px;
			}
			input {
				width: 120px;
				border: none;
				text-align: right;
			}
			select {
				width: 120px;
			}
			label {
				width: 100px;
			}
			span {
				font-weight: bolder;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<form>
			<div class="btnContainer firstBtn">
				<button class="register">회원가입</button>
				<button class="login">로그인</button>
			</div>

			<div class="loginContainer">
				<div class="inputContainer">
					<label for="name">이름</label>
					<input
						type="text"
						name="name"
						placeholder="이름을 입력하세요"
						required
					/>
				</div>

				<input type="hidden" name="user_id" />

				<div class="inputContainer">
					<label for="password">비밀번호</label>
					<input
						type="password"
						name="password"
						placeholder="비밀번호를 입력하세요"
						required
					/>
				</div>
			</div>
			<div class="signUpbox hidden">
				<div class="inputContainer">
					<label for="">주소 </label>
					<select name="address">
						<option value="seoul">서울</option>
						<option value="daejun">대전</option>
						<option value="daegu">대구</option>
						<option value="busan">부산</option>
						<option value="gwangju">광주</option>
						<option value="jeju">제주</option>
					</select>
				</div>
				<div class="inputContainer">
					<label for="detail_addr">상세주소</label>
					<input
						type="text"
						name="detail_addr"
						placeholder="상세주소를 입력하세요"
						required
					/>
				</div>

				<div class="inputContainer">
					<label for="resident">주민등록번호</label>
					<div class="inline">
						<input type="text" name="first" required />
						<span>-</span>
						<input type="text" name="second" required />
					</div>
				</div>

				<div class="inputContainer">
					<label for="telephone">집전화번호</label>
					<div class="inline">
						<input type="text" name="phone_1" />
						<span>-</span>
						<input type="text" name="phone_2" />
						<span>-</span>
						<input type="text" name="phone_3" />
					</div>
				</div>
				<div class="inputContainer">
					<label for="mobile_phone">핸드폰 번호 </label>
					<div class="inline">
						<input type="text" name="mobile_1" required />
						<span>-</span>
						<input type="text" name="mobile_2" required />
						<span>-</span>
						<input type="text" name="mobile_3" required />
					</div>
				</div>
			</div>
			<div class="btnContainer">
				<button type="button" class="signUp hidden">회원등록</button>
				<button type="reset" class="reset hidden"> x </button>
			</div>
		</form>
		</div>
		<form class='form' type="get" action="/shop/product">
			<input type="hidden" name="id" />
		</form>
	</body>
	<script>
		$(document).ready(function (e) {
			var userInfo = {};
			$(".register").click(() => {
				$(".firstBtn").addClass("hidden");
				$(".signUpbox").removeClass("hidden");
				$(".signUp").removeClass("hidden");
			}); //register
			$(".signUp").click(function (e) {
				e.preventDefault();
				
				$(".firstBtn").removeClass("hidden");
				$(".signUpbox").addClass("hidden");
				$(".signUp").addClass("hidden");

				let phoneInput = $('input[name="phone_1"]').val();
				let telephone = phoneInput !== null && phoneInput.length > 1
						? [
								$('input[name="phone_1"]').val(),
								$('input[name="phone_2"]').val(),
								$('input[name="phone_3"]').val(),
						  ].join("-")
						: null;
				let mobileInput = $('input[name="mobile_1"]').val();
				let mobile_phone = mobileInput !== null && mobileInput.length > 1
						? [
								$('input[name="mobile_1"]').val(),
								$('input[name="mobile_2"]').val(),
								$('input[name="mobile_3"]').val(),
						  ].join("-")
						: null;
				let user_name = $('input[name="name"]').val();
				user_password = $('input[name="password"]').val();
				user_detail_addr = $('input[name="detail_addr"]').val();
				user_resident = [
					$('input[name="first"]').val(),
					$('input[name="second"]').val(),
				].join("-");

				userInfo = {
					user_name,
					user_password,
					user_address: $("select[name='address'] option:selected").val(),
					user_detail_addr,
					user_resident,
					telephone,
					mobile_phone,
				};
				console.table(userInfo);
				$.getJSON("/shop/getAll", function (list) {
					list.some(
						(i) =>
							i.user_name === userInfo.user_name &&
							i.user_password === userInfo.user_password
					)
						? alert("이미 등록된 정보입니다 다른 정보를 입력하세요 ")
						: singUp(
								userInfo,
								(result) => {
									alert("회원 가입 되었습니다 로그인 하세요");
									$('.reset').trigger('click')
								},
								(err) => alert(err)
						  );
				});
			}); //signuup click
			$(".showlogin").click(() => {
				$(".firstBtn").addClass("hidden");
			}); //showlogin
			$(".login").click(function (e) {
				e.preventDefault();
				userInfo = {
					user_name: $('input[name="name"]').val(),
					user_password: $('input[name="password"]').val(),
				};
				$.getJSON("/shop/getAll", function (list) {
					if (
						list.some(
							(i) =>
								i.user_name === userInfo.user_name &&
								i.user_password === userInfo.user_password
						)
					) {
						userInfo = list.find((i) => i.user_name == userInfo.user_name);
						$('input[name="id"]').val(userInfo.user_id);
						$(".form").submit();
					} else {
						alert("등록된 정보가 없습니다");
					}
				});
			});
			const singUp = (userInfo, callback, error) => {
				$.ajax({
					type: "post",
					url: "/shop/signUp",
					data: JSON.stringify(userInfo),
					contentType: "application/json; charset=utf-8",
					success: function (result, status, xhr) {
						if (callback) callback(result);
					},
					error: function (xhr, status, er) {
						if (error) console.log(error);
					},
				});
			}; //func signup
		}); // docu
	</script>
</html>
