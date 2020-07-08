<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 30.06.2020
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="styles/w3.css">
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<div align="center">
    <h1>User Reg Form</h1>
    <form action="reg" method="post">
        <table style="with: 100%">
            <tr>
                <td>Name</td>
                <td><input type="name" name="login" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" /></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="email" name="email" /></td>
            </tr>

        </table>
        <input type="submit" value="Submit" />
    </form>

    <ul>
        <li><a href="tasks">Home</a></li>
        <li><a href="login">Login</a></li>
        <li><a href="reg">Create new user</a>
    </ul>
</div>
</body>
</html>

