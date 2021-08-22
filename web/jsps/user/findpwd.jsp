<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>找回密码</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="drugDescription" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jsps/js/user/findpwd.js"></script>
    <style type="text/css">
        label.errorClass {
            color: #cc3300;
        }
    </style>
    <script type="text/javascript">
        var countdown = 10;

        function settime(obj) {
            if (countdown == 0) {
                obj.removeAttribute("disabled");
                obj.value = "获取验证码";
                countdown = 10;
                return;
            } else {
                obj.setAttribute("disabled", true);
                obj.value = "重新发送(" + countdown + ")";
                countdown--;
            }
            setTimeout(function () {
                    settime(obj);
                }
                , 1000);
        }

    </script>
</head>

<body>
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 150px; border: 3px solid #D2D3D5;">
    <div style="text-align: center"><h4>找回密码</h4></div>
    <form class="form-horizontal" action="<c:url value='/UserServlet'/>" method="post" id="findpwdForm">
        <input type="hidden" name="method" value="findPassword"/>
        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">用户名：</label>
            <div class="col-sm-4">
                <input class="form-control inputClass"
                       type="text" name="loginname" id="loginname" value="${user.loginname }">
            </div>
            <div class="col-sm-4">
                <label id="loginnameError" class="errorClass">${errors.loginname }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">新密码：</label>
            <div class="col-sm-4">
                <input class="form-control inputClass" type="password" name="loginpass" id="loginpass"
                       value="${user.loginpass }">
            </div>
            <div class="col-sm-4">
                <label id="loginpassError" class="errorClass">${errors.loginpass }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">确认密码：</label>
            <div class="col-sm-4">
                <input class="form-control inputClass" type="password" name="reloginpass" id="reloginpass"
                       value="${user.reloginpass }">
            </div>
            <div class="col-sm-4">
                <label id="reloginpassError" class="errorClass">${errors.reloginpass }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">Email：</label>
            <div class="col-sm-4">
                <input class="form-control inputClass" type="text" name="email" id="email" value="${user.email }">
            </div>
            <div class="col-sm-4">
                <label id="emailError" class="errorClass">${errors.email }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">验证码：</label>
            <div class="col-sm-4">
                <input class="form-control inputClass" type="text" name="verifyCode" id="verifyCode"
                       value="${user.verifyCode }">
            </div>
            <div class="col-sm-4">
                <label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-1">
                <input type="button" class="btn btn-success" id="content" onclick="settime(this)" value="获取验证码"></input>
            </div>

            <div class="col-sm-offset-1 col-sm-4">
                <button type="submit" class="btn btn-primary" id="submit">确定</button>
                <button type="reset" class="btn btn-danger">重置</button>
                <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
