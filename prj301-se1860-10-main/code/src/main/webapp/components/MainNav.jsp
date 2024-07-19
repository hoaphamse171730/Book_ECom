<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="flex gap-10 p-4 justify-between border-b">
	<%@include file="Category.jsp"%>
	<%@include file="SearchBar.jsp"%>
	<div>
		<c:if test="${sessionScope.usersession.id != null}">
			<div class="flex items-center gap-4">
				<div onclick="showNotice()">
					<i class="text-slate-500 text-lg fa-solid fa-bell relative cursor-pointer">
						<c:if test="${requestScope.anyRead}">
							<i class="absolute text-[10px] top-0 right-0 text-red-500 fa-solid fa-circle"></i>
						</c:if>
					</i>
					<div id="noti-container" class="hidden absolute w-[240px] -translate-x-1/2 bg-white border rounded-md z-10 text-sm">
						<c:forEach items="${requestScope.notifications}" var="notification" begin="0" end="5">
							<div class="py-2 px-4 hover:bg-slate-200">
								<span class="font-bold">[${notification.type}]</span>
								 ${notification.message}
							</div>
						</c:forEach>
						<div class="">
							<a href="./notification" class="flex justify-center w-full border-t text-blue-500 font-bold underline py-2 px-4 hover:text-blue-800">Xem thêm</a>						
						</div>
					</div>
				</div>
				<a href="./favorite">
					<i class="text-red-500 fa-solid fa-heart"></i>
				</a> 
				<a href="./shopcart">
					<i class="text-slate-500 fa-solid fa-cart-shopping"></i>
				</a>
				<%@include file="NavAvatar.jsp"%>
			</div>
		</c:if>
		<c:if test="${sessionScope.usersession.id == null}">
			<a href="./login"
				class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block">
				Login</a>
		</c:if>
	</div>
</div>
