<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/10
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>提示信息</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<div class="col-lg-offset-4 col-sm-5" style="margin-top: 250px;text-align: center">
    <h2 style="color: red">${msg }</h2>
    <ul>
        <c:forEach items="${links }" var="link">
            <li>${link }</li>
        </c:forEach>
    </ul>
    <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
</div>
</body>
</html>
