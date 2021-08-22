<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/12
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>管理员后台首页</title>
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/klorofil-common.js"></script>
</head>

<!-- 闲鱼资源网：精品资源分享网www.xianyuboke.com -->
<body>
<div id="loading">
    <div></div>
    <div></div>
    <span></span>
</div>
<!-- WRAPPER -->
<div id="wrapper">
    <!-- NAVBAR -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="brand"><a href="main.jsp"><img src="img/logo-dark.png" alt="Klorofil Logo"
                                                   class="img-responsive logo"></a></div>
        <div class="container-fluid">
            <div class="navbar-btn col-sm-1" style="padding: 0; padding-top: 10px;">
                <button type="button" class="btn-toggle-fullwidth btn-toggle-mx"><img src="img/left.png" height="40px"
                                                                                      alt=""></button>
            </div>
            <div class="col-sm-2" style="margin-top: 20px">
                <h4 style="color: white">管理员：${sessionScope.admin.adminname }</h4>
            </div>
        </div>
    </nav>
    <!-- END NAVBAR -->
    <!--_________________________________________________________________________________________-->
    <!-- LEFT SIDEBAR -->
    <div id="sidebar-nav" class="sidebar">
        <div class="sidebar-scroll">
            <nav>
                <ul class="nav">
                    <li><a href="<c:url value='/AdminCategoryServlet?method=findAll'/>" target="body"
                           class="iframe_link active"><span>分类管理</span></a></li>
                    <li><a href="link/2/index.html" target="_blank" class="iframe_link"><span>层次选择器</span></a></li>
                    <li>
                        <a href="javascript:;" class="nav-togg"> <span>药品管理</span> </a>
                        <div>
                            <ul>
                                <li><a href="link/2/index.html" target="_blank" class="iframe_link"><span>打包</span></a></li>
                                <li><a href="link/2/index.html" target="_blank" class="iframe_link"><span>啪啪</span></a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
    <div class="main">
        <div class="main-content" style="height: 100%;">
            <iframe src="link/1/index.html" class="iframe_mx uicss-cn"></iframe>
        </div>
    </div>
</div>

</body>
</html>

