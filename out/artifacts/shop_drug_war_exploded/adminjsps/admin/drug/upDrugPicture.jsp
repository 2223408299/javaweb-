<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改商品图片</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 100px; border: 3px solid #D2D3D5;">
    <div style="text-align: center" ><h4>更换药品图片</h4></div>
    <div style="text-align: center"><h4 style="color: red">${msg}</h4></div>
    <form class="form-horizontal" action="<c:url value='/AdminUpDrugPicture'/>" enctype="multipart/form-data" method="post"
          onsubmit="return checkForm()">
        <input type="hidden" name="method" value="addChild"/>
        <div class="form-group">
            <label class="col-sm-2 control-label">药品图片</label>
            <div class="col-sm-6">
                <input type="file" id="image_b" name="image_b">
            </div>
        </div>
        <div class="form-group">
            <div style="text-align: center">
                <button type="submit" class="btn btn-primary" id="submit">确认</button>
                <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
