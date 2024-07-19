<%@page import="models.product.ProductDTO"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="grid grid-cols-4 my-4 gap-4">
    <div class="col-span-3 border p-4 grid grid-cols-1 gap-6">
        <!--<ProductSection />-->
        <%@include file="TieuThuyet.jsp" %>
        <%@include file="TacPhamKinhDien.jsp" %>
        <%@include file="KinhDi.jsp" %>
        <%@include file="TranhTruyen.jsp" %>
        <!--<ProductSection />-->
    </div>
    <div class="col-span-1 border p-4">
        <div class="flex flex-col">
            <div class="font-bold text-md">Sản phẩm bán chạy</div>
            <div class="flex flex-col gap-4 mt-4">
                <c:forEach items="${mostSelledProducts}" var="product">
                    <div class="text-sm grid grid-cols-4">
                        <c:if test="${product.images.size() != 0}">
                            <a href="./product?id=${product.productId}">
                                <div
                                    style="background-image: url(${product.images[0].url})"
                                    class="aspect-[3/4] w-[60px] bg-center bg-cover no-repeat"
                                    ></div>
                            </a>
                        </c:if>
                        <c:if test="${product.images.size() == 0}">
                            <a href="./product?id=${product.productId}" class="pointer">
                                <div class="aspect-[3/4] w-[60px] bg-slate-100 flex items-center justify-center rounded-md">
                                    <%@include file="../../../components/SmallLogo.jsp" %>
                                </div>
                            </a>
                        </c:if>
                        <div class="grow flex flex-col col-span-3">
                            <div class="overflow-hidden h-5 text-ellipsis whitespace-nowrap">
                                <a href="./product?id=${product.productId}">
                                    ${product.name}
                                </a>
                            </div>
                            <div class="text-xs opacity-50">
                                <c:if test="${product.authors.size() != 0}">
                                    ${product.authors[0].name}
                                </c:if>
                            </div>
                            <div class="flex flex-col justify-center">
                                <div class="text-xs flex gap-4">
                                    <!--<StarRating />-->
                                    <div class="text-[10px] flex items-center gap-4">
                                    </div>
                                </div>

                                <div class="text-sm"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${product.price}"/> ₫</div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
