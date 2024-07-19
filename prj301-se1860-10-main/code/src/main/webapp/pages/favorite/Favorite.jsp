<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--<fmt:formatNumber type="number" maxFractionDigits="3" value="${product.price}" />--%>

<!DOCTYPE html>
<html>

<head>
	<%@include file="../../components/Import.jsp"%>
	<title>Sản phẩm yêu thích</title>
</head>

<body>
	<%@include file="../../components/MainNav.jsp"%>
	<div class="w-full">
		<div class="m-4 gap-4">
			<div class="row-span-2 p-4 border w-full">
				<!--<ShopcartSection />-->
				<%@include file="FavoriteSection.jsp"%>
			</div>
		</div>
		<div>
			<!--<RelatedProduct />-->
		</div>
		<!--<Footer />-->
		<%@include file="../../components/Footer.jsp"%>
	</div>
	<%@include file="../../components/script/Script.jsp"%>
</body>

</html>