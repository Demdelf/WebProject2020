<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 30.06.2020
  Time: 0:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="styles/w3.css">
</head>
<link rel="stylesheet" href="styles/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body class="w3-light-grey">
<div class="w3-container w3-teal" align="center">
    <h1>User Login Form</h1>
    <form action="login" method="post">
        <table style="with: 100%" class="w3-table">
            <tr>
                <td>Name</td>
                <td><input type="name" name="login" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" /></td>
            </tr>
            <input type="submit" value="Submit" class="w3-btn w3-round-large"/>
        </table>
    </form>
    <ul>
        <li><a href="goals">Home</a></li>
        <li><a href="login">Login</a></li>
        <li><a href="reg">Create new user</a>
    </ul>
</div>
</body>
</html>
