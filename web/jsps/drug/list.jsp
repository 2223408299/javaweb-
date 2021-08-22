<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>查找药品展示</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/drug/list.css'/>">
    <script type="text/javascript" src="<c:url value='/jsps/js/drug/list.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
    <style type="text/css">
        b:hover {
            color: #ff0000;
        }

        /*b{*/
        /*    font-size: medium;*/
        /*}*/
    </style>
</head>
<%
    String condition = (String) request.getAttribute("condition");
    session.setAttribute("condition", condition);
%>
<body>

<%--<jsp:include page="../top.jsp"></jsp:include>--%>

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
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp"><b>首页</b></a>
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
                            <li><a href="<c:url value='/CartItemServlet?method=myCart'/>"><b>购物车</b></a>
                            </li>
                            <li><a href="<c:url value='/OrderServlet?method=myOrders'/>"><b>订单</b></a></li>
                            <li><a href="<c:url value='/OrderServlet?method=myOrders'/>"><b>个人中心</b></a></li>
                            <li><a href="<c:url value='/UserServlet?method=quit'/>" target="_parent"><b>退出</b></a></li>
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
<div class="container" style="margin-top: 20px">

    <c:choose>
        <c:when test="${empty pb.beanList }">
            <table align="center" style="margin-top: 150px">
                <tr>
                    <td>
                        <img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
                    </td>
                    <td>
                        <h4>暂时没有符合条件商品!</h4>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <div class="btn-group col-sm-1 col-sm-offset-10" style="">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false">
                    排序 <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="<c:url value='/DrugServlet?method=findBydrugDesc&condition2=price'/>"
                           target="_parent"><b>价格降序</b></a></li>
                    <li><a href="<c:url value='/DrugServlet?method=findByAsc&condition2=price'/>"
                           target="_parent"><b>价格升序</b></a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="<c:url value='/DrugServlet?method=findBydrugDesc&condition2=sales'/>"
                           target="_parent"><b>销量降序</b></a></li>
                    <li><a href="<c:url value='/DrugServlet?method=findByAsc&condition2=sales'/>"
                           target="_parent"><b>销量升序</b></a></li>
                </ul>
            </div>

            <div style="margin-bottom: 40px;">
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-primary btn-primary" data-toggle="modal" data-target="#myModal">
                    筛选
                </button>

                <!-- Modal -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">全部筛选</h4>
                            </div>
                            <div class="modal-body">
                                <form action="<c:url value='/DrugServlet?method=findByCombination'/>" method="post"
                                      id="form1">
                                    <input type="hidden" name="condition" value="${condition}"/>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">牌子</label>
                                        <div class="col-sm-10">
                                            <input class="form-control input"
                                                   type="text" name="sign" id="sign">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">发货地</label>
                                        <div class="col-sm-10">
                                            <input class="form-control input"
                                                   type="text" name="shipAddress" id="shipAddress">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">价格范围</label>
                                        <div class="col-sm-2">
                                            <input class="form-control input"
                                                   type="text" name="minprice" id="minprice">
                                        </div>
                                        <div class="col-sm-2">
                                            <input class="form-control input"
                                                   type="text" name="maxprice" id="maxprice">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <a href="javascript:$('#form1').submit();">
                                    <button type="button" class="btn btn-primary">确认</button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <ul>
                    <c:forEach items="${pb.beanList }" var="drug">
                        <li id="liid">
                            <div class="inner" style="margin-left: 80px">
                                <a class="pic"
                                   href="<c:url value='/DrugServlet?method=load&drugid=${drug.drugId }'/>"><img
                                        src="<c:url value='/${drug.image_b }'/>" border="0" width="200"
                                        height="200"/></a>
                                <p class="price">
                                    <span class="price_n">&yen;${drug.price }</span>

                                </p>
                                <p><a id="drugname" title="${drug.drugDesc }"
                                      href="<c:url value='/DrugServlet?method=load&drugid=${drug.drugId }'/>">${drug.drugName }</a>
                                </p>
                                    <%-- url标签会自动对参数进行url编码 --%>

                                <p>
                                    <span>牌子：${drug.sign }</span>
                                </p>
                                <p>
                                    <span>发货地：${drug.shipAddress }</span>
                                </p>
                                <p>
                                    <span>销量：</span>${drug.sales }
                                </p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div style="float:left; width: 100%; text-align: center;">
                <hr/>
                <br/>
                <%@include file="/jsps/pager/pager.jsp" %>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
