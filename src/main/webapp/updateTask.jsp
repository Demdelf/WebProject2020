<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 03.07.2020
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="styles/w3.css">
    <meta charset="ISO-8859-1">
    <title>User tasks</title>
</head>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body class="w3-light-grey">

<h1>Update Task</h1>
<% String tid = (String) request.getParameter("tid");
    out.println(tid); %>
<form action="userstasks" method="post">
    <input type="hidden" name="_METHOD" value="PUT"/>

    <table class="w3-table">
        <tr><td>Text:</td><td><input type="text" name="text"/></td></tr>
        <tr><td>description:</td><td><input type="text" name="description"/></td></tr>
        <tr><td>deadline:</td><td><input type="text" name="deadline"/></td></tr>
        <tr><td>status select:</td><td>
            <label>
                <select name="status">
                <c:forEach items="${requestScope.listStatus}" var="status">
                    <option value="${status.name()}">${status.name}</option>
                </c:forEach>
            </select>
            </label>
        </td></tr>
        <input type="hidden" name="tid" value="<%=tid%>"/>
        <tr><td colspan="2"><input type="submit" value="Update"/></td></tr>
    </table>
</form>

<br/>
<a href="/goals">view goals</a>

</body>
</html>
