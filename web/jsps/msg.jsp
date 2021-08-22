<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>提示页面</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <style type="text/css">
        body {
            font-size: 10pt;
            color: #404040;
            font-family: SimSun;
        }

        .divTitle {
            text-align: left;
            width: 900px;
            height: 25px;
            line-height: 25px;
            background-color: #efeae5;
            border: 5px solid #efeae5;
        }

        .divContent {
            width: 900px;
            height: 230px;
            border: 5px solid #efeae5;
            margin-bottom: 20px;
        }

        .spanTitle {
            margin-top: 10px;
            height: 25px;
            font-weight: 900;
        }

        a {
            text-decoration: none;
        }

        a:visited {
            color: #018BD3;
        }

        a:hover {
            color: #FF6600;
            text-decoration: underline;
        }

        }
    </style>

</head>

<body>
<div class="row">
    <jsp:include page="top.jsp"></jsp:include>
</div>
<c:choose>
    <c:when test="${code eq 'success' }"><%--如果code是功能，它显示对号图片 --%>
        <c:set var="img" value="/images/duihao.jpg"/>
        <c:set var="title" value="成功"/>
    </c:when>
    <c:when test="${code eq 'error' }"><%--如果code是功能，它显示错号图片 --%>
        <c:set var="img" value="/images/cuohao.png"/>
        <c:set var="title" value="失败"/>
    </c:when>

</c:choose>
<div class="row" align="center" style="margin-top: 100px">
    <div class="divTitle">
        <span class="spanTitle">${title }</span>
    </div>
    <div class="divContent">
        <div style="margin: 20px;">
            <img style="float: left; margin-right: 30px;" src="<c:url value='${img }'/>" width="150"/>
            <span style="font-size: 30px; color: #c30; font-weight: 900;">${msg }</span>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style="margin-left: 50px;"><a target="_top" href="<c:url value='/jsps/user/login.jsp'/>">登录</a></span>
            <span style="margin-left: 50px;"><a target="_top" href="<c:url value='/index.jsp'/>">主页</a></span>
        </div>
    </div>
</div>


</body>
</html>