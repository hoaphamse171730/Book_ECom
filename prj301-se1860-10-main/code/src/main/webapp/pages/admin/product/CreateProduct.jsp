<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="block h-screen w-full px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
    <div class="flex justify-between mt-4 mb-2">
        <%@include file="../../../components/NavAvatar.jsp" %>
    </div>
    <!--<DashboardTopNav/>-->
    <div class="flex mt-6">
        <a href="./admin?tab=ProductManager" class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer">
            <i class="fa-solid fa-arrow-left"></i>
        </a>
    </div>
    <div class="block w-full overflow-x-hidden bg-[#F4F7FE] rounded-md mt-4">
        <div class="w-full bg-white border rounded-md">
            <form action="./admin?tab=ProductManager&action=${action}" method="POST" class="grid grid-cols-6">
                <div class="col-span-4">
                    <c:if test="${action == 'update'}">
                        <div class="flex flex-col m-4">
                            <a href="./product?id=${updateProduct.productId}" class="text-blue-400 hover:text-blue-500 font-bold">Editing product #${updateProduct.productId}</a>
                        </div>
                    </c:if>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Name
                        </label>
                        <input
                            value="${updateProduct.name}"
                            type=text
                            name="name"
                            class="px-2 py-1 border rounded-md w-full"
                            minlength="1"
                            maxlength="150"
                            required
                            />
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Category
                        </label>
                        <select name="categoryId" class="px-2 py-1 border rounded-md" required>
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Price
                        </label>
                        <input
                            value="${updateProduct.price}"
                            type="number"
                            name="price"
                            class="px-2 py-1 border rounded-md w-full"
                            min="0"
                            required
                            />
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Amount
                        </label>
                        <input
                            value="${updateProduct.remain}"
                            type="number"
                            name="remain"
                            class="px-2 py-1 border rounded-md w-full"
                            min="0"
                            max="30000"
                            required
                            />
                    </div>

                    <div class="flex flex-col m-4">
                        <label class="mb-2 font-bold">
                            Images
                        </label>
                        <textarea name="images" class="p-2 border rounded-md h-[160px]"
                            required rows="30" cols="30">${images}</textarea>
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="mb-2 font-bold">
                            Short description
                        </label>
                        <textarea
                            name="shortDescription"
                            class="p-2 border rounded-md h-[160px]"
                            required
                            rows={10}>${updateProduct.shortDescription}</textarea>
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="mb-2 font-bold">
                            Description
                        </label>
                        <textarea
                        	id="descriptionTextArea"
                            name="description"
                            class="p-2 border rounded-md h-[200px]"
                            rows={10}
                            >${updateProduct.description}</textarea>
                    </div>
                </div>
                <div class="col-span-2">
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Publish day
                        </label>
                        <input
                            value="${updateProduct.publishDay}"
                            type="date"
                            name="publishDay"
                            class="px-2 py-1 border rounded-md w-[200px]"
                            required
                            />
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Manufacturer
                        </label>
                        <input
                            value="${updateProduct.manufacturer}"
                            type="text"
                            name="manufacturer"
                            class="px-2 py-1 border rounded-md w-[200px]"
                            maxlength="50"
                            required
                            />
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Publisher
                        </label>
                        <input
                            value="${updateProduct.publisher}"s
                            type="text"
                            name="publisher"
                            class="px-2 py-1 border rounded-md w-[200px]"
                            maxlength="50"
                            required
                            />
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Size
                        </label>
                        <input
                            value="${updateProduct.size}"
                            type="text"
                            name="size"
                            class="px-2 py-1 border rounded-md w-[200px]"
                            maxlength="30"
                            required
                            />
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Format
                        </label>
                        <select
                            name="format"
                            class="px-2 py-1 border rounded-md"
                            required
                            >
                            <option value="1">Bìa cứng</option>
                            <option value="2">Bìa mềm</option>
                        </select>
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="pb-2 font-bold">
                            Pages
                        </label>
                        <input
                            value="${updateProduct.pages}"
                            type="number"
                            name="pages"
                            class="px-2 py-1 border rounded-md w-[200px]"
                            min="0"
                            max="30000"
                            required
                            />
                    </div>
                                        <div class="flex flex-col m-4">
                        <label class="mb-2 font-bold">
                            Author 1 *
                        </label>
						<select name="author1" class="px-2 py-1 border rounded-md" required>
							<option disabled selected value>Select an option</option>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}" ${updateProduct.authors[0].name == author.name ? "selected" : ""}>[#${author.id}] ${author.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="mb-2 font-bold">
                            Author 2
                        </label>
						<select name="author2" class="px-2 py-1 border rounded-md">
                            <option disabled selected value>Can be blank</option>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}" ${updateProduct.authors[1].name == author.name ? "selected" : ""}>[#${author.id}] ${author.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="mb-2 font-bold">
                            Author 3
                        </label>
						<select name="author3" class="px-2 py-1 border rounded-md">
							<option disabled selected value>Can be blank</option>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}" ${updateProduct.authors[2].name == author.name ? "selected" : ""}>[#${author.id}] ${author.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="flex flex-col m-4">
                        <label class="mb-2 font-bold">
                            Author 4
                        </label>
						<select name="author4" class="px-2 py-1 border rounded-md">
							<option disabled selected value>Can be blank</option>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id} ${updateProduct.authors[3].name == author.name ? "selected" : ""}">[#${author.id}] ${author.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <input name="productId" value="${updateProduct.productId}" type="hidden"/>
                <button type="submit" class="font-bold p-2 text-white bg-black rounded-md ml-4 mb-4">
                    <c:if test="${action == 'update'}">
                        Save
                    </c:if>
                    <c:if test="${action == 'create'}">
                        Add
                    </c:if>
                </button>
            </form>
        </div>
    </div>
</div>
