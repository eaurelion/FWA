<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="edu.school21.cinema.models.AuthHistory" %>
<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
    <title>User profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style>
        body {
            margin-top: 25px;
            background: radial-gradient(farthest-corner at 50% 50%, #aaf0d1, #ffc0cb);
            font-family: "Courier",sans-serif;
        }
        th {
            font-size: 0.8em;
            padding: 5px 7px;
            border: 1px solid #478a84;
            text-align: center;
        }
        td {
            font-size: 0.8em;
            padding: 5px 7px;
            border: 1px solid #478a84;
            text-align: center;
        }
        table {
            border-collapse: collapse;
            line-height: 1.1;
            background:  radial-gradient(farthest-corner at 50% 50%, white, #9C9C9C);
            color: #478a84;
        }
        .form-test {
            font: 14px Arial;
            alignment: right;
        }
        .form-test input {
            font: 14px Arial;
            border: none;
            padding: 0;
            background: #d2defc;
            color: #00f;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-8">
            <h1 style="text-align: center">User profile</h1>
        </div>
        <div class="col-sm-4">
            <form class="form-test" action="${pageContext.request.contextPath}/logout" method="post">
                <h6 style="text-align: right"><button type="submit" style="width: 25%"> Log out</button></h6>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4">
            <c:if test="${requestScope.image != null}">
                <div style="text-align: center;">
                  <img src="data:image/png;base64,<%=request.getAttribute("image")%>" style="height: 400px; width: 100%;">
                </div>
            </c:if>
            <c:if test="${requestScope.image == null}">
            <div style="text-align: center;">
                <img src="${pageContext.request.contextPath}/img/avatar.jpeg" style="height: 400px; width: 100%;">
            </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/images" enctype="multipart/form-data" method="post">
                <input type="file" id="image" name="image" accept="image/*">
                <p></p>
                <button type="submit" style="width: 100%">Upload a photo</button>
            </form>

        </div>
        <div class="col-sm-8">
            <% User user = (User) request.getSession().getAttribute("user");%>
            <table class="table table-bordered">
                <tr>
                    <td>Login</td>
                    <td><%=user.getLogin()%></td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><%=user.getFirstName()%></td>
                </tr>
                <tr>
                    <td>Lastname</td>
                    <td><%=user.getLastName()%></td>
                </tr>
                <tr>
                    <td>Phone number</td>
                    <td><%=user.getPhoneNumber()%></td>
                </tr>
            </table>
            <form action="${pageContext.request.contextPath}/profile" method="post">
                <div class="col">
                    <label for="firstname">Name</label>
                    <br>
                    <input type="text" id="firstname" name="firstname" placeholder="Name" style="width: 100%">
                </div>
                <div class="col">
                    <label for="lastname">Lastname</label>
                    <br>
                    <input type="text" id="lastname" name="lastname" placeholder="Lastname" style="width: 100%">
                </div>
                <div class="col">
                    <label for="phone">Phone number</label>
                    <br>
                    <input type="text" id="phone" name="phone" placeholder="Phone number" style="width: 100%">
                </div>
                <div>
                    <p></p>
                    <button type="submit" style="width: 100%">Update info</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <%ArrayList<AuthHistory> authHistories = (ArrayList<AuthHistory>)request.getSession().getAttribute("auth");
            request.setAttribute("auths", authHistories);
            request.setAttribute("files", new File((String) request.getAttribute("uploadPath")).listFiles());
        %>
        <div class="col-sm-4">
            <h1>Login history</h1>
            <table style="width: 100%">
                <th>Operation</th>
                <th>Time</th>
                <th>IP address</th>
                <c:forEach items="${requestScope.auths}" var="auth">
                    <tbody>
                    <td>
                        <c:if test="${auth.type == 'sign_up'}">Registration</c:if>
                        <c:if test="${auth.type == 'sign_in'}">Authorization</c:if>
                    </td>
                    <td>${auth.time}</td>
                    <td>
                        <c:if test="${auth.address == '0:0:0:0:0:0:0:1'}">127.0.0.1</c:if>
                        <c:if test="${auth.address != '0:0:0:0:0:0:0:1'}">${auth.address}</c:if>
                    </td>
                    </tbody>
                </c:forEach>
            </table>
        </div>
        <div class="col-sm-8">
            <h1>Download history</h1>
            <table style="width: 100%">
                <th>File name</th>
                <th>Size</th>
                <th>MIME</th>
                <c:forEach items="${requestScope.files}" var="file">
                    <tbody>
                    <td>
                            <a href="images/${file.getName()}" target="_blank">${file.getName()}</a>
                    </td>
                    <td>
                            ${file.length()} kb
                    </td>
                    <td>
                        image/jpg
                    </td>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
