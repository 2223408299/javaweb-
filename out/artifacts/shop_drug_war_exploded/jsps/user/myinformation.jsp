<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人信息</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../top.jsp"></jsp:include>
<div class="container">
    <div class="col-md-2" style="margin-top: 60px">
        <table class="table table-hover">
            <tr>
                <td><h4 style="margin-left: 3rem;">个人中心</h4></td>
            </tr>
            <tr class="danger">
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
            <tr>
                <td><a href="<c:url value='/UserServlet?method=myaddress'/>"><h5 style="margin-left: 3rem;">我的收货地址</h5>
                </a></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/UserServlet?method=mypurse'/>"><h5 style="margin-left: 3rem;">我的钱包</h5></a>
                </td>
            </tr>
        </table>
    </div>
    <div class="col-md-10" style="text-align: center">
        <div class="row" style="text-align: center">
            <h2>个人信息</h2>
        </div>
        <div class="row" style="margin-top: 60px">
            账户名:<h3 style="color: red">${user.loginname}</h3>
        </div>
        <div class="row">
            绑定email:<h3 style="color: red">${user.email}</h3>
        </div>
        <div class="row">
            余额:<h3 style="color: red">${user.balance}</h3>
        </div>
    </div>
    <div class="row" style="text-align: center">
        <a href="<c:url value='/jsps/user/pwd.jsp'/>">
            <button type="button" class="btn btn-primary">修改密码</button>
        </a>
    </div>
</div>
</body>
</html>
