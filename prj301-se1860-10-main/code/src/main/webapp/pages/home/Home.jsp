<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../../components/Import.jsp" %>
        <title>Home</title>
    </head>
    <body>
        <%@include file="../../components/MainNav.jsp" %>
		<div class="w-full p-4">
			<%@include file="../../components/Banner.jsp" %>
			<%@include file="./content/ContentSection.jsp" %>
		</div>
		<%@include file="../../components/Footer.jsp" %>
		<%@include file="../../components/script/Script.jsp" %>
    </body>
</html>