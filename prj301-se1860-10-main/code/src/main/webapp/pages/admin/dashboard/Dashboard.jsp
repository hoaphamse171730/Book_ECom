<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
    <head>
        <%@include file="../../../components/Import.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="ChartImport.jsp" %>
    </head>
    <body>
        <div class="flex">
            <%@include file="../SideNav.jsp" %>
            <%@include file="DashboardContent.jsp" %>
        </div>
        <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
        <%@include file="../../../components/script/Script.jsp" %>
    </body>
</html>