<div class="relative inline-block text-left">
    <div>
        <!-- Avatar Image -->
        <button type="button" class="flex items-center focus:outline-none" onclick="toggleDropdown()">
            <%@include file="Avatar.jsp" %>
            <!--<img class="h-8 w-8 rounded-full" src="https://placekitten.com/100/100" alt="Avatar">-->
        </button>
    </div>

    <!-- Dropdown Menu -->
    <div id="dropdown-menu" class="z-10 hidden origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5">
        <div class="">
            <!-- Profile Button -->
            <a href="./profile" class="bg-white block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Profile</a>

            <!-- Settings Button -->
            <a href="./order" class="bg-white block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">My Order</button>
            
            <!-- Admin Page -->
            <c:if test="${sessionScope.usersession.role != 'USER'}">
	            <a href="./admin" class="bg-white block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Admin Dashboard</button>
            </c:if>

            <!-- Logout Button -->
            <a href="./logout" class="bg-white rounded-b-md block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Logout</a>
        </div>
    </div>
</div>