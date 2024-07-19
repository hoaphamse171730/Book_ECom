<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../../components/Import.jsp" %>
        <title>Chi tiết đơn hàng</title>
    </head>
    <body>
    	<%@include file="../../components/MainNav.jsp" %>
		<div class="mx-4 my-4 grid grid-cols-12">
			<div class="col-span-10">
		        <div class="w-full bg-white border rounded-md">
					<div class="col-span-5">
							<a href="./order"
								class="mt-4 ml-4 bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer">
								<i class="fa-solid fa-arrow-left"></i>
							</a>
		        			<div class="inline-block text-2xl font-bold m-4 translate-y-1">Chi tiết đơn hàng</div>
							<div class="flex m-4 gap-12">
							<div class="font-bold">Order #${order.orderId}</div>
							<div class="">${order.createdAt}</div>
						</div>
					<form action="./admin" class="flex items-center m-4 gap-12" method="POST">
						<input type="hidden" name="tab" value="OrderManager"/>
						<input type="hidden" name="action" value="update"/>
						<input type="hidden" name="orderId" value="${order.orderId}"/>
						<div class="">
							<span class="font-bold"> UserID: </span> ${order.userId}
						</div>
					<div class="flex">
						<span class="font-bold mr-2"> Total price: </span>
						<div class="font-bold text-md">
							<fmt:formatNumber type="number" maxFractionDigits="3"
								value="${order.totalPrice}" /> ₫
						</div>
					</div>
					<input name="orderId" type="hidden" />
					<div>
						<span class="font-bold">Status: </span>${order.status}
					</div>
				</form>
				<table class="mb-8 ml-4">
					<tr>
						<th class="border border-black p-4">STT</th>
						<th class="border border-black py-4 px-32">Product</th>
						<th class="border border-black p-4">Quantity</th>
						<th class="border border-black p-4">Price</th>
						<th class="border border-black p-4">Total</th>
						<th class="border border-black p-4">Feedback</th>
					</tr>
					<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="i">
						<tr>
							<td class="border border-black p-4">${i.index+1}</td>
							<td class="border border-black p-4">
								<a class="hover:underline" href="./product?id=${orderDetail.product.productId}">[#${orderDetail.product.productId}] ${orderDetail.product.name}</a>
							</td>
							<td class="border border-black p-4">${orderDetail.amount}</td>
							<td class="border border-black p-4"><fmt:formatNumber
									type="number" maxFractionDigits="3"
									value="${orderDetail.price}" /> ₫</td>
							<td class="border border-black p-4"><fmt:formatNumber
									type="number" maxFractionDigits="3"
									value="${orderDetail.price*orderDetail.amount}" /> ₫</td>
							<td class="border border-black p-4">
								<a href="./feedback?productId=${orderDetail.product.productId}&orderId=${order.orderId}"
								   class="px-4 py-2 font-bold rounded-md text-white ${order.status != 'COMPLETED' ? 'bg-slate-300 cursor-not-allowed' : 'bg-blue-500'}"
								   style="${order.status != 'COMPLETED' ? 'pointer-events: none;' : ''}">
								   Feedback
								</a>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td class="border border-black p-2 font-bold" colspan="4">Total:</td>
						<td class="border border-black p-2 font-bold" colspan="2"><fmt:formatNumber
								type="number" maxFractionDigits="3" value="${order.totalPrice}" />
							₫</td>
					</tr>
				</table>
			</div>
		</div>
	        </div>
	        <div class="col-span-3">
	        	
	        </div>
		</div>
		<%@include file="../../components/script/Script.jsp" %>
    </body>
</html>
