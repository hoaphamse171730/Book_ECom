<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="block w-full h-screen px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
    <div class="flex justify-between mt-4 mb-2">
        <%@include file="../../../components/NavAvatar.jsp" %>
    </div>
    <!--<DashboardTopNav/>-->
    <div class="flex justify-between mt-6">
        <div class="text-xl font-bold">Author</div>
        <a href="./admin?tab=AuthorManager&action=create" class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer">
            Add author
        </a>
    </div>
    <div class="w-full my-4 bg-white">
        <div>
            <!--<ProductHeader />-->
            <div class="grid grid-cols-12 p-4 font-bold border-b rounded-md">
                <div class="col-span-1">ID</div>
                <div class="col-span-3">Name</div>
                <div class="col-span-2">Function</div>
            </div>
            <!--<ProductItem />-->
            <c:forEach items="${authors}" var="author">
                <div class="grid items-center grid-cols-12 p-4 border-b">
                    <div class="col-span-1"># ${author.id}</div>
                    <div class="col-span-3">${author.name}</div>
                    <div class="flex col-span-2 gap-4">
                        <a
                            href="./admin?tab=AuthorManager&action=update&authorId=${author.id}"
                            class="px-3 py-2 font-bold text-white bg-blue-500 rounded-md"
                            >
                            Edit
                        </a>
                        <!--
                        <a
                            href="./admin?tab=AuthorManager&action=delete&authorId=${author.id}"
                            class="px-3 py-2 font-bold text-white bg-red-500 rounded-md"
                            >
                            Delete
                        </a>
                         -->
                    </div>
                </div>
            </c:forEach>

            <div class="p-2">
                <%@include file="../../../components/Pagination.jsp" %>
            </div>
        </div>
    </div>
</div>
