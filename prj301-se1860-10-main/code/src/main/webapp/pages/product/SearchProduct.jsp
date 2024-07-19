<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%--<fmt:formatNumber type="number" maxFractionDigits="3" value="${product.price}" />--%>
<!DOCTYPE html>
<html>

	<head>
		<%@include file="../../components/Import.jsp" %>
		<title>Sản phẩm</title>
	</head>

	<body>
		<%@include file="../../components/MainNav.jsp" %>
		<c:if test="${page == null}">
			<c:set var="page" value="1" />
		</c:if>
		<c:if test="${page != null}">
			<c:set var="page" value="${currentPage}" />
		</c:if>
		<div class="w-full">
			<div class="grid grid-cols-7 m-4 gap-4">
				<div class="col-span-1 border p-2">
					<div class="flex flex-col gap-4 [&>*:not(:first-child)]:border-t [&>*:not(:first-child)]:pt-2 mb-4">
						<div class="text-sm font-bold">Danh mục sản phẩm</div>
					</div>
					<div class="flex flex-col gap-4 pt-2">
						<c:forEach items="${categories}" var="cate">
							<div class="transition-all hover:p-2 text-sm hover:bg-blue-500 hover:border hover:text-white hover:font-bold rounded-xl">
								<a href="./search?category=${cate.id}" class="text-sm">${cate.name}</a>
							</div>
							<hr/>
						</c:forEach>
					</div>
				</div>
				<div class="col-span-6 p-4 border">
					<div>
						<div class="font-bold text-2xl">${requestScope.category.name}</div>
						<c:if test="${param.keyword != null}">
							<div class="font-bold text-2xl">Kết quả tìm kiếm cho: ${param.keyword}</div>
						</c:if>
                                <!--<FilterBar />-->
							<div class="font-semibold my-2 flex gap-8">
								<c:if test="${param.keyword != null}">
	                                <a href="./search?keyword=${param.keyword}&search=${param.search}&sort=publish_day&order=desc"
	                                    class="border-blue-500 hover:border-b hover:text-blue-500">
	                                    Mới nhất
	                                </a>
	                                <a href="./search?keyword=${param.keyword}&search=${param.search}&sort=price&order=desc"
	                                    class="border-blue-500 hover:border-b hover:text-blue-500">
	                                    Giá cao đến thấp
	                                </a>
	                                <a href="./search?keyword=${param.keyword}&search=${param.search}&sort=price&order=asc"
	                                    class="border-blue-500 hover:border-b hover:text-blue-500">
	                                    Giá thấp đến cao
	                                </a>
								</c:if>
								<c:if test="${param.keyword == null}">
	                                <a href="./search?category=${requestScope.category.id}&sort=publish_day&order=desc"
	                                    class="border-blue-500 hover:border-b hover:text-blue-500">
	                                    Mới nhất
	                                </a>
	                                <a href="./search?category=${requestScope.category.id}&sort=price&order=desc"
	                                    class="border-blue-500 hover:border-b hover:text-blue-500">
	                                    Giá cao đến thấp
	                                </a>
	                                <a href="./search?category=${requestScope.category.id}&sort=price&order=asc"
	                                    class="border-blue-500 hover:border-b hover:text-blue-500">
	                                    Giá thấp đến cao
	                                </a>
								</c:if>
                            </div>
                            <div>
							<div class="grid grid-cols-5 gap-8 my-4">
								<c:forEach var="product" items="${products}">
						            <div class="items-center">
						                <c:if test="${product.images.size() != 0}">
						                    <a href="./product?id=${product.productId}">
						                        <div style="background-image: url('${product.images[0].url}')"
						                            class="aspect-[3/4] w-full bg-center bg-cover bg-no-repeat rounded-md">
												</div>
						                    </a>
						                </c:if>
						                <c:if test="${product.images.size() == 0}">
						                    <a href="./product?id=${product.productId}" class="pointer aspect-[3/4] w-full bg-slate-100 flex items-center justify-center rounded-md">
						                        <!--<div class="aspect-[3/4] w-full bg-slate-100 flex items-center justify-center rounded-md">-->
						                        <%@include file="../../components/Logo.jsp" %>
						                        <!--</div>-->
						                    </a>
						                </c:if>
						                <div class="w-full flex flex-col">
						                    <div class="text-sm overflow-hidden h-5 text-ellipsis whitespace-nowrap">
						                        <a href="./product?id=${product.productId}">
						                            ${product.name}
						                        </a>
						                    </div>
						                    <div class="text-sm opacity-60 overflow-hidden h-5 text-ellipsis h-4 whitespace-nowrap">
						                        <c:if test="${product.authors.size() != 0}">
						                            ${product.authors[0].name}
						                        </c:if>
						                    </div>
						                    <div class="flex justify-between my-2">
						                        <div class="font-bold"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${product.price}"/> ₫</div>
						                    </div>
						                </div>
						            </div>
								</c:forEach>
							</div>
						</div>
                        <!--<Pagination />-->
                        <%@include file="../../components/Pagination.jsp" %>
					</div>
				</div>
			</div>
		</div>
		<div>
                                <!--<Footer />-->
			<%@include file="../../components/Footer.jsp" %>
		</div>
	</body>

</html>