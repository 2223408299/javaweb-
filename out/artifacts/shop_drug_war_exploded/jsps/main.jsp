<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>网站</title>
    <%--    <link href=${pageContext.request.contextPath}/jsps/css/menu.css">--%>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/iconfont.js"></script>
    <%--    <link rel="stylesheet" href="jsps/css/style.css">--%>
    <style type="text/css">
        b:hover {
            color: #ff0000;
        }

        .icon {
            width: 1em;
            height: 1em;
            vertical-align: -0.15em;
            fill: currentColor;
            overflow: hidden;
        }
    </style>
    <%--    <script type="text/javascript">--%>
    <%--        $(function () {--%>
    <%--            $.ajax({--%>
    <%--                type: "get",--%>
    <%--                dataType: "json",--%>
    <%--                data: {method: "findAll"},--%>
    <%--                url: "/shop_drug_war_exploded/CategoryServlet",--%>
    <%--                success: function (parents) {--%>
    <%--                    var parents = parents;--%>
    <%--                    for (var i = 0; i < parents.length; i++) {--%>
    <%--                        var cname = parents[i].cname;--%>
    <%--                        var yiji = $('<div class="box-item">\n' +--%>
    <%--                            '        <p class="title" align="center">${parents[i].cname}</p>\n' +--%>
    <%--                            '        <ul class="box-item-content">');--%>
    <%--                        $("#menu").append(yiji);--%>
    <%--                        for (var j = 0; j < parents[i].children.length; j++) {--%>
    <%--                            var cname2 = parents[i].children[j].cname;--%>
    <%--                            var cid = parents[i].children[j].cid;--%>
    <%--                            var erji = $('<li align="center"><a\n' +--%>
    <%--                                '  href="<c:url value='/DrugServlet?method=findByCategory&cid=${parents[i].children[j].cid}'/>"\n' +--%>
    <%--                                '  target="_parent" style="color:white">${parents[i].children[j].cname}</a></li>');--%>
    <%--                            $("#menu").append(erji);--%>
    <%--                        }--%>
    <%--                        var bq = $('</ul>\n' +--%>
    <%--                            '        </div>');--%>
    <%--                        $("#menu").append(bq);--%>
    <%--                    }--%>
    <%--                },--%>
    <%--                error: function (msg) {--%>
    <%--                    alert(123);--%>
    <%--                }--%>
    <%--            });--%>
    <%--        })--%>
    <%--    </script>--%>
</head>
<body>
<%--<div class="row">--%>
<%--    <c:choose>--%>
<%--        <c:when test="${empty sessionScope.sessionUser }">--%>
<%--            <nav class="navbar navbar-inverse">--%>
<%--                <div class="container-fluid">--%>
<%--                    <!-- Brand and toggle get grouped for better mobile display -->--%>
<%--                    <div class="navbar-header" style="margin-left: 200px;">--%>
<%--                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"--%>
<%--                                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">--%>
<%--                            <span class="sr-only">Toggle navigation</span>--%>
<%--                            <span class="icon-bar"></span>--%>
<%--                            <span class="icon-bar"></span>--%>
<%--                            <span class="icon-bar"></span>--%>
<%--                        </button>--%>
<%--                        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp" target="_parent"><b>首页</b></a>--%>
<%--                    </div>--%>

<%--                    <!-- Collect the nav links, forms, and other content for toggling -->--%>
<%--                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">--%>
<%--                        <ul class="nav navbar-nav">--%>
<%--                            <li><a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent"><b>登录</b></a></li>--%>
<%--                            <li><a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent"><b>注册</b></a></li>--%>
<%--                        </ul>--%>
<%--                        <form class="navbar-form navbar-right"--%>
<%--                              action="<c:url value='/DrugServlet?method=findByDrugname'/>"--%>
<%--                              method="post" target="body">--%>
<%--                            <div class="form-group" style="margin-right: 200px;">--%>
<%--                                <input type="text" class="form-control" placeholder="药品名称" name="drugname">--%>
<%--                                <button type="submit" class="btn btn-default"><b>搜索</b></button>--%>
<%--                            </div>--%>
<%--                        </form>--%>
<%--                    </div><!-- /.navbar-collapse -->--%>
<%--                </div><!-- /.container-fluid -->--%>
<%--            </nav>--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <nav class="navbar navbar-inverse">--%>
<%--                <div class="container-fluid">--%>
<%--                    <!-- Brand and toggle get grouped for better mobile display -->--%>
<%--                    <div class="navbar-header" style="margin-left: 200px;">--%>
<%--                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"--%>
<%--                                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">--%>
<%--                            <span class="sr-only">Toggle navigation</span>--%>
<%--                            <span class="icon-bar"></span>--%>
<%--                            <span class="icon-bar"></span>--%>
<%--                            <span class="icon-bar"></span>--%>
<%--                        </button>--%>
<%--                        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp" target="_parent"><b>首页</b></a>--%>
<%--                    </div>--%>

