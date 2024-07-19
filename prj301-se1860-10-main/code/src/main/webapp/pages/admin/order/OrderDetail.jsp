<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div
	class="block w-full h-screen px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
	<div class="flex justify-between mt-4 mb-2">
        <%@include file="../../../components/NavAvatar.jsp" %>
	</div>
	<!--<DashboardTopNav/>-->
	<div class="flex mt-6">
		<a href="./admin?tab=OrderManager"
			class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer">
			<i class="fa-solid fa-arrow-left"></i>
		</a>
	</div>
	<div
		class="block h-screen w-full overflow-x-hidden bg-[#F4F7FE] rounded-md mt-4">
		<div class="w-full bg-white border rounded-md">
			<div class="col-span-4">
				<div class="flex m-4 gap-8">
					<div class="font-bold">Order #${order.orderId}</div>
					<div class="">${order.createdAt}</div>
				</div>
				<form action="./admin" class="flex items-center m-4 gap-8" method="POST">
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
					<input name="orderId" type="hidden" /> <span class="font-bold">
						Status: </span> 
						<c:if test="${order.status == 'CANCELLED' || order.status == 'COMPLETED'}">
							${order.status}
						</c:if>
						<c:if test="${order.status == 'PENDING' || order.status == 'DELIVERING'}">
							<select name="status" id="status" class="px-2 py-1 border rounded-md" onchange="showMessageBox()" required>
								<c:if test="${order.status == 'PENDING'}">
									<option value="PENDING"
										${order.status == "PENDING" ? "selected" : ""}>PENDING</option>
									<option value="DELIVERING"
										${order.status == "DELIVERING" ? "selected" : ""}>DELIVERING</option>
									<option value="CANCELLED"
										${order.status == "CANCELLED" ? "selected" : ""}>CANCELLED</option>
									<option value="COMPLETED"
										${order.status == "COMPLETED" ? "selected" : ""}>COMPLETED</option>
								</c:if>
								<c:if test="${order.status == 'DELIVERING'}">
									<option value="DELIVERING"
										${order.status == "DELIVERING" ? "selected" : ""}>DELIVERING</option>
									<option value="CANCELLED"
										${order.status == "CANCELLED" ? "selected" : ""}>CANCELLED</option>
									<option value="COMPLETED"
										${order.status == "COMPLETED" ? "selected" : ""}>COMPLETED</option>
								</c:if>
							</select>
						</c:if>
						<div class="w-[300px]" id="message">
							<c:if test="${order.status != 'CANCELLED' && order.status != 'COMPLETED'}">
								<div class="font-bold">Message: </div>
								<textarea name="message" class="p-2 border rounded-md w-full" rows="2"></textarea>
							</c:if>
						</div>
						<c:if test="${order.status == 'PENDING' || order.status == 'DELIVERING'}">
							<button type="submit" class="font-bold px-4 py-2 text-white bg-black rounded-md">
								Save
							</button>
						</c:if>
						<br/>
				</form>
				<table class="mb-8 ml-4">
					<tr>
						<th class="border border-black p-4">STT</th>
						<th class="border border-black py-4 px-32">Product</th>
						<th class="border border-black p-4">Quantity</th>
						<th class="border border-black p-4">Unit price</th>
						<th class="border border-black p-4">Money</th>
					</tr>
					<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="i">
						<tr>
							<td class="border border-black p-4">${i.index+1}</td>
							<td class="border border-black p-4 hover:underline">
								<a href="./product?id=${orderDetail.product.productId}">[#${orderDetail.product.productId}] ${orderDetail.product.name}</a>
							</td>
							<td class="border border-black p-4">${orderDetail.amount}</td>
							<td class="border border-black p-4"><fmt:formatNumber
									type="number" maxFractionDigits="3"
									value="${orderDetail.price}" /> ₫</td>
							<td class="border border-black p-4"><fmt:formatNumber
									type="number" maxFractionDigits="3"
									value="${orderDetail.price*orderDetail.amount}" /> ₫</td>
						</tr>
					</c:forEach>
					<tr>
						<td class="border border-black p-2 font-bold" colspan="4">Total:</td>
						<td class="border border-black p-2 font-bold"><fmt:formatNumber
								type="number" maxFractionDigits="3" value="${order.totalPrice}" />
							₫</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
