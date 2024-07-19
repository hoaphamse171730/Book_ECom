<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../../components/Import.jsp" %>
    <title>Lịch sử mua hàng</title>
</head>
<body>
<%@include file="../../components/MainNav.jsp" %>
<div class="m-4 grid grid-cols-12">
	<div class="col-span-9 border p-4 rounded-md">
       	<div class="text-2xl font-bold">Lịch sử mua hàng</div>
        <form action="./order" method="get" class="mt-3">
        	<div class="inline-block font-bold mr-4">Trạng thái: </div>
            <select name="status" class="border border-2 p-1 rounded-sm">
                <option value="ALL" ${currentStatus == 'ALL' ? 'selected' : ''}>Tất cả</option>
                <option value="PENDING" ${currentStatus == 'PENDING' ? 'selected' : ''}>Đang xử lí</option>
                <option value="COMPLETED" ${currentStatus == 'COMPLETED' ? 'selected' : ''}>Đã mua</option>
                <option value="CANCELLED" ${currentStatus == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                <option value="DELIVERING" ${currentStatus == 'DELIVERING' ? 'selected' : ''}>Đang vận chuyển</option>
            </select>

        	<div class="inline-block font-bold mx-4">Sắp xếp theo: </div>
            <select name="sortBy" class="border border-2 rounded-sm p-1">
                <option value="order_id" ${currentSortBy == 'order_id' ? 'selected' : ''}>Mã đơn hàng</option>
                <option value="total_price" ${currentSortBy == 'total_price' ? 'selected' : ''}>Tổng tiền</option>
                <option value="created_at" ${currentSortBy == 'created_at' ? 'selected' : ''}>Ngày đặt</option>
            </select>

			<div class="inline-block font-bold mx-4">Thứ tự: </div>
            <select name="sortOrder" class="border border-2 rounded-sm p-1">
                <option value="ASC" ${currentSortOrder == 'ASC' ? 'selected' : ''}>Tăng dần</option>
                <option value="DESC" ${currentSortOrder == 'DESC' ? 'selected' : ''}>Giảm dần</option>
            </select>

            <button type="submit" class="px-4 py-1 font-bold bg-blue-500 rounded-md mx-4 text-white">
            	<i class="fa-solid fa-filter pr-1"></i>
            	Lọc
            </button>
        </form>
        <c:if test="${not empty orders}">
            	<div class="grid grid-cols-12 w-full font-bold my-3 text-lg">
                    <div class="col-span-2 font-bold">Order ID</div>
                    <div class="col-span-2">Total Price</div>
                    <div class="col-span-2">Status</div>
                    <div class="col-span-3">Created At</div>
                    <div class="col-span-3">Details</div>
            	</div>
                <c:forEach items="${orders}" var="order">
	            	<div class="grid grid-cols-12 w-full text-lg py-2">
                        <div class="col-span-2">#${order.orderId}</div>
                        <div class="col-span-2"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${order.totalPrice}"/> ₫</div>
                        <div class="col-span-2">${order.status}</div>
                        <div class="col-span-3">${order.createdAt}</div>
                        <div class="col-span-3 text-sm -translate-x-8">
	                        <a href="./order?orderId=${order.orderId}" class="px-4 py-2 font-bold bg-blue-500 rounded-md text-white">
	                        	View Details
	                        </a>
                        </div>
	            	</div>
                </c:forEach>
        </c:if>
        <c:if test="${empty orders}">
            <p>No orders found.</p>
        </c:if>
       </div>
       <div class="col-span-3">
       	
       </div>
</div>
<%@include file="../../components/script/Script.jsp" %>
</body>
</html>
