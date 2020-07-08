<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 07.07.2020
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="styles/w3.css">
    <meta charset="ISO-8859-1">
    <title>User tasks</title>
</head>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body>

<h1>User Goals</h1>
<table>
    <tr class="w3-teal">
        <th>text</th>
        <th>tasks</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${requestScope.goals}" var="goal">
        <tr>
            <td>${goal.text}</td>
            <td><a href="/goals?gid=${goal.id}" >show all tasks and child goals</a></td>
            <td><a href="/goals?guid=${goal.id}" >update</a></td>
            <td><a href="/goals?del=${goal.id}" >delete</a></td>
        </tr>
    </c:forEach>
</table>

<h1>User Tasks</h1>
<table>
    <tr class="w3-teal">
        <th>text</th>
        <th>description</th>
        <th>deadline</th>
        <th>status</th>
        <th></th>
        <th></th>
    </tr>

    <c:forEach items="${requestScope.tasks}" var="task">
        <tr>
            <td>${task.text}</td>
            <td>${task.description}</td>
            <td>${task.timeToBeCompleted}</td>
            <td>${task.status}</td>
            <td><a href="/userstasks?tid=${task.id}" >update</a></td>
            <td><a href="/userstasks?del=${task.id}" >delete</a></td>
        </tr>
    </c:forEach>
</table>

<br/>
<a href="/goals?addgoal=true">add goal</a>
<a href="/userstasks?addtask=true">add task</a>
<a href="/goals">all goals</a>
<a href="/login?out=true">logout</a>
</body>
</html>
