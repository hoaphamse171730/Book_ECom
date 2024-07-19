<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="block w-full h-screen px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
    <div class="flex justify-between mt-4 mb-2">
        <%@include file="../../../components/NavAvatar.jsp" %>
    </div>
    <!--<DashboardTopNav/>-->
    <div class="flex justify-between mt-6">
        <div class="text-xl font-bold">Customer</div>
    </div>
    <div class="w-full my-4 bg-white">
        <div>
            <!--<ProductHeader />-->
            <div class="grid grid-cols-12 p-4 font-bold border-b rounded-md">
                <div class="col-span-2">Customer ID</div>
                <div class="col-span-2">Name</div>
                <div class="col-span-2">Phone number</div>
                <div class="col-span-2">Created at</div>
                <div class="col-span-1">Genre</div>
                <div class="col-span-1">Role</div>
                <div class="col-span-2">Function</div>
            </div>
            <!--<ProductItem />-->
            <c:forEach items="${customers}" var="customer">
                <div class="grid items-center grid-cols-12 p-4 border-b">
                    <div class="col-span-2"># ${customer.id}</div>
                    <div class="col-span-2 mr-10">${customer.name}</div>
                    <div class="col-span-2 mr-10">${customer.phoneNumber}</div>
                    <div class="col-span-2">${customer.createdAt}</div>
                    <div class="col-span-1 mr-10">${customer.gender}</div>
                    <div class="col-span-1 mr-10">${customer.role}</div>
                    <div class="flex col-span-2 gap-4">
                        <a
                            href="./admin?tab=CustomerManager&action=detail&userId=${customer.id}"
                            class="px-3 py-2 font-bold text-white bg-blue-400 hover:bg-blue-500 rounded-md"
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
