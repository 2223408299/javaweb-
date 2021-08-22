<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<c:url value='/jsps/js/user/regist2.js'/>"></script>
    <style type="text/css">
        .error{
            color: red;
        }
    </style>
</head>
<body style="background-color: #0c8ec3">
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 150px;border: 3px solid #D2D3D5;background-color: white">
    <div style="text-align: center"><h3>新用户注册</h3></div>
    <div class=" col-sm-2">
        <a  href="index.jsp">网站主页</a>
    </div>
    <div class="col-sm-offset-8 col-sm-2">
        <a href="<c:url value='/jsps/user/login.jsp'/>">前往登录</a>
    </div>
    <form class="form-horizontal" action="<c:url value='/UserServlet'/>" method="post" id="loginForm">
        <input type="hidden" name="method" value="regist"/>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">用户名</label>
            <div class="col-sm-4">
                <input class="form-control input"
                       type="text" name="loginname" id="loginname">
            </div>
            <div class="col-sm-4">
                <label id="loginnameError" class="error">${errors.loginname }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">密码</label>
            <div class="col-sm-4">
                <input class="form-control input" type="password" name="loginpass" id="loginpass" value="${form.loginpass }">
            </div>
            <div class="col-sm-4">
                <label id="loginpassError" class="error">${errors.loginpass }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">确认密码</label>
            <div class="col-sm-4">
                <input class="form-control input" type="password" name="reloginpass" id="reloginpass" value="${form.reloginpass }">
            </div>
            <div class="col-sm-4">
                <label id="reloginpassError" class="error">${errors.reloginpass }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">Email</label>
            <div class="col-sm-4">
                <input class="form-control input" type="text" name="email" id="email" value="${form.email }">
            </div>
            <div class="col-sm-4">
                <label id="emailError" class="error">${errors.email }</label>
            </div>
        </div>

        <div class="row">
            <div style="text-align: center">
                <img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/>
                <a href="javascript:_hyz()">换一张</a>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">验证码</label>
            <div class="col-sm-4">
                <input class="form-control input yzm" type="text" name="verifyCode" id="verifyCode" value="${form.verifyCode }">
            </div>
            <div class="col-sm-4">
                <label id="verifyCodeError" class="error">${errors.verifyCode }</label>
            </div>
        </div>

        <div class="form-group" style="margin-top: 5px">
            <div class="col-sm-offset-5 col-sm-3">
                <button type="submit" class="btn btn-primary" id="submit">立即注册</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
