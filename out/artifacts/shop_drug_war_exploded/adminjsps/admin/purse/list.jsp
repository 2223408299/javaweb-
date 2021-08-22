<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xlink" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="adminjsps/admin/js/purse/list.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
</head>
<body>

<div class="container">

    <div class="row" style="text-align: center">
        <h1>钱包管理</h1>
    </div>
    <div class="row">
        <c:choose>
        <c:when test="${empty pb.beanList }">
            <table style="margin-top: 200px;margin-left: 350px">
                <tr>
                    <td>
                        <h1>暂时没有符合条件的钱包管理!</h1>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <%--        <div style="margin-top: 50px" class="col-sm-1">--%>
            <%--            <a href="javascript:batchDelete();">--%>
            <%--                <button type="button" class="btn btn-danger">批量删除</button>--%>
            <%--            </a>--%>
            <%--        </div>--%>
        <div style="margin-top: 50px" class="col-sm-1">
            <c:if test="${condition eq 'uncheck' }">
                <a href="javascript:batchCheck();">
                    <button type="button" class="btn btn-danger">批量通过</button>
                </a>
            </c:if>
            <c:if test="${condition eq 'check' }">
                <a href="javascript:batchDelete();">
                    <button type="button" class="btn btn-danger">批量删除</button>
                </a>
            </c:if>
        </div>
        <div class="col-sm-4 col-md-offset-7" style="margin-top: 43px" s>
            <form class="navbar-form navbar-left"
                  action="<c:url value='/PurseServlet?method=findPurseByUid&condition=${condition }'/>"
                  method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户ID" name="uid">
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
                <th style="text-align: center ">充值申请Id</th>
                <th style="text-align: center ">用户Id</th>
                <th style="text-align: center ">充值金额</th>
                <th style="text-align: center ">审核状态</th>
                <th style="text-align: center ">操作</th>
            </tr>
            <c:forEach items="${pb.beanList }" var="purse">
                <tr>
                    <td width="10%" style="vertical-align: middle">
                        <input value="${purse.purseId }" type="checkbox" name="checkboxBtn" checked="checked"/>
                    </td>
                    <td width="20%" style="vertical-align: middle">${purse.purseId}</td>
                    <td width="20%" style="vertical-align: middle">${purse.uid}</td>
                    <td width="15%" style="vertical-align: middle">${purse.rechange}</td>
                    <td width="15%" style="vertical-align: middle">
                        <c:if test="${purse.state eq 'true' }">
                            已审核
                        </c:if>
                        <c:if test="${purse.state eq 'false' }">
                            未审核
                        </c:if>
                    </td>
                    <td width="20%" style="vertical-align: middle">
                        <c:if test="${condition eq 'uncheck' }">
                            <a onclick="return confirm('您是否真要通过此充值申请？')"
                               href="<c:url value='/PurseServlet?method=updateState&purseId=${purse.purseId }'/>">
                                <button type="button" class="btn btn-danger">通过</button>
                            </a>
                        </c:if>
                        <c:if test="${condition eq 'check' }">
                            <a onclick="return confirm('您是否真要删除此充值申请？')"
                               href="<c:url value='/PurseServlet?method=delete&purseId=${purse.purseId }'/>">
                                <button type="button" class="btn btn-danger">删除</button>
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
