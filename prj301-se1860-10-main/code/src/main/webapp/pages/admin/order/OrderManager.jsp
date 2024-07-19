<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="block w-full h-screen px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
    <div class="flex justify-between mt-4 mb-2">
        <%@include file="../../../components/NavAvatar.jsp" %>
    </div>
    <!--<DashboardTopNav/>-->
    <div class="flex justify-between mt-6">
        <div class="text-xl font-bold">Order</div>
    </div>
    <div class="w-full my-4 bg-white">
        <div>
            <!--<ProductHeader />-->
            <div class="grid grid-cols-12 p-4 font-bold border-b rounded-md">
                <div class="col-span-2">Order ID</div>
                <div class="col-span-2">User ID</div>
                <div class="col-span-2">Total price</div>
                <div class="col-span-2">Status</div>
                <div class="col-span-2">Created at</div>
                <div class="col-span-2">Function</div>
            </div>
            <!--<ProductItem />-->
            <c:forEach items="${orders}" var="order">
                <div class="grid items-center grid-cols-12 p-4 border-b">
                    <div class="col-span-2"># ${order.orderId}</div>
                    <div class="col-span-2 mr-10"># ${order.userId}</div>
                    <div class="col-span-2"><div class=""><fmt:formatNumber type="number" maxFractionDigits = "3" value="${order.totalPrice}"/> VND</div></div>
                    <div class="col-span-2 text-lg font-bold ${order.status == 'COMPLETED' ? "text-green-600":""}${order.status == 'CANCELLED' ? "text-red-600":""}${order.status == 'PENDING' || order.status == 'DELIVERING' ? "text-yellow-500":""}">${order.status}</div>
                    <div class="col-span-2">${order.createdAt}</div>
                    <div class="flex col-span-2 gap-4">
                        <a
                            href="./admin?tab=OrderManager&action=detail&orderId=${order.orderId}"
                            class="px-3 py-2 font-bold bg-blue-400 text-white rounded-md hover:bg-blue-500"
                            >
                            View details
                        </a>
                    </div>
                </div>
            </c:forEach>

            <div class="p-2">
                <%@include file="../../../components/Pagination.jsp" %>
            </div>
        </div>
    </div>
</div>
