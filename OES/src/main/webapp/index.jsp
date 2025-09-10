
<%
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setHeader("Expires", "0"); //prevents caching at the proxy server
%>
<!DOCTYPE html>
<!-- Maintained by SaiRam Muppalla — https://www.linkedin.com/in/sai-ram-muppalla-188a312b9/ -->
<html lang="en" dir="ltr">

<head>
<meta charset="utf-8">
<title>ExamQuiz · SaiRam Muppalla</title>
<link rel="stylesheet" href="css/User-Login-Register.css">
<link rel="stylesheet" href="css/nav.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="image/x-icon" href="img/logo2.png">
</head>

<body>
	<jsp:include page="includes/header.jspf" />
	<div class="new">
		<div class="wrapper1">
		<br>
		
			<img src="img/123.png" alt="User Login">
			<i>"Testing made easy, with our user-friendly online exam system."</i>
		</div>
		<div class="wrappe">
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<h1>"Experience a smarter way to exam,<br> with our online system."</h1>
			<!-- <a href="Student-Login.jsp?msg=1"><button class="stud-login-btn">Student Login</button></a>
			<a href="Student-Login.jsp?msg=1"><button class="stud-login-btn">Student Login</button></a>
			<br> <a href="User-Login.jsp?msg=1">1. New User</a> <br> <a
				href="Student-Login.jsp?msg=1">2, User Login</a>
 -->
		</div>
	</div>
	<%@ include file="includes/footer.jspf" %>
	<script>
	history.forward();
	window.onbeforeunload = function() {
		return "You work will be lost.";
	};
</script>
</body>

</html>