<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/10
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>药品分类管理</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <table class="table table-hover" style="background-color: #c9e2b3" border="6">
        <caption style="text-align: center">
            <a href="<c:url value='/adminjsps/admin/category/add.jsp'/>" >
                <button type="button" class="btn btn-primary">添加一级分类</button></a>
        </caption>
        <tr>
            <th>分类名称</th>
            <th>描述</th>
            <th>操作</th>
        </tr>

        <c:forEach items="${parents }" var="parent">
            <tr>
                <td width="20%">${parent.cname }</td>
                <td width="58.2%">${parent.categoryDesc }</td>
                <td width="21.8%">
                    <a href="<c:url value='/AdminCategoryServlet?method=addChildPre&pid=${parent.cid }'/>">
                        <button type="button" class="btn btn-primary">添加二级分类</button></a>
                    <a href="<c:url value='/AdminCategoryServlet?method=editParentPre&cid=${parent.cid }'/>">
                        <button type="button" class="btn btn-primary">修改</button></a>
                    <a onclick="return confirm('您是否真要删除该一级分类？')"
                       href="<c:url value='/AdminCategoryServlet?method=deleteParent&cid=${parent.cid }'/>">
                        <button type="button" class="btn btn-danger">删除</button></a>
                </td>
            </tr>
            <c:forEach items="${parent.children }" var="child">
                <tr style="text-align: right">
                    <td>${child.cname }</td>
                    <td>${child.categoryDesc }</td>
                    <td align="right">
                        <a href="<c:url value='/AdminCategoryServlet?method=editChildPre&cid=${child.cid }'/>">
                            <button type="button" class="btn btn-primary">修改</button></a>
                        <a onclick="return confirm('您是否真要删除该二级分类？')"
                           href="<c:url value='/AdminCategoryServlet?method=deleteChild&cid=${child.cid }'/>">
                            <button type="button" class="btn btn-danger">删除</button></a>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>

</body>
</html>