<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../../components/Import.jsp" %>
	<title>Thank you</title>
	</head>
	<body>
		<div class="bg-slate-300 w-screen h-screen flex justify-center items-center">
			<div class="flex flex-col justify-center items-center p-24 border bg-white rounded-xl">
				<div class="font-bold text-3xl">🙏 Cảm ơn quý khách đã nhận xét về sản phẩm và dịch vụ của chúng tôi 🙏</div>
				<a class="underline text-blue-500 font-bold text-2xl mt-8" href="./order?orderId=${requestScope.orderId}">
					Quay trở lại đơn hàng
				</a>
			</div>
		</div>
	</body>
</html>