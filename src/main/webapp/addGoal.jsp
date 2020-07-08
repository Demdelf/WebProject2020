<%@ page import="model.Status" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="model.Goal" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 07.07.2020
  Time: 22:45
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
<body class="w3-light-grey">
<%--<% List<Goal> goals = new ArrayList<Goal>( Arrays.asList(Status.values() ));
    request.setAttribute("listStatus", listStatus); %>--%>
<h1>Add New Goal</h1>
<form action="goals" method="post">
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

        <tr><td colspan="2"><input type="submit" value="Save Goal"/></td></tr>
    </table>
</form>

<br/>
<a href="/goals">view goals</a>

</body>
</html>
