<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--<fmt:formatNumber type="number" maxFractionDigits="3" value="${product.price}" />--%>

<!DOCTYPE html>
<html>
	<head>
	<%@include file="../../components/Import.jsp"%>
		<title>Giỏ hàng</title>
	</head>
	<body>
		<%@include file="../../components/MainNav.jsp"%>
		<div class="w-full">
			<div class="grid grid-cols-7 m-4 gap-4">
				<div class="col-span-5 row-span-2 p-4 border">
					<!--<ShopcartSection />-->
					<%@include file="ShopcartSection.jsp"%>
				</div>
				<div class="col-span-2 row-span-1 border p-4">
					<!--<ShopcartTotalSection />-->
					<div>
						<div class="font-bold text-2xl cursor-pointer">Thanh toán</div>
						<div class="flex justify-between pt-2">
							<div>Tổng số sản phẩm</div>
							<div id="totalAmount">${totalAmount}</div>
						</div>
						<div class="flex justify-between pt-2 pb-2">
							<div>Tổng cộng</div>
							<div id="totalMoney">
								<fmt:formatNumber type="number" maxFractionDigits="3"
									value="${totalPrice}" />
									<c:if test="${sessionScope.shopcart != null}">đ</c:if>
							</div>
						</div>
						<!--<Button />-->
					</div>
					<c:if test="${requestScope.error != null}">
						<p style="color: red;">${requestScope.error}</p>
					</c:if>
						<c:if test="${sessionScope.shopcart != null}">
							<c:if test="${sessionScope.shopcart.size() != 0}">
								<form action="./buy" method="POST">
									<button type="submit" class="mt-8 bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer">
										Mua
									</button>
								</form>
							</c:if>
						</c:if>
					</div>
				<div>
					<!--<RelatedProduct />-->
				</div>
				<!--<Footer />-->
			</div>
			<%@include file="../../components/Footer.jsp"%>
			<%@include file="../../components/script/Script.jsp"%>
	</body>
</html>