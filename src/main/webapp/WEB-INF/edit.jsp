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
	<h1>Edit ${task.name}</h1>
	<p><form:errors path="task.*"/></p>
	<form:form action="/createTask" method="post" modelAttribute="task">
	    <p>
	        <form:label path="name">Task</form:label>
	        <form:input path="name"/>
	    </p>
	    <p>
		    <form:label path="assignee">Assignee</form:label>
		    <form:select path="assignee">
		    	<c:forEach items="${users}" var="user">
	    		<form:option value="${user.name}">${user.name}</form:option>
	    		</c:forEach>
			</form:select>
		</p>
		<p>
			<form:label path="priority">Priority</form:label>
		    <form:select path="priority">
				<form:option value="High">High</form:option>
				<form:option value="Medium">Medium</form:option>
				<form:option value="Low">Low</form:option>
			</form:select>
		</p>
	    <input type="submit" value="Create"/>
	</form:form>
</body>
</html>