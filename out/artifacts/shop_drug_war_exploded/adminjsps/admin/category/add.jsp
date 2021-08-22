<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/11
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加一级分类</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function checkForm() {
            if (!$("#cname").val()) {
                alert("分类名不能为空！");
                return false;
            }
            if (!$("#categoryDesc").val()) {
                alert("分类描述不能为空！");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 100px; border: 3px solid #D2D3D5;">
    <div style="text-align: center"><h4>添加一级分类</h4></div>
    <form class="form-horizontal" action="<c:url value='/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
                <input type="hidden" name="method" value="addParent"/>
        <div class="form-group">
            <label class="col-sm-2 control-label">分类名</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="text" name="cname" id="cname">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">分类描述</label>
            <div class="col-sm-10">
                <textarea class="form-control input" type="text" name="categoryDesc" id="categoryDesc"></textarea>
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
