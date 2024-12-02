<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
</head>
<body>
	${sr.outRegulation}
	<table>
		<tr>
			<th>exe</th>
			<th>i</th>
			<th>name</th>
			<th>co</th>
			<th>村%</th>
			<th>狼%</th>
			<th>村win%</th>
			<th>狼win%</th>
		</tr>
		<c:forEach var="i" begin="0" end="${sr.roleList.size()-1}" step="1">
			<tr>
				<td><input type="radio" name="exe" value="${i}"></td>
				<td>${i+1}.</td>
				<td><input type="text" name="${i}_name" style="width: 60px"></td>
				<td><select name="${i}_co" style="width: 60px">
						<option value="null">?</option>
						<c:forEach var="key" items="${sr.roleSizeMap.keySet()}">
							<option value="${key.name}">${key.name}</option>
						</c:forEach>
				</select></td>
				<td>${cp.perToJsp(sb.playerList.get(i).villsPer)}</td>
				<td>${cp.perToJsp(sb.playerList.get(i).wwsPer)}</td>
				<td></td>
				<td></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>