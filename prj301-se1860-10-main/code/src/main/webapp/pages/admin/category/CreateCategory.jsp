<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div
	class="block w-full h-screen px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
	<div class="flex justify-between mt-4 mb-2">
        <%@include file="../../../components/NavAvatar.jsp" %>
	</div>
	<!--<DashboardTopNav/>-->
	<div class="flex mt-6">
		<a href="./admin?tab=CategoryManager"
			class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer">
			<i class="fa-solid fa-arrow-left"></i>
		</a>
	</div>
	<div
		class="block h-screen w-full overflow-x-hidden bg-[#F4F7FE] rounded-md mt-4">
		<div class="w-full bg-white border rounded-md">
			<form action="./admin?tab=CategoryManager&action=${action}"
				method="POST">
				<c:if test="${action == 'update'}">
					<div class="font-bold mt-4 ml-4">Editing category #${categoryUpdate.id}</div>
				</c:if>
				<div class="flex items-end">
					<div class="flex flex-col m-4">
						<label class="pb-2 font-bold">Category name</label> <input
							value="${categoryUpdate.name}" type=text name="name"
							class="px-2 py-1 border rounded-md w-[500px]" minlength="1"
							maxlength="150" required />
							<input
							value="${categoryUpdate.id}" type=hidden name="categoryId" minlength="1"
							maxlength="150" required />
					</div>
					<button type="submit"
						class="h-[40px] font-bold px-4 text-white bg-black rounded-md ml-4 mb-4">
						<c:if test="${action == 'update'}">
                        Save
                    </c:if>
						<c:if test="${action == 'create'}">
                        Add
                    </c:if>
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
