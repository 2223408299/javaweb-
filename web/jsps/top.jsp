<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>网站首页顶部</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<div class="row">
    <c:choose>
        <c:when test="${empty sessionScope.sessionUser }">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header" style="margin-left: 200px;">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp" target="_parent"><b>首页</b></a>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li><a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent"><b>登录</b></a></li>
                            <li><a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent"><b>注册</b></a></li>
                        </ul>
                        <form class="navbar-form navbar-right"
                              action="<c:url value='/DrugServlet?method=findByDrugname'/>"
                              method="post" target="_parent">
                            <div class="form-group" style="margin-right: 200px;">
                                <input type="text" class="form-control" placeholder="药品名称" name="drugname">
                                <button type="submit" class="btn btn-default"><b>搜索</b></button>
                            </div>
                        </form>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>
        </c:when>
        <c:otherwise>
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header" style="margin-left: 200px;">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp"><b>首页</b></a>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li style="margin-top: 7px;"><h5 style="color:white">
                                你好：${sessionScope.sessionUser.loginname }</h5></li>
                            <li><a href="<c:url value='/CartItemServlet?method=myCart'/>" target="_parent"><b>购物车</b></a>
                            </li>
                            <li><a href="<c:url value='/OrderServlet?method=myOrders'/>" target="_parent"><b>订单</b></a></li>
                            <li><a href="<c:url value='/UserServlet?method=myinformation'/>" target="_parent"><b>个人中心</b></a></li>
                            <li><a href="<c:url value='/UserServlet?method=quit'/>" target="_parent"><b>退出</b></a></li>
<%--                            <li class="dropdown">--%>
<%--                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"--%>
<%--                                   aria-haspopup="true" aria-expanded="false"><b>个人中心</b><span class="caret"></span></a>--%>
<%--                                <ul class="dropdown-menu">--%>
<%--                                    <li><a href="<c:url value='/CartItemServlet?method=myCart'/>"--%>
<%--                                           target="body"><b>我的购物车</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/OrderServlet?method=myOrders'/>"--%>
<%--                                           target="body"><b>我的订单</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/jsps/user/pwd.jsp'/>"><b>修改密码</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/UserServlet?method=mypurse'/>"--%>
<%--                                           target="body"><b>我的账号余额</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/UserServlet?method=myaddress'/>"--%>
<%--                                           target="body"><b>我的收货地址</b></a></li>--%>
<%--                                    <li role="separator" class="divider"></li>--%>
<%--                                    <li><a href="<c:url value='/UserServlet?method=quit'/>"--%>
<%--                                           target="_parent"><b>退出</b></a></li>--%>
<%--                                </ul>--%>
<%--                            </li>--%>
                        </ul>
                        <form class="navbar-form navbar-right"
                              action="<c:url value='/DrugServlet?method=findByDrugname'/>"
                              method="post" target="_parent">
                            <div class="form-group" style="margin-right: 200px;">
                                <input type="text" class="form-control" placeholder="Search" name="drugname">
                                <button type="submit" class="btn btn-default"><b>搜索</b></button>
                            </div>
                        </form>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
