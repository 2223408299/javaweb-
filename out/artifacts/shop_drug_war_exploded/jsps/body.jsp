<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>轮播图</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>

<body>
<c:set var="size2" value="${size }" scope="session"/>
<div id="carousel-example-generic" class="carousel slide" data-ride="carousel"
     data-interval="2000" align="center">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <c:forEach items="${drugList }" var="drug">
            <c:choose>
                <c:when test="${size eq size2}">
                    <c:set var="size2" value="${size-1 }" scope="session"/>
                    <li data-target="#carousel-example-generic" class="active"></li>
                </c:when>
                <c:otherwise>
                    <li data-target="#carousel-example-generic"></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <%--        			    <li data-target="#carousel-example-generic"  class="active"></li>--%>
        <%--        			    <li data-target="#carousel-example-generic" ></li>--%>
        <%--        				<li data-target="#carousel-example-generic" ></li>--%>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
        <c:set var="size2" value="${size }" scope="session"/>
        <c:forEach items="${drugList }" var="drug">
            <c:choose>
                <c:when test="${size eq size2}">
                    <c:set var="size2" value="${size-1 }" scope="session"/>
                    <div class="item active">
                        <a target="_parent" href="<c:url value='/DrugServlet?method=load&drugid=${drug.drugId }'/>"><img
                                src="<c:url value='/${drug.image_b }'/>" height="455px" width="455px"/></a>
                        <div class="carousel-caption">
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="item">
                        <a target="_parent" href="<c:url value='/DrugServlet?method=load&drugid=${drug.drugId }'/>"><img
                                src="<c:url value='/${drug.image_b }'/>" height="455px" width="455px"/></a>
                        <div class="carousel-caption">
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <%--        <div class="item">--%>
        <%--            <a href=""><img src="./images/lbt2.jpg"></a>--%>
        <%--            <div class="carousel-caption">--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <%--        <div class="item">--%>
        <%--            <a href=""><img src="./images/lbt3.jpg"></a>--%>
        <%--            <div class="carousel-caption">--%>
        <%--            </div>--%>
        <%--        </div>--%>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
</body>
</html>
