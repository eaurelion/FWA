<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");%>

<html>
<head>
    <title>Authorization</title>
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
<h1>Authorization</h1>
<form action="${pageContext.request.contextPath}/signIn" method="post">
    <div>
    <label for="login">Login</label>
    <input type="text" id="login" name="login" placeholder="Login">
    <label for="password">Password</label>
    <input type="password" id="password" name="password" placeholder="Password">
    <button type="submit">Submit</button>
    </div>
</form>
<a href="${pageContext.request.contextPath}/">To main</a>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
