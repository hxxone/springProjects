<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
     <script src="/resources/dist/js/sb-admin-2.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel-pannel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">로그인 페이지 </h3>
					</div>
					<div class="panel-body">
						<form role="form" method="post" action="/login">
							<fieldset>
								<div class="form-group">
									<input type="text" id="username" name="username" value="admin90">
								</div>
								<div class="form-group">
									<input type="password" id="password" name="password" value="pw90">
								</div>
								<div class="checkbox">
									
									<label> 
										<input type="checkbox" name="remember-me">
										로그인 상태 유지 
									</label>
					
								</div>
								<button type="submit" class="btn btn-lg btn-success btn-block">
									로그인
								</a>
							</fieldset>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		
			$(".btn_success").click(function(e){
				e.preventDefault();
				$("form").submit();
			})
		
			

	</script>
</body>
</html>