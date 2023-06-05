<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style>
        body {
            margin-left: 50px;
            margin-top: 50px;
            background: radial-gradient(farthest-corner at 50% 50%, #aaf0d1, #ffc0cb);
            font-family: "Courier",sans-serif;
            font-weight: bold;
        }
        button {
            margin-left: 50px;
        }
    </style>
</head>
<body>
<h1>Registration</h1>
<form action="${pageContext.request.contextPath}/signUp" method="post">
    <label for="login">Login</label>
    <input type="text" id="login" name="login" placeholder="Login">
    <label for="password">Password</label>
    <input type="password" id="password" name="password" placeholder="Password">
    <button type="submit">Register</button>
</form>
<a href="${pageContext.request.contextPath}/">To main</a>
</body>
</html>
