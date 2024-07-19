<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div>
	<div class="font-bold text-2xl">Sản phẩm yêu thích</div>
	<div class="my-4">
		<!--<ShopcartHeader />-->
		<c:if test="${requestScope.favoriteItems.size() == 0}">
			<div>Hiện chưa có sản phẩm yêu thích nào</div>
		</c:if>
		<div>
			<c:forEach items="${requestScope.favoriteItems}" var="specialItem">
				<!--<ShopcartItem />-->
				<div class="border rounded-md p-8 flex gap-8 text-md mb-4">
					<c:if test="${specialItem.product.images.size() != 0}">
						<a href="./product?id=${specialItem.productId}">
							<div
								style="background-image: url('${specialItem.product.images[0].url}')"
								class="aspect-[3/4] w-[160px] bg-center bg-cover bg-no-repeat rounded-md"></div>
						</a>
					</c:if>
					<div class="w-full">
						<div class="pb-2">
							<div class="font-bold text-xl">${specialItem.product.name}</div>
							<div class="border-b inline pb-1">${specialItem.product.authors[0].name}</div>
						</div>
						<div class="text-ellipsis overflow-hidden h-48">
							${specialItem.product.description}
						</div>
						<div class="font-semibold">
							Tồn kho:
							<fmt:setLocale value="vi_VN" />
							<fmt:formatNumber type="number" value="${specialItem.product.remain}" />
							sản phẩm
						</div>
						<div class="flex gap-8 items-center mt-4">
							<div class="font-bold text-lg">
								<fmt:formatNumber type="number" maxFractionDigits="3"
									value="${specialItem.product.price}" />
								₫
							</div>
							<form action="./shopcart">
								<input name="action" value="add" type="hidden" /> <input
									name="productId" value="${specialItem.product.productId}"
									type="hidden" />
								<div class="flex items-center gap-8 my-4 font-semibold">
									<!--<Quantity />-->
									Số lượng: <input type="number" value="1" name="amount" min="1"
										max="${specialItem.product.remain}"
										title="Bạn đã đặt quá số lượng hàng trong kho"
										id="quantity-input"
										class="border border-x-[0.25px] w-[80px] flex justify-center items-center px-2 py-2" />
									<input type="submit"
										class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer"
										value='Thêm vào giỏ' />
								</div>
							</form>
							<form action="./favorite">
								<input type="hidden" name="productId" value="${specialItem.product.productId}"/>
								<input type="hidden" name="action" value="delete"/>
								<button type="submit"
									class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block">
									Bỏ thích
								</button>
							</form>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
