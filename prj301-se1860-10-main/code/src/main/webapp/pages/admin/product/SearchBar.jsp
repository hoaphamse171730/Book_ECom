<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="flex">
	<form action="./admin" method="GET"
		class="flex rounded-md overflow-hidden w-[550px]">
		<input type="hidden" name="tab" value="ProductManager"/>
		<button
			class=" text-black border border-r-0 opacity-50 px-3 text-lg font-semibold py-1 rounded-l-md"
			type="submit">
			<i class="fa-solid fa-magnifying-glass"></i>
		</button>
		<input type="text" placeholder="Search..." name="keyword"
			class="w-full rounded-md rounded-l-none border px-4 outline-none" />
		<select name="search" placeholder="Tìm theo" class="pl-4 border rounded-r-md -translate-x-4 z-10" required>
			<option value="NAME">Tên</option>
			<option value="SHORT_DESCRIPTION">Mô Tả</option>
			<option value="MANUFACTURER">Nhà Xuất Bản</option>
			<option value="PUBLISHER">Nhà Phát Hành</option>
		</select>

	</form>
</div>