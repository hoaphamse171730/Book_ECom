<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="flex items-center space-x-4 lg:space-x-6">
    <a href="./"><%@include file="Logo.jsp" %></a>
    <div class="h-full group relative">
        <div class="h-full p-[10px] text-sm font-medium transition-colors hover:underline">
            Category
        </div>
        <div class="z-10 absolute hidden group-hover:grid min-w-[1000px] border rounded-lg grid-cols-5 bg-white">
            <c:forEach items="${categories}" var="category">
                <div class="transition-all h-full group-2 relative bg-white hover:font-bold hover:text-white hover:bg-blue-500 first:rounded-tl-md last:rounded-b-md">
                    <a href="./search?category=${category.id}" class="bg-white">
                        <div class="h-full p-[10px] w-full text-sm">${category.name}</div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
