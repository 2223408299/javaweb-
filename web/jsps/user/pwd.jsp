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

    <title>修改密码</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="drugDescription" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <script type="text/javascript" src="<c:url value='/js/jquery-1.11.3.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/jsps/js/user/pwd.js'/>"></script>
    <script src="<c:url value='/js/common.js'/>"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<style type="text/css">
    label.error {
        color: #cc3300;
    }
</style>
<body>
<jsp:include page="../top.jsp"></jsp:include>
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 150px; border: 3px solid #D2D3D5;">
    <div style="text-align: center"><h4>修改密码</h4></div>
    <form class="form-horizontal" action="<c:url value='/UserServlet'/>" method="post">
        <input type="hidden" name="method" value="updatePassword"/>
        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">原密码</label>
            <div class="col-sm-4">
                <input class="form-control input"
                       type="password" name="loginpass" id="loginpass" value="${user.loginpass }">
            </div>
            <div class="col-sm-4">
                <label id="loginpassError" class="error">${errors.loginpass }${msg }</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">新密码</label>
            <div class="col-sm-4">
                <input class="form-control input" type="password" name="newpass" id="newpass" value="${user.newpass }">
            </div>
            <div class="col-sm-4">
                <label id="newpassError" class="error">${errors.newpass }</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">确认密码</label>
            <div class="col-sm-4">
                <input class="form-control input" type="password" name="reloginpass" id="reloginpass"
                       value="${user.reloginpass }">
            </div>
            <div class="col-sm-4">
                <label id="reloginpassError" class="error">${errors.reloginpass }</label>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-4">
                <button type="submit" class="btn btn-primary" id="submit">修改</button>
                <button type="reset" class="btn btn-danger">重置</button>
                <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
            </div>
            <div class="col-sm-3 col-sm-offset-1">
                <a href="jsps/user/findpwd.jsp">忘记密码</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>