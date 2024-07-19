<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div>
	<div class="font-bold text-2xl">Giỏ hàng</div>
	<div class="my-4">
		<!--<ShopcartHeader />-->
		<c:if test="${sessionScope.shopcart == null}">
			<div>Giỏ hàng của bạn hiện tại không có sản phẩm nào</div>			
		</c:if>
		<c:if test="${sessionScope.shopcart != null}">
			<c:if test="${sessionScope.shopcart.size() == 0}">
				<div>Giỏ hàng của bạn hiện tại không có sản phẩm nào</div>			
			</c:if>
		</c:if>
		<c:if test="${sessionScope.shopcart != null}">
			<c:if test="${sessionScope.shopcart.size() != 0}">
				<div class="grid grid-cols-12 items-center justify-between mb-4 gap-4">
					<div class="col-span-5 grid grid-cols-12 flex gap-3">
						<div class="col-span-12 pl-4 font-bold">Sản phẩm</div>
					</div>
					<div class="col-span-2 font-bold">Giá</div>
					<div class="col-span-2 font-bold">Số lượng</div>
					<div class="col-span-2 font-bold">Thành tiền</div>
					<div class="col-span-1">
						<i class="fa-regular fa-trash-can"></i>
					</div>
				</div>
			</c:if>
		</c:if>
		<div>
			<c:if test="${sessionScope.shopcart != null}">
				<c:if test="${sessionScope.shopcart.size() != 0}">
					<c:forEach items="${sessionScope.shopcart}" var="item">
				<!--<ShopcartItem />-->
				<div id="parent-${item.product.productId}"
					class="grid grid-cols-12 items-center justify-between py-4 gap-4">
					<a href="./product?id=${item.product.productId}"
						class="col-span-5 grid grid-cols-12 gap-3 items-center"> <c:if
							test="${item.product.images.size() != 0}">
							<div
								style="background-image: url('${item.product.images[0].url}')"
								class="col-span-4 aspect-square w-full bg-center bg-contain bg-no-repeat rounded-md"></div>
						</c:if> <c:if test="${item.product.images.size() == 0}">
							<div
								class="col-span-5 aspect-square w-full flex items-center justify-center rounded-md text-xs border">
								<%@include file="../../components/SmallLogo.jsp"%>
							</div>
						</c:if>
						<div class="col-span-8 text-sm px-2">${item.product.name}</div>
					</a>
					<div id="p${item.product.productId}" class="col-span-2 price">
						<fmt:formatNumber type="number" maxFractionDigits="3"
							value="${item.product.price}" />
						đ
					</div>
					<div class="col-span-2">
						<!--<Quantity />-->
						<div class="inline-block">
							<div class="flex">
								<div class="flex justify-center items-center">${item.amount}</div>
							</div>
						</div>
					</div>
					<div id="p${item.product.productId}"
						class="productTotalMoney col-span-2">
						<fmt:formatNumber type="number" maxFractionDigits="3"
							value="${item.product.price * item.amount}" />
						đ
					</div>
					<div class="col-span-1 pr-2">
						<form action="./shopcart">
							<input type="hidden" name="action" value="delete" /> <input
								type="hidden" name="productId" value="${item.product.productId}" />
							<input type="submit" value="Remove"
								class="-translate-x-6 border px-2 py-1 font-bold rounded-md transition-all hover:bg-red-500 hover:text-white cursor-pointer" />
						</form>
					</div>
				</div>
					</c:forEach>
				</c:if>
			</c:if>
		</div>
	</div>
</div>
