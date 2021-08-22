<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加用戶</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/adminjsps/admin/js/user/add.js"></script>
</head>
<body>
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 100px; border: 3px solid #D2D3D5;">
    <div style="text-align: center"><h4>添加用戶</h4></div>
    <form class="form-horizontal" action="<c:url value='/AdminUserServlet'/>"
          method="post" onsubmit="return checkForm()">
        <input type="hidden" name="method" value="addUser"/>

        <div class="form-group">
            <label class="col-sm-2 control-label">用戶名称</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="text" name="loginname" id="loginname" value="${form.loginname}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="password" name="loginpass" id="loginpass" value="${form.loginpass}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">确认密码</label>
            <div class="col-sm-10">
                <input class="form-control input"
                          type="password" name="reloginpass" id="reloginpass">${form.reloginpass}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">email</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="email" name="email" value="${form.email}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">用户状态</label>
            <div class="col-sm-3" style="margin-top: 7px">
                <input type="checkbox" name="status">账号是否激活
            </div>
            <div class="col-sm-3" style="margin-top: 7px">
                <input type="checkbox" name="state">账号是否正常
            </div>
        </div>
        <div class="form-group">
            <div style="text-align: center">
                <button type="submit" class="btn btn-primary" id="submit">确认</button>
                <button type="reset" class="btn btn-danger">重置</button>
                <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
