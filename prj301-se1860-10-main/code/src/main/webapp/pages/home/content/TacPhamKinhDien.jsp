<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="">
    <div class="font-bold text-2xl">Tác phẩm kinh điển</div>
    <div class="my-8">
        <%--<%@include file="SpecialItem.jsp" %>--%>
        <c:set var="specialItem" value="${tacPhamKinhDien[0]}"/>
        <div class="border rounded-md p-8 flex gap-8 text-md">
            <c:if test="${specialItem.images.size() != 0}">
                <a href="./product?id=${specialItem.productId}">
                    <div
                        style="background-image: url('${specialItem.images[0].url}')"
                        class="aspect-[3/4] w-[160px] bg-center bg-cover bg-no-repeat rounded-md"
                        ></div>
                </a>
            </c:if>
            <div class="w-full">
                <div class="pb-2">
                    <div class="font-bold text-xl">${specialItem.name}</div>
                    <div class="border-b inline pb-1">${specialItem.authors[0].name}</div>
                </div>
                <div class="text-ellipsis overflow-hidden h-48">
                    ${specialItem.description}
                </div>
                <div class="flex gap-8 items-center mt-4">
                    <div class="font-bold text-lg"><fmt:formatNumber type="number" maxFractionDigits = "3" value="${specialItem.price}"/> ₫</div>
                    <form action="./shopcart">
                    	<input type="hidden" name="action" value="add"/>
                    	<input type="hidden" name="productId" value="${specialItem.productId}"/>
                    	<input type="hidden" name="amount" value="1"/>
        	            <button type="submit" class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block">
    	                    Thêm vào giỏ
	                    </button>
                    </form>
                    <form action="./favorite">
                    	<input type="hidden" name="action" value="add"/>
                    	<input type="hidden" name="productId" value="${specialItem.productId}"/>
        	            <button type="submit" class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block">
    	                    Yêu thích
	                    </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="grid grid-cols-5 gap-8 my-4">
        <c:forEach items="${tacPhamKinhDien}" var="product" begin="1">
            <div class="items-center">
                <c:if test="${product.images.size() != 0}">
                    <a href="./product?id=${product.productId}">
                        <div
                            style="background-image: url('${product.images[0].url}')"
                            class="aspect-[3/4] w-full bg-center bg-cover bg-no-repeat rounded-md"
                            ></div>
                    </a>
                </c:if>
                <c:if test="${product.images.size() == 0}">
                    <a href="./product?id=${product.productId}" class="pointer aspect-[3/4] w-full bg-slate-100 flex items-center justify-center rounded-md">
                        <!--<div class="aspect-[3/4] w-full bg-slate-100 flex items-center justify-center rounded-md">-->
                        <%@include file="../../../components/Logo.jsp" %>
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
