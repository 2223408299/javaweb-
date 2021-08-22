<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改收货地址</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function checkForm() {
            if (!$("#consignee").val()) {
                alert("收货人不能为空!");
                return false;
            }
            if (!$("#phone").val()) {
                alert("手机号码不能为空!");
                return false;
            }
            if (!$("#address").val()) {
                alert("收货地址不能为空!");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<jsp:include page="../top.jsp"></jsp:include>
<div class="col-sm-8 col-sm-offset-2" style="margin-top: 100px; border: 3px solid #D2D3D5;">
    <div style="text-align: center"><h4>修改收货地址</h4></div>
    <form class="form-horizontal" action="<c:url value='/UserServlet'/>"
          method="post" onsubmit="return checkForm()">
        <input type="hidden" name="method" value="edit"/>
        <input type="hidden" name="addressId" id="addressId" value="${myAddress.addressId}">

        <div class="form-group">
            <label class="col-sm-2 control-label">收货人</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="text" name="consignee" id="consignee" value="${myAddress.consignee}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">手机号码</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="number" name="phone" id="phone" value="${myAddress.phone}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">收货地址</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="text" name="address" id="address" value="${myAddress.address}">
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
