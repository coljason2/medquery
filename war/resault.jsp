<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="java.util.List,com.medquery.comman.MedEntity"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/layout/meta.jsp" />
<title>查詢結果</title>
</head>
<body>
	<jsp:include page="/layout/nav.jsp" />
	<div class="container">
		<table class="table table-striped">
			<tr>
				<td>藥名</td>
				<td>藥價/庫存</td>
				<td>健保碼</td>
				<td>健保價</td>
			</tr>
			<c:forEach items="${meds}" var="item">
				<tr>
					<td>${item.name}</td>
					<td>${item.isenough}</td>
					<td>${item.oid}</td>
					<td>${item.oidprice}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<jsp:include page="/layout/foot.jsp" />
</body>
</html>