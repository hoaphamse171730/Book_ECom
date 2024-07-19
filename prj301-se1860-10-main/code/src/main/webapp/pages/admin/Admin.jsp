<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>TBSD | Admin</title>
<meta charset="UTF-8">
<%@include file="../../components/Import.jsp"%>
</head>
<body>
	<div class="flex fixed w-full">
		<%@include file="SideNav.jsp"%>
		<c:if test="${tab == 'Dashboard'}">
		</c:if>
		<c:if test="${tab == 'ProductManager'}">
			<c:if test="${action == ''}">
				<%@include file="./product/ProductManager.jsp"%>
			</c:if>
			<c:if test="${action == 'create'}">
				<%@include file="./product/CreateProduct.jsp"%>
			</c:if>
			<c:if test="${action == 'update'}">
				<%@include file="./product/CreateProduct.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'OrderManager'}">
			<c:if test="${action == ''}">
				<%@include file="./order/OrderManager.jsp"%>
			</c:if>
			<c:if test="${action == 'detail'}">
				<%@include file="./order/OrderDetail.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'CustomerManager'}">
			<c:if test="${action == ''}">
				<%@include file="./customer/CustomerManager.jsp"%>
			</c:if>
			<c:if test="${action == 'detail'}">
				<%@include file="./customer/CustomerDetail.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'CategoryManager'}">
			<c:if test="${action == ''}">
				<%@include file="./category/CategoryManager.jsp"%>
			</c:if>
			<c:if test="${action == 'create'}">
				<%@include file="./category/CreateCategory.jsp"%>
			</c:if>
			<c:if test="${action == 'update'}">
				<%@include file="./category/CreateCategory.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'AuthorManager'}">
			<c:if test="${action == ''}">
				<%@include file="./author/AuthorManager.jsp"%>
			</c:if>
			<c:if test="${action == 'detail'}">
				<%@include file="./author/AuthorManager.jsp"%>
			</c:if>
			<c:if test="${action == 'update'}">
				<%@include file="./author/CreateAuthor.jsp"%>
			</c:if>
			<c:if test="${action == 'create'}">
				<%@include file="./author/CreateAuthor.jsp"%>
			</c:if>
		</c:if>
	</div>
	<%@include file="../../../components/script/Script.jsp" %>
</body>
</html>
