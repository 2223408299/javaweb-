<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/10
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>管理员登录页面</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<style type="text/css">
label.error {
color: #cc3300;
}
</style>
<script type="text/javascript">
    function checkForm() {
        if(!$("#adminname").val()) {
            alert("管理员名称不能为空！");
            return false;
        }
        if(!$("#adminpwd").val()) {
            alert("管理员密码不能为空！");
            return false;
        }
        return true;
    }
</script>
<body style="background-color: #0c1312">
<div class="col-sm-4 col-sm-offset-4" style="margin-top: 150px;border: 3px solid #d2d3d5;background-color: white">
    <div style="text-align: center"><h4>管理员登录</h4></div>
    <form class="form-horizontal" action="<c:url value='/AdminServlet'/>" method="post" onsubmit="return checkForm()">
        <input type="hidden" name="method" value="login"/>

        <div class="col-sm-12" style="text-align: center">
            <label class="error" style="color: red">${msg }</label>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">管理员</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="text" name="adminname" id="adminname" value="${form.adminname }">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
                <input class="form-control input" type="password" name="adminpwd" id="adminpwd" value="${form.adminpwd }">
            </div>
        </div>

        <div class="form-group">
            <div style="text-align: center">
                <button type="submit" class="btn btn-primary" id="submit">登录后台</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
