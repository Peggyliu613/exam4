<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exam4</title>
</head>
<body>
	<h1>Welcome, ${user.name}</h1>
	<p><a href="/logout">Logout</a></p>
	<table>
		<thead>
			<tr>
				<th>Task</th>
				<th>Creator</th>
				<th>Assignee</th>
				<th>Priority</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tasks}" var="task">
		        <tr>
		            <td><a href="/${task.id}">${task.name}</a></td>
		            <td>${task.user.name}</td>
		            <td>${task.assignee}</td>
		            <td>${task.priority}</td>
		        </tr>
			</c:forEach>
		</tbody>
	</table>
	<p><a href="/createTask">Create Task</a></p>
</body>
</html>