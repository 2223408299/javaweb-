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
        <div class="brand">
            <a href="main.jsp"><img src="img/logo-dark.png" alt="找不到图片!!!" class="img-responsive logo"></a>
        </div>
        <div class="container-fluid">
            <div class="navbar-btn col-sm-1" style="padding: 0; padding-top: 10px;">
                <button type="button" class="btn-toggle-fullwidth btn-toggle-mx"><img src="img/left.png" height="40px"
                                                                                      alt="找不到图片!!!"></button>
            </div>
            <div class="col-sm-2" style="">
                <h2 style="color: white">管理员：${sessionScope.admin.adminname }</h2>
            </div>
            <div class="col-sm-3" style="margin-left: 40px">
                <h2 style="color: white">网上药店后台管理</h2>
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
                    <li>
                        <a href="<c:url value='/AdminCategoryServlet?method=findAll'/>" target="_blank"
                           class="iframe_link active"><span>分类管理</span></a>
                    </li>
                    <li><a href="javascript:;" class="nav-togg"><span>用户管理</span></a>
                        <div>
                            <ul>
                                <li><a href="<c:url value='/AdminUserServlet?method=findAllUser&condition=normal'/>"
                                       target="_blank"
                                       class="iframe_link"><span>正常用户</span></a></li>
                                <li><a href="<c:url value='/AdminUserServlet?method=findAllUser&condition=improper'/>"
                                       target="_blank"
                                       class="iframe_link"><span>非正常用户</span></a></li>
                            </ul>
                        </div>
                    </li>
                    <li><a href="javascript:;" class="nav-togg"><span>钱包管理</span></a>
                        <div>
                            <ul>
                                <li><a href="<c:url value='/PurseServlet?method=findAllPurse&condition=check'/>"
                                       target="_blank"
                                       class="iframe_link"><span>已审核</span></a></li>
                                <li><a href="<c:url value='/PurseServlet?method=findAllPurse&condition=uncheck'/>"
                                       target="_blank"
                                       class="iframe_link"><span>未审核</span></a></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a href="javascript:;" class="nav-togg"> <span>药品管理</span> </a>
                        <div>
                            <ul>
                                <li><a href="<c:url value='/AdminDrugServlet?method=findAllDrug&condition3=putaway'/>"
                                       target="_blank"
                                       class="iframe_link"><span>已上架</span></a></li>
                                <li><a href="<c:url value='/AdminDrugServlet?method=findAllDrug&condition3=soldout'/>"
                                       target="_blank"
                                       class="iframe_link"><span>已下架</span></a></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a href="<c:url value='/AdminOrderServlet?method=findAllOrder'/>" target="_blank"
                           class="iframe_link"><span>订单管理</span></a>
                    </li>
                    <li>
                        <a href="javascript:;" class="nav-togg"> <span>评价管理</span> </a>
                        <div>
                            <ul>
                                <li><a href="<c:url value='/CommentServlet?method=findCommentBystate&state=1'/>"
                                       target="_blank"
                                       class="iframe_link"><span>已回复</span></a></li>
                                <li><a href="<c:url value='/CommentServlet?method=findCommentBystate&state=0'/>"
                                       target="_blank"
                                       class="iframe_link"><span>未回复</span></a></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a href="<c:url value='/AdminServlet?method=quit'/>" target="_parent">退出</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
    <div class="main">
        <div class="main-content" style="height: 100%;">
            <iframe src="body.jsp" class="iframe_mx uicss-cn"></iframe>
        </div>
    </div>
</div>

</body>
</html>
