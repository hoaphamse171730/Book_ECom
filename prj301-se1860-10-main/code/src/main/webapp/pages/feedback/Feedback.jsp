<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../../components/Import.jsp" %>
        <title>Đánh giá sản phẩm</title>
    </head>
    <body>
    	<%@include file="../../components/MainNav.jsp" %>
    	<c:set value="${requestScope.product}" var="product"/>
    	<c:set value="${requestScope.feedback}" var="feedback"/>
		<div class="mx-4 my-4 grid grid-cols-12">
			<div class="col-span-12">
		        <div class="w-full bg-white border rounded-md">
					<div class="col-span-7 m-4">
	        			<div class="text-xl font-bold">Đánh giá sản phẩm</div>
	        				<div class="border rounded-md p-8 flex gap-8 text-md my-4">
								<c:if test="${product.images.size() != 0}">
									<a href="./product?id=${product.productId}">
										<div
											style="background-image: url('${product.images[0].url}')"
											class="aspect-[3/4] w-[160px] bg-center bg-cover bg-no-repeat rounded-md"></div>
									</a>
								</c:if>
								<div class="w-full">
									<div class="pb-2">
										<div class="font-bold text-xl">${product.name}</div>
										<div class="border-b inline pb-1">${product.authors[0].name}</div>
									</div>
									<div class="text-ellipsis overflow-hidden h-48">
										${product.description}
									</div>
									<div class="flex gap-8 items-center mt-4">
										<div class="font-bold text-lg">
											<fmt:formatNumber type="number" maxFractionDigits="3"
												value="${product.price}" />
											₫
										</div>
									</div>
								</div>
							</div>
						</div>
					<div class="m-4 col-span-12">
						<form action="./feedback" class="gap-4" method="POST">
							<input type="hidden" name="tab" value="OrderManager"/>
							<input type="hidden" name="feedbackId" value="${feedback.feedbackId}"/>
							<input type="hidden" name="action" value="update"/>
							<input type="hidden" name="orderId" value="${requestScope.orderId}"/>
							<input type="hidden" name="productId" value="${product.productId}"/>
							<div class="flex gap-4">
								<div class="font-semibold text-lg">Đánh giá:</div>
								<select name="rating" class="border boder-2 rounded-md px-2 py-1" required>
									<option value="5" selected>5</option>
									<option value="4">4</option>
									<option value="3">3</option>
									<option value="2">2</option>
									<option value="1">1</option>
								</select>
							</div>
							<div class="font-semibold text-lg">Nhận xét về sản phẩm:</div>
							<textarea name="content" class="border w-full p-2" rows="5" required>${feedback.content}</textarea>
							<button type="submit" class="px-4 py-2 font-bold bg-blue-500 rounded-md text-white">
								Feedback
							</button>
						</form>
					</div>
				</div>
	        </div>
	        <div class="col-span-3">
	        </div>
		</div>
		<%@include file="../../components/script/Script.jsp" %>
    </body>
</html>
