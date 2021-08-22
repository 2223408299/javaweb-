<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的收货地址</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/iconfont.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../top.jsp"></jsp:include>
<div class="container">
    <div class="col-md-2" style="margin-top: 60px">
        <table class="table table-hover">
            <tr>
                <td><h4 style="margin-left: 3rem;">个人中心</h4></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/UserServlet?method=myinformation'/>"><h5 style="margin-left: 3rem;">个人信息</h5></a></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/CartItemServlet?method=myCart'/>"><h5 style="margin-left: 3rem;">我的购物车</h5>
                </a></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/OrderServlet?method=myOrders'/>"><h5 style="margin-left: 3rem;">我的订单</h5>
                </a></td>
            </tr>
            <tr class="danger">
                <td><a href="<c:url value='/UserServlet?method=myaddress'/>"><h5 style="margin-left: 3rem;">我的收货地址</h5>
                </a></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/UserServlet?method=mypurse'/>"><h5 style="margin-left: 3rem;">我的钱包</h5></a>
                </td>
            </tr>
<%--            <tr>--%>
<%--                <td><a href="sbdt.html"><h5 style="margin-left: 3rem;">我的足迹</h5></a></td>--%>
<%--            </tr>--%>
        </table>
    </div>
    <div class="col-md-10">
        <div class="row" style="text-align: center">
            <h1>我的收货地址</h1>
        </div>
        <div class="row">
            <div class="col-sm-1">
                <a href="<c:url value='/jsps/user/addmyaddress.jsp'/>">
                    <button type="button" class="btn btn-primary">添加收货地址</button>
                </a>
            </div>
        </div>

        <div class="row" style="margin-top: 10px">
            <table class="table table-hover" style="text-align: center;" border="6">
                <tr>
                    <th style="text-align: center ">收货人</th>
                    <th style="text-align: center ">收货手机号码</th>
                    <th style="text-align: center ">收货地址</th>
                    <th style="text-align: center ">操作</th>
                </tr>
                <c:forEach items="${myAddressList }" var="myAddress">
                    <c:choose>
                        <c:when test="${myAddress.state eq 1}">
                            <tr style="color: red">
                                <td width="20%" style="vertical-align: middle">${myAddress.consignee}</td>
                                <td width="25%" style="vertical-align: middle">${myAddress.phone}</td>
                                <td width="25%" style="vertical-align: middle">${myAddress.address}</td>
                                <td width="30%" style="vertical-align: middle">
                                    <a href="<c:url value='/UserServlet?method=load&addressId=${myAddress.addressId }'/>">
                                        <button type="button" class="btn btn-primary">修改</button>
                                    </a>
                                    <a onclick="return confirm('您是否真要删除该收货地址？')"
                                       href="<c:url value='/UserServlet?method=delete&addressId=${myAddress.addressId }'/>">
                                        <button type="button" class="btn btn-danger">删除</button>
                                    </a>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td width="20%" style="vertical-align: middle">${myAddress.consignee}</td>
                                <td width="25%" style="vertical-align: middle">${myAddress.phone}</td>
                                <td width="25%" style="vertical-align: middle">${myAddress.address}</td>
                                <td width="30%" style="vertical-align: middle">
                                    <a href="<c:url value='/UserServlet?method=load&addressId=${myAddress.addressId }'/>">
                                        <button type="button" class="btn btn-primary">修改</button>
                                    </a>
                                    <a onclick="return confirm('您是否真要设置该地址为默认地址？')"
                                       href="<c:url value='/UserServlet?method=upstate&addressId=${myAddress.addressId }'/>">
                                        <button type="button" class="btn btn-danger">设默认地址</button>
                                    </a>
                                    <a onclick="return confirm('您是否真要删除该收货地址？')"
                                       href="<c:url value='/UserServlet?method=delete&addressId=${myAddress.addressId }'/>">
                                        <button type="button" class="btn btn-danger">删除</button>
                                    </a>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
