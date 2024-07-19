<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TBSD | Login</title>
        <%@include file="../../components/Import.jsp" %>
    </head>
    <body>
        <div class="flex items-center min-h-screen p-4 bg-gray-100 lg:justify-center">
            <div class="flex flex-col overflow-hidden bg-white rounded-md shadow-lg max md:flex-row md:flex-1 lg:max-w-screen-md">
                <div class="p-12 bg-white md:flex-1">
                    <div class="flex justify-center items-center gap-4">
                        <%@include file="../../components/Logo.jsp" %>
                    </div>
                    <form action="./login" method="POST" class="flex flex-col space-y-5">
                        <%@include file="../../components/form/EmailInput.jsp" %>
                        <%@include file="../../components/form/PasswordInput.jsp" %>
                        <p class="text-red-400">
                            <%
                                String error = (String) request.getAttribute("error");
                                if (error != null) {
                                    out.println(error);
                                }
                            %>
                        </p>
                        <div class="flex items-center space-x-2"></div>
                        <div>
                            <button
                                type="submit"
                                class="w-full px-4 py-2 text-lg font-semibold text-white transition-colors duration-300 bg-blue-500 rounded-md shadow hover:bg-blue-600 focus:outline-none focus:ring-blue-200 focus:ring-4"
                                >
                                Log in
                            </button>
                        </div>
                        <div>
                            <p class="text-slate-500 mr-4 inline-block">
                                Don't have an account?
                            </p>
                            <a href="./register" class="text-sm text-blue-600 hover:underline focus:text-blue-800 mt-1">Register</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
