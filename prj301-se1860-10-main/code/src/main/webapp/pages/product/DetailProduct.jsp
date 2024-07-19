<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../components/Import.jsp"%>
<title>${currentProduct.name}</title>
</head>
<body>
	<%@include file="../../components/MainNav.jsp"%>
	<div class="w-full p-4">
		<div class="grid grid-cols-1 my-4 gap-4">
			<!--<ProductOverview />-->
			<div class="border p-4">
				<div class="flex gap-8">
					<div class="flex flex-col gap-4">
						<c:forEach items="${currentProduct.images}" var="image" begin="1">
							<div
								style="
                                    background-image: url('${image.url}');
                                    "
								class="aspect-[3/4] w-[60px] bg-center bg-contain bg-no-repeat rounded-md"></div>
						</c:forEach>
					</div>
					<c:if test="${currentProduct.images.size() != 0}">
						<div
							style="
                                background-image: url('${currentProduct.images[0].url}');
                                "
							class="aspect-[3/4] h-[400px] bg-center bg-contain bg-no-repeat rounded-md"></div>
					</c:if>
					<c:if test="${currentProduct.images.size() == 0}">
						<div
							class="aspect-[3/4] h-[400px] bg-slate-200 flex justify-center items-center rounded-md">
							<%@include file="../../components/BigLogo.jsp"%>
						</div>
					</c:if>
					<div class="grow flex flex-col gap-2 font-normal">
						<div>
							<div class="text-2xl">${currentProduct.name}</div>
							<a href="" class="font-bold text-slate-400"> <c:if
									test="${currentProduct.authors.size() != 0}">
									<div class="flex gap-4">
										<c:forEach items="${currentProduct.authors}" var="author">
											<div>${author.name}</div>
										</c:forEach>
									</div>
								</c:if>
							</a>
						</div>
						<!--<StarRating />-->
						<div class="grid grid-cols-2 gap-1">
							<c:if test="${currentProduct.manufacturer.length() > 0}">
								<div class="text-slate-400">
									NXB: <span class="text-black">${currentProduct.manufacturer}</span>
								</div>
							</c:if>
							<c:if test="${currentProduct.size.length() > 0}">
								<div class="text-slate-400">
									Kích thước: <span class="text-black">${currentProduct.size}</span>
								</div>
							</c:if>
							<c:if test="${currentProduct.format.length() > 0}">
								<div class="text-slate-400">
									Loại bìa: <span class="text-black">${currentProduct.format}</span>
								</div>
							</c:if>
							<c:if test="${currentProduct.pages > 0}">
								<div class="text-slate-400">
									Số trang: <span class="text-black">${currentProduct.pages}</span>
								</div>
							</c:if>
							<div class="text-slate-400">
								Ngày xuất bản: <span class="text-black">${currentProduct.publishDay}</span>
							</div>
						</div>
						<div>Giao hàng siêu tốc trong nội đô HN, TP.HCM</div>
						<div>Chính sách đổi trả: đổi trả sản phẩm trong 30 ngày</div>
						<div class="flex gap-16">
							<div class="font-bold text-xl">
								Giá:
								<fmt:setLocale value="vi_VN" />
								<fmt:formatNumber type="currency"
									value="${currentProduct.price}" />
							</div>
							<div class="">
								Tồn kho:
								<fmt:setLocale value="vi_VN" />
								<fmt:formatNumber type="number" value="${currentProduct.remain}" />
								sản phẩm
							</div>
						</div>
						<div class="flex gap-8 items-center">
							<form action="./shopcart">
								<input name="action" value="add" type="hidden" /> <input
									name="productId" value="${currentProduct.productId}"
									type="hidden" />
								<div class="flex items-center gap-8 my-4 font-semibold">
									<!--<Quantity />-->
									Số lượng: <input type="number" value="1" name="amount" min="1"
										max="${currentProduct.remain}"
										title="Bạn đã đặt quá số lượng hàng trong kho"
										id="quantity-input"
										class="border border-x-[0.25px] w-[80px] flex justify-center items-center px-2 py-2" />
									<input type="submit"
										class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer"
										value='Thêm vào giỏ' />
								</div>
							</form>
							<form action="./favorite">
								<input type="hidden" name="productId" value="${currentProduct.productId}"/>
								<input type="hidden" name="action" value="add"/>
								<button type="submit"
									class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block">Yêu
									thích
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!--<ProductDescription />-->
			<div class="border p-4">
				<div class="font-bold text-3xl mb-4">Description</div>
				<div>${currentProduct.description}</div>
			</div>
			<!--<FeedbackSection />-->
			<div class="grid grid-cols-6">
				<div class="border p-4 col-span-5">
					<div class="font-bold text-3xl">Feedback</div>
					<!--<FeedbackOverview />-->
					<div class="flex gap-4 text-sm font-bold mt-4">
					</div>
					<div class="max-h-[800px] overflow-scroll overflow-x-hidden">
						<!--<FeedbackItem />-->
						<c:forEach items="${feedbacks}" var="comment">
							<div class="my-4 border rounded-lg p-4">
								<div class="flex gap-4">
									<%@include file="../../components/Avatar.jsp"%>
									<!--<div class="aspect-square rounded-full w-[40px] bg-slate-400"></div>-->
									<div>
										<div class="font-semibold text-sm mb-1">${comment.user.name}</div>
										<div class="text-xs flex items-center gap-4">
											<div class="flex gap-1">
												<span class="fa fa-star ${comment.rating >= 1 ? "text-yellow-500" : ""}"></span>
												<span class="fa fa-star ${comment.rating >= 2 ? "text-yellow-500" : ""}"></span>
												<span class="fa fa-star ${comment.rating >= 3 ? "text-yellow-500" : ""}"></span>
												<span class="fa fa-star ${comment.rating >= 4 ? "text-yellow-500" : ""}"></span>
												<span class="fa fa-star ${comment.rating >= 5 ? "text-yellow-500" : ""}"></span>
											</div>
										</div>
									</div>
								</div>
								<div class="ml-14 mt-2">${comment.content}</div>
							</div>
						</c:forEach>
					</div>
					<!--<Pagination />-->

				</div>
			</div>
		</div>
		<!--<RelatedProduct />-->

		<!--<Footer/>-->
		<%@include file="../../components/Footer.jsp"%>
	</div>
	<%@include file="../../components/script/Script.jsp"%>
</body>
</html>