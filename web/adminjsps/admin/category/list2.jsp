<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/11
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2 style="text-align: center;">分类列表</h2>
<table align="center" border="1" cellpadding="0" cellspacing="0">
    <caption class="captionAddOneLevel">
        <a href="<c:url value=''/>">添加一级分类</a>
    </caption>
    <tr class="trTitle">
        <th>分类名称</th>
        <th>描述</th>
        <th>操作</th>
    </tr>

    <c:forEach items="${parents }" var="parent">
        <tr class="trOneLevel">
            <td width="200px;">${parent.cname }</td>
            <td>${parent.desc }</td>
            <td width="200px;">
                <a href="<c:url value='/AdminCategoryServlet?method=addChildPre&pid=${parent.cid }'/>">添加二级分类</a>
                <a href="<c:url value='/AdminCategoryServlet?method=editParentPre&cid=${parent.cid }'/>">修改</a>
                <a onclick="return confirm('您是否真要删除该一级分类？')"
                   href="<c:url value='/AdminCategoryServlet?method=deleteParent&cid=${parent.cid }'/>">删除</a>
            </td>
        </tr>
        <c:forEach items="${parent.children }" var="child">
            <tr class="trTwoLevel">
                <td>${child.cname }</td>
                <td>${child.desc }</td>
                <td width="200px;" align="right">
                    <a href="<c:url value='/AdminCategoryServlet?method=editChildPre&cid=${child.cid }'/>">修改</a>
                    <a onclick="return confirm('您是否真要删除该二级分类？')"
                       href="<c:url value='/AdminCategoryServlet?method=deleteChild&cid=${child.cid }'/>">删除</a>
                </td>
            </tr>
        </c:forEach>
    </c:forEach>


</table>
</body>
</html>