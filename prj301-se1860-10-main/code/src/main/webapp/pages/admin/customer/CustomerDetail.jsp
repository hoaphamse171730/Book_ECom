<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div
	class="block w-full h-screen px-4 overflow-y-scroll overflow-x-hidden bg-[#F4F7FE] rounded-md">
	<div class="flex justify-between mt-4 mb-2">
        <%@include file="../../../components/NavAvatar.jsp" %>
	</div>
	<!--<DashboardTopNav/>-->
	<div class="flex mt-6">
		<a href="./admin?tab=CustomerManager"
			class="bg-black text-white font-bold justify-center items-center rounded-xl text-sm py-2 px-4 inline-block cursor-pointer">
			<i class="fa-solid fa-arrow-left"></i>
		</a>
	</div>
	<div
		class="block h-screen w-full overflow-x-hidden bg-[#F4F7FE] rounded-md mt-4">
		<div class="w-full bg-white border rounded-md">
			<div class="col-span-4 mb-8">
				<div class="flex m-4 gap-8">
					<div class="font-bold">User #${customer.id}</div>
					<div class="">Created at: ${customer.createdAt}</div>
				</div>
				<div class="flex ml-4">
					<div>
						<div class="h-[140px] bg-slate-300 aspect-[3/4]"
							style="background-image: url('assets/img/User-avatar.png'); background-size: contain; background-position-y: 70%;background-repeat: no-repeat;"></div>
					</div>

					<div class="flex flex-col gap-4 ml-8">
						<div class="flex gap-8 items-center">
							<div class="inline-block">
								<span class="font-bold">Name: </span> ${customer.name}
							</div>
							<div class="inline-block">
								<span class="font-bold">Gender: </span> ${customer.gender}
							</div>
							<form action="./admin" method="POST">
								<input type="hidden" name="action" value="update" />
								<input type="hidden" name="tab" value="CustomerManager" />
								<input type="hidden" name="id" value="${customer.id}" />
								<span class="font-bold">Role: </span>
								<select name="role" id="status" class="px-2 py-1 border rounded-md" required>
										<option value="ADMIN"
											${customer.role == "ADMIN" ? "selected" : ""}>ADMIN</option>
										<option value="USER"
											${customer.role == "USER" ? "selected" : ""}>USER</option>
										<option value="STAFF"
											${customer.role == "STAFF" ? "selected" : ""}>STAFF</option>
								</select>
								<button type="submit" class="ml-4 font-bold px-4 py-2 text-white bg-black rounded-md">
									Save
								</button>
							</form>
						</div>
						<div class="flex gap-8">
							<div class="inline-block">
								<span class="font-bold">Email: </span> ${customer.email}
							</div>
							<div class="inline-block">
								<span class="font-bold">Phone number: </span>
								${customer.phoneNumber}
							</div>
						</div>
						<div class="">
							<span class="font-bold">Address: </span> ${customer.address}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>