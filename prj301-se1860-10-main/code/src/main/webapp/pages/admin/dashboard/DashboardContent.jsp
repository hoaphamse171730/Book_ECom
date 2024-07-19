<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div
	class="block w-full h-screen px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
	<div class="flex justify-between mt-4 mb-2">
	<div class="flex mt-6 gap-4 ">
		<div
			class="bg-white col-span-1 font-bold justify-center flex items-center rounded-xl text-sm py-2 px-4">
			<div
				class="text-xl bg-slate-100 flex justify-center p-2 rounded-full">
				<i class="fa-solid fa-chart-simple text-indigo-700"></i>
			</div>
			<div class="flex flex-col ml-2">
				<div class="text-slate-400">Doanh thu</div>
				<div class="w-auto"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${requestScope.tongDoanhThu}"/> tỷ VND</div>
			</div>
		</div>
		<div
			class="bg-white col-span-1 font-bold justify-center flex items-center rounded-xl text-sm py-2 px-4">
			<div
				class="text-xl bg-slate-100 flex justify-center p-2 rounded-full">
				<i class="fa-solid fa-receipt text-indigo-800"></i>
			</div>
			<div class="flex flex-col ml-2">
				<div class="text-slate-400">Đơn đã bán</div>
				<div class="w-auto"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${requestScope.tongDonHang}"/> đơn</div>
			</div>
		</div>
		<div
			class="bg-white col-span-1 font-bold justify-center flex items-center rounded-xl text-sm py-2 px-4">
			<div
				class="text-xl bg-slate-100 flex justify-center p-2 rounded-full">
				<i class="fa-solid fa-dollar-sign text-indigo-800"></i>
			</div>
			<div class="flex flex-col ml-2">
				<div class="text-slate-400">Sản phẩm đã bán</div>
				<div class="-auto"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${requestScope.tongSanPham}"/> sản phẩm</div>
			</div>
		</div>
		<div
			class="bg-white col-span-1 font-bold justify-center flex items-center rounded-xl text-sm py-2 px-4">
			<div
				class="text-xl bg-slate-100 flex justify-center p-2 rounded-full">
				<i class="fa-solid fa-house text-indigo-800"></i>
			</div>
			<div class="flex flex-col ml-2">
				<div class="text-slate-400">Tồn kho</div>
				<div class="-auto"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${requestScope.tonKho}"/> sản phẩm</div>
			</div>
		</div>
	</div>
        <%@include file="../../../components/Avatar.jsp" %>
	</div>
	<!--<DashboardTopNav/>-->
	<div class="w-full">
		<div class="grid grid-cols-2 gap-4">
			<div class="col-span-1">
				<div id="chartContainer1" style="height: 370px; width: 100%;"></div>
			</div>
			<div class="col-span-1">
				<div id="chartContainer2" style="height: 370px; width: 100%;"></div>
			</div>
		</div>
		<div>
			<!--<ProductHeader />-->
			<div class="grid grid-cols-12 gap-4 mt-8">
				<div class="col-span-6 rounded-md border bg-white">
					<div class="font-bold p-4">Khách hàng thân thiết</div>
					<c:forEach items="${requestScope.mostPotentialCustomer}" var="user" varStatus="i">
						<div class="text-sm px-4 py-2">
						<span class="${i.index == 0 || i.index == 1 || i.index == 2 ? "font-bold":""} ${i.index == 0 ? "text-yellow-500":""} ${i.index == 1 ? "text-slate-500":""} ${i.index == 2 ? "text-red-500":""}"">Top.${i.index+1}</span>
						<span class="font-semibold">[#${user.id}] ${user.name}</span> đã mua <span class="text-red-500 font-semibold"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${user.moneySpend}"/></span> tiền sách</div>
					</c:forEach>
				</div>
				<div class="col-span-6 border bg-white rounded-md">
					<div class="font-bold p-4">Sản phẩm bán chạy</div>
					<c:forEach items="${requestScope.mostSelledProduct}" var="product" varStatus="i">
						<div class="text-sm px-4 py-2">
						<span class="${i.index == 0 || i.index == 1 || i.index == 2 ? "font-bold":""} ${i.index == 0 ? "text-yellow-500":""} ${i.index == 1 ? "text-slate-500":""} ${i.index == 2 ? "text-red-500":""}"">Top.${i.index+1}</span>
						<span class="font-semibold">[#${product.productId}] ${product.name}</span> đã bán được <span class="text-red-500 font-bold">${product.selledAmount}</span> sản phẩm</div>
					</c:forEach>
				</div>
			</div>
			<div class="grid grid-cols-12 p-4 font-bold border-b rounded-md">
				<div class="col-span-6"></div>
				<div class="col-span-3"></div>
				<div class="col-span-3"></div>
			</div>
			<!--<ProductItem />-->
		</div>
	</div>
</div>
