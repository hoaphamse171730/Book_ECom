<%@ page import="models.user.Role" %>
<%@ page import="models.user.UserDTO" %>
<div class="border-r-2 border h-screen w-[260px]">
    <div class="flex justify-center items-center h-[80px] border-b">
    	<a href="./">
	        <%@include file="../../components/Logo.jsp" %>
    	</a>
    </div>
    <div class="mt-2">
        <a href="./admin?tab=Dashboard" class="block p-2 mx-2 font-bold text-sm rounded-md text-slate-400 hover:bg-red-400 hover:text-black">
            <div class="inline-block px-2">
                <div>
                    <i class="fa-solid fa-house"></i>
                    <div class="inline-block mx-2">Dashboard</div>
                </div>
            </div>
        </a>
        <a href="./admin?tab=ProductManager" class="block p-2 mx-2 font-bold text-sm rounded-md text-slate-400 hover:bg-red-400 hover:text-black">
            <div class="inline-block px-2">
                <div>
                    <i class="fa-solid fa-cart-shopping"></i>
                    <div class="inline-block mx-2">Products</div>
                </div>
            </div>
        </a>
        <a href="./admin?tab=OrderManager" class="block p-2 mx-2 font-bold text-sm rounded-md text-slate-400 hover:bg-red-400 hover:text-black">
            <div class="inline-block px-2">
                <div>
                    <i class="fa-solid fa-box"></i>
                    <div class="inline-block mx-2">Orders</div>
                </div>
            </div>
        </a>
        <% if (((UserDTO) request.getAttribute("user")).getRole().equals(Role.ADMIN)) { %>
        <a href="./admin?tab=CustomerManager" class="block p-2 mx-2 font-bold text-sm rounded-md text-slate-400 hover:bg-red-400 hover:text-black">
            <div class="inline-block px-2">
                <div>
                    <i class="fa-solid fa-user"></i>
                    <div class="inline-block mx-2">Users</div>
                </div>
            </div>
        </a>
        <% } %>
        <a href="./admin?tab=CategoryManager" class="block p-2 mx-2 font-bold text-sm rounded-md text-slate-400 hover:bg-red-400 hover:text-black">
            <div class="inline-block px-2">
                <div>
                    <i class="fa-solid fa-list"></i>
                    <div class="inline-block mx-2">Categories</div>
                </div>
            </div>
        </a>
        <a href="./admin?tab=AuthorManager" class="block p-2 mx-2 font-bold text-sm rounded-md text-slate-400 hover:bg-red-400 hover:text-black">
            <div class="inline-block px-2">
                <div>
                    <i class="fa-solid fa-pen"></i>
                    <div class="inline-block mx-2">Authors</div>
                </div>
            </div>
        </a>
    </div>
</div>
