<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Gson gsonObj = new Gson();
    List<Map<Object, Object>> list = (List<Map<Object, Object>>) request.getAttribute("totalIncomeStatistic");
    List<Map<Object, Object>> listPie = (List<Map<Object, Object>>) request.getAttribute("totalIncomeByCategory");


    String dataPoints = gsonObj.toJson(list);
    String dataPoints2 = gsonObj.toJson(listPie);
%>

<script type="text/javascript">
    window.onload = function () {

        var chart = new CanvasJS.Chart("chartContainer1", {
            theme: "light2",
            title: {
                text: "Doanh thu hàng năm (VND)",
                fontSize: 18,
            },
            axisX: {
                title: "Năm"
            },
            axisY: {
                includeZero: true
            },
            data: [{
                    type: "line",
                    yValueFormatString: "#,##0 VND",
                    dataPoints: <%out.print(dataPoints);%>
                }]
        });
        chart.render();
        var chart2 = new CanvasJS.Chart("chartContainer2", {
            theme: "light2",
        	exportEnabled: true,
        	animationEnabled: true,
        	title: {
        		text: "Doanh thu theo danh mục",
        		fontSize: 18,
        	},
        	data: [{
        		type: "pie",
        		toolTipContent: "<b>{label}</b>: {y}%",
        		indexLabelFontSize: 14,
        		dataPoints: <%out.print(dataPoints2);%>
        	}]
        });
        chart2.render();

    }
</script>