<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xlink" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="adminjsps/admin/js/user/list.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
</head>
<body>

<div class="container">

    <div class="row" style="text-align: center">
        <h1>用户管理</h1>
    </div>
    <div class="row">
        <c:choose>
        <c:when test="${empty pb.beanList }">
            <table style="margin-top: 200px;margin-left: 350px">
                <tr>
                    <td>
                        <h1>暂时没有符合条件的用户!</h1>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
        <div style="margin-top: 50px" class="col-sm-1">
            <a href="javascript:batchDelete();">
                <button type="button" class="btn btn-danger">批量删除</button>
            </a>
        </div>
        <div style="margin-top: 50px" class="col-sm-1">
            <c:if test="${condition eq 'normal' }">
                <a href="javascript:batchLock();">
                    <button type="button" class="btn btn-danger">批量封号</button>
                </a>
            </c:if>
            <c:if test="${condition eq 'improper' }">
                <a href="javascript:batchUnlock();">
                    <button type="button" class="btn btn-danger">批量解封</button>
                </a>
            </c:if>
        </div>
        <div style="margin-top: 50px" class="col-sm-1">
            <a href="<c:url value='/adminjsps/admin/user/add.jsp'/>">
                <button type="button" class="btn btn-primary">添加用户</button>
            </a>
        </div>
        <div class="col-sm-4 col-sm-offset-5" style="margin-top: 43px" s>
            <form class="navbar-form navbar-left"
                  action="<c:url value='/AdminUserServlet?method=findByUsername&condition=${condition}'/>"
                  method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户名称" name="loginname">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>

    <div class="row" style="margin-top: 10px">
        <table class="table table-hover" style="text-align: center;" border="6">
            <tr>
                <th style="text-align: center ">
                    <input type="checkbox" id="selectAll" checked="checked"/><label
                        for="selectAll">全选</label>
                </th>
                <th style="text-align: center ">用户Id</th>
                <th style="text-align: center ">用户名</th>
                <th style="text-align: center ">用户密码</th>
                <th style="text-align: center ">绑定Email</th>
                <th style="text-align: center ">是否激活</th>
                <th style="text-align: center ">用户状态</th>
                <th style="text-align: center ">操作</th>
            </tr>
            <c:forEach items="${pb.beanList }" var="user">
                <tr>
                    <td width="8%" style="vertical-align: middle">
                        <input value="${user.uid }" type="checkbox" name="checkboxBtn" checked="checked"/>
                    </td>
                    <td width="15%" style="vertical-align: middle">${user.uid}</td>
                    <td width="12%" style="vertical-align: middle">${user.loginname}</td>
                    <td width="10%" style="vertical-align: middle">${user.loginpass}</td>
                    <td width="12%" style="vertical-align: middle">${user.email}</td>
                    <td width="9%" style="vertical-align: middle">
                        <c:if test="${user.status eq 'true' }">
                            已激活
                        </c:if>
                        <c:if test="${user.status eq 'false' }">
                            未激活
                        </c:if>
                    </td>
                    <td width="10%" style="vertical-align: middle">
                        <c:if test="${user.state eq 'true' }">
                            正常
                        </c:if>
                        <c:if test="${user.state eq 'false' }">
                            异常
                        </c:if>
                    </td>
                    <td width="24%" style="vertical-align: middle">
                        <a href="<c:url value='/AdminUserServlet?method=load&uid=${user.uid }'/>">
                            <button type="button" class="btn btn-primary">修改</button>
                        </a>
                        <a onclick="return confirm('您是否真要删除该用户？')"
                           href="<c:url value='/AdminUserServlet?method=delete&uid=${user.uid }&condition=${condition}'/>">
                            <button type="button" class="btn btn-danger">删除</button>
                        </a>
                        <c:if test="${condition eq 'normal' }">
                            <a onclick="return confirm('您是否真要封此号？')"
                               href="<c:url value='/AdminUserServlet?method=updateState&uid=${user.uid }&condition=${condition}'/>">
                                <button type="button" class="btn btn-danger">封号</button>
                            </a>
                        </c:if>
                        <c:if test="${condition eq 'improper' }">
                            <a onclick="return confirm('您是否真要解封此号？')"
                               href="<c:url value='/AdminUserServlet?method=updateState&uid=${user.uid }&condition=${condition}'/>">
                                <button type="button" class="btn btn-danger">解封</button>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div style="float:left; width: 100%; text-align: center;">
    <hr/>
    <br/>
    <%@include file="/jsps/pager/pager.jsp" %>
</div>
</c:otherwise>
</c:choose>
</body>
</html>
