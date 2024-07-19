<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../../components/Import.jsp" %>
        <title>Profile</title>
    </head>
    <body>
    	<%@include file="../../components/MainNav.jsp" %>
        
        <div class="mx-4 my-4 grid grid-cols-12">
			<div class="col-span-12">
		        <div class="w-full bg-white border p-4 rounded-md">
		        	<div class="font-bold text-lg pl-4">User Profile</div>
			        <% if (request.getAttribute("updateSuccess") != null) { %>
			            <p style="color: green;"><%= request.getAttribute("updateSuccess") %></p>
			        <% } %>
			
			        <% if (request.getAttribute("updateError") != null) { %>
			            <p style="color: red;"><%= request.getAttribute("updateError") %></p>
			        <% } %>
					<form action="/profile" method="post">
			        	<div class="flex flex-col m-4">
	                        <label class="pb-2 font-bold">Full Name</label>
	                        <input
	                            value="${user.name}"
	                            type=text
	                            name="fullName"
	                            class="px-2 py-1 border rounded-md w-full"
	                            minlength="1"
	                            maxlength="150"
	                            required
	                            />
	                    </div>
			        	<div class="flex flex-col m-4">
	                        <label class="pb-2 font-bold">Password</label>
	                        <input
	                            value=""
	                            type="password"
	                            name="password"
	                            class="px-2 py-1 border rounded-md w-full"
	                            placeholder="Change password"
	                            minlength="1"
	                            maxlength="150"
	                            />
	                    </div>
			        	<div class="flex flex-col m-4">
	                        <label class="pb-2 font-bold">Confirm Password</label>
	                        <input
	                            value=""
	                            type="password"
	                            name="confirmPassword"
	                            class="px-2 py-1 border rounded-md w-full"
	                            placeholder="Confirm new password"
	                            minlength="1"
	                            maxlength="150"
	                            />
	                    </div>
						<div class="flex flex-col m-4">
	                        <label class="pb-2 font-bold">Gender</label>
	                        <select name="gender" class="px-2 py-1 border rounded-md" required>
					        	<option value="MALE" ${user.gender.toString() == 'MALE' ? 'selected' : ''}>Male</option>
					        	<option value="FEMALE" ${user.gender.toString() == 'FEMALE' ? 'selected' : ''}>Female</option>
	                        </select>
	                    </div>
			        	<div class="flex flex-col m-4">
	                        <label class="pb-2 font-bold">Address</label>
	                        <input
	                            value="${user.address}"
	                            type="text"
	                            name="address"
	                            class="px-2 py-1 border rounded-md w-full"
	                            placeholder="Confirm new password"
	                            minlength="1"
	                            maxlength="150"
	                            required
	                            />
	                    </div>
			        	<div class="flex flex-col m-4">
	                        <label class="pb-2 font-bold">Phone Number</label>
	                        <input
	                            value="${user.phoneNumber}"
	                            type="text"
	                            name="phoneNumber"
	                            class="px-2 py-1 border rounded-md w-full"
	                            placeholder="Confirm new password"
	                            minlength="1"
	                            maxlength="150"
	                            required
	                            />
	                    </div>
					    <button type="submit" value="" class="ml-4 rounded-md px-4 py-2 font-bold text-white bg-blue-600">Save</button>
					</form>
		        	<br/>
					<a href="/" class="ml-4 no-underline px-4 py-2 font-bold bg-blue-600 text-white rounded-md">Back to Home</a>
					<div class="col-span-7 m-4">
	        			<div class="text-xl font-bold"></div>
						</div>
					<div class="m-4 col-span-12">
					</div>
				</div>
	        </div>
	        <div class="col-span-3"></div>
		</div>
        <%@include file="../../components/script/Script.jsp" %>
    </body>
</html>





