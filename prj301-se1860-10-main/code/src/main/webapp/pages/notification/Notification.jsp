<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../../components/Import.jsp" %>
        <title>Thông báo</title>
    </head>
    <body>
    	<%@include file="../../components/MainNav.jsp" %>
    	<c:set value="${requestScope.product}" var="product"/>
    	<c:set value="${requestScope.feedback}" var="feedback"/>
		<div class="mx-4 my-4 grid grid-cols-12">
			<div class="col-span-12">
		        <div class="w-full bg-white border rounded-md">
					<div class="col-span-7 m-4">
	        			<div class="text-xl font-bold"></div>
	        				<c:forEach items="${requestScope.notifications}" var="notification">
		        				<div class="border rounded-md flex gap-8 text-md my-4">
									<div class="w-full p-4 ${notification.read ? "bg-slate-300 opacity-30":""}">
										<div class="flex gap-4 items-center">
											<div class="font-bold text-lg">[${notification.type}]</div>
											<div class="text-md font-semibold inline">${notification.message} (${notification.createdAt})</div>
										</div>
										<c:if test="${!notification.read}">
											<form action="./notification" class="gap-4 mt-2" method="POST">
												<input type="hidden" name="notificationId" value="${notification.notificationId}"/>
												<button type="submit" class="px-2 py-1 font-bold bg-blue-500 rounded-md text-white">
													Đánh dấu là đã xem
												</button>
											</form>
										</c:if>
									</div>
								</div>
	        				</c:forEach>
						</div>
					<div class="m-4 col-span-12">
					</div>
				</div>
	        </div>
	        <div class="col-span-3">
	        </div>
		</div>
		<%@include file="../../components/script/Script.jsp" %>
    </body>
</html>
