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
	<h1>Task: ${task.name}</h1>
	<p>Creator: ${task.user.name}</p>
	<p>Assignee: ${task.assignee}</p>
	<p>Priority: ${task.priority}</p>
	<p><c:if test = "${task.user.id == user.id}"><a href="/edit/${task.id}">Edit</a>   <a href="/delete/${task.id}">Delete</a></c:if></p>
	<p><c:if test = "${task.assignee.equals(user.name)}"><a href="/complete/${task.id}">Complete</a></c:if></p>
</body>
</html>