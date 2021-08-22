<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/13
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>分类显示</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/drug/listcate.css'/>">

</head>
<body>
<div class="navbar">
    <div class="nav">
        <ul>
            <c:forEach items="${parents}" var="parent">
            <li>
                <a href="javascript:;">${parent.cname}</a>
                <ol>
                    <c:forEach items="${parent.children}" var="child">
                        <li align="center"><a href="<c:url value='/AdminDrugServlet?method=findByCategory&condition3=${condition3}&cid=${child.cid}'/>"
                                              >${child.cname}</a></li>
                    </c:forEach>
                </ol>
            </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
