<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>充值余额</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function checkForm() {
            if (!$("#rechange").val()) {
                alert("充值金额不能为空！");
                return false;
            }else {
                alert("充值申请成功,请等待管理员审核!");
                return true;
            }

        }
    </script>
</head>
<body>
<jsp:include page="../top.jsp"></jsp:include>
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 100px; border: 3px solid #D2D3D5;">
    <div style="text-align: center"><h4>余额充值</h4></div>
    <form class="form-horizontal" action="<c:url value='/UserServlet'/>" method="post" onsubmit="return checkForm()">
        <input type="hidden" name="method" value="rechange"/>
        <div class="form-group">
            <label class="col-sm-4 control-label">充值金额</label>
            <div class="col-sm-5">
                <input class="form-control input"
                       type="number" min="0" step="0.01" name="rechange" id="rechange">
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