<%--                    <!-- Collect the nav links, forms, and other content for toggling -->--%>
<%--                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">--%>
<%--                        <ul class="nav navbar-nav">--%>
<%--                            <li style="margin-top: 7px;"><h5 style="color:white">--%>
<%--                                你好：${sessionScope.sessionUser.loginname }</h5></li>--%>
<%--                            <li><a href="<c:url value='/CartItemServlet?method=myCart'/>" target="_top"><b>购物车</b></a>--%>
<%--                            </li>--%>
<%--                            <li><a href="<c:url value='/OrderServlet?method=myOrders'/>" target="_top"><b>订单</b></a></li>--%>
<%--                            <li class="dropdown">--%>
<%--                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"--%>
<%--                                   aria-haspopup="true" aria-expanded="false"><b>个人中心</b><span class="caret"></span></a>--%>
<%--                                <ul class="dropdown-menu">--%>
<%--                                    <li><a href="<c:url value='/CartItemServlet?method=myCart'/>"--%>
<%--                                           ><b>我的购物车</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/OrderServlet?method=myOrders'/>"--%>
<%--                                           ><b>我的订单</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/jsps/user/pwd.jsp'/>"><b>修改密码</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/UserServlet?method=mypurse'/>"--%>
<%--                                           ><b>我的账号余额</b></a></li>--%>
<%--                                    <li><a href="<c:url value='/UserServlet?method=myaddress'/>"--%>
<%--                                           ><b>我的收货地址</b></a></li>--%>
<%--                                    <li role="separator" class="divider"></li>--%>
<%--                                    <li><a href="<c:url value='/UserServlet?method=quit'/>"--%>
<%--                                           ><b>退出</b></a></li>--%>
<%--                                </ul>--%>
<%--                            </li>--%>
<%--                        </ul>--%>
<%--                        <form class="navbar-form navbar-right"--%>
<%--                              action="<c:url value='/DrugServlet?method=findByDrugname'/>"--%>
<%--                              method="post" target="body">--%>
<%--                            <div class="form-group" style="margin-right: 200px;">--%>
<%--                                <input type="text" class="form-control" placeholder="Search" name="drugname">--%>
<%--                                <button type="submit" class="btn btn-default"><b>查找</b></button>--%>
<%--                            </div>--%>
<%--                        </form>--%>
<%--                    </div><!-- /.navbar-collapse -->--%>
<%--                </div><!-- /.container-fluid -->--%>
<%--            </nav>--%>
<%--        </c:otherwise>--%>
<%--    </c:choose>--%>
<%--</div>--%>
<jsp:include page="top.jsp"></jsp:include>
<div class="row">
    <div class="col-xs-4 col-xs-offset-1">
        <img src="${pageContext.request.contextPath}/images/logo1.png">
    </div>
    <div class="col-xs-4">
        <img src="${pageContext.request.contextPath}/images/logo2.png">
    </div>
</div>
<div class="container">
    <div class="row">
<%--        <div class="col-xs-3">--%>
            <iframe frameborder="0" src="<c:url value='/CategoryServlet?method=findAll'/>"
                    name="left" height="455px" width="30%" style="float:left;" scrolling="no"></iframe>
            <%--            <jsp:include page="left.jsp"></jsp:include>--%>
            <%--            <div class=top>--%>
            <%--                <p class="title" align="center" style="color:white">全部商品分类</p>--%>
            <%--            </div>--%>
            <%--            <div class="box" id="menu">--%>

            <%--            </div>--%>
<%--        </div>--%>
<%--        <div class="col-xs-9">--%>
                        <iframe frameborder="0" src="<c:url value='/DrugServlet?method=findHotDrug2'/>"
                                name="body" height="455px" width="70%" style="float:right;"></iframe>
<%--            <jsp:include page="body.jsp"></jsp:include>--%>
<%--        </div>--%>
        <%--        <jsp:forward page="CategoryServlet?method=findAll"></jsp:forward>--%>
        <%--                <jsp:include page="CategoryServlet?method=findAll"></jsp:include>--%>
    </div>
    <div class="row col-sm-offset-1" style="margin-top: 50px">
        <h1>热门商品</h1>
    </div>
    <div class="row" style="margin-top: 50px">
        <iframe frameborder="0" src="<c:url value='/DrugServlet?method=findHotDrug'/>"
                name="hotDrug" height="100%" width="100%" scrolling="no"></iframe>
    </div>
</div>
</body>
</html>
