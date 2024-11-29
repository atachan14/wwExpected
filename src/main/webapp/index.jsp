<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>wwExpectedr</title>
</head>
<body>
<c:forEach var="roll" items="rollsList">
<c:out value="${rollsList.name}" /> <input type="number">
</c:forEach>
</body>
</html>