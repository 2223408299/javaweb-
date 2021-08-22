<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/6/7
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            alert(34);
            $("#submit").submit(function(){
                alert(222);
            });
        });
    </script>
</head>
<body>
<form action="#">
    <input type="text" id="name">
    <button type="submit" class="btn btn-primary" id="submit">登录</button>
</form>
</body>
</html>
