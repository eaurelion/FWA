<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>View photo</title>
    <style>
        body {
            margin-left: 50px;
            margin-top: 50px;
            background: radial-gradient(farthest-corner at 50% 50%, #aaf0d1, #ffc0cb);
            font-family: "Courier",sans-serif;
            font-weight: bold;
        }
    </style>
</head>
<body>
<img src="data:image/png;base64,<%=request.getAttribute("image")%>" style="height: 30%; width: 30%;">
</body>
</html>
