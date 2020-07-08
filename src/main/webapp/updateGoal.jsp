<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 08.07.2020
  Time: 23:25
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

<h1>Add New Task</h1>
<% String guid = (String) request.getParameter("guid");
    out.println(guid); %>
<form action="goals" method="post">
    <input type="hidden" name="_METHOD" value="PUT"/>

    <table class="w3-table">
        <tr><td>Text:</td><td><input type="text" name="text"/></td></tr>
        <tr><td>Parent goal:</td><td>
            <label>
                <select name="parentgoal">
                    <c:forEach items="${requestScope.goals}" var="parentgoal">
                        <option value="${parentgoal.id}">${parentgoal.text}</option>
                    </c:forEach>
                    <option value="0">No parent goal</option>
                </select>
            </label>
        </td></tr>
        <input type="hidden" name="guid" value="<%=guid%>"/>
        <tr><td colspan="2"><input type="submit" value="Update"/></td></tr>
    </table>
</form>

<br/>
<a href="/goals">view goals</a>

</body>
</html>
