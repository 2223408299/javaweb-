
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加药品</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="adminjsps/admin/js/drug/drugadd.js"></script>
</head>
<body>

<div class="col-sm-6 col-sm-offset-3" style="margin-top: 100px; border: 3px solid #D2D3D5;">
    <div style="text-align: center" ><h4>添加商品</h4></div>
    <div style="text-align: center"><h4 style="color: red">${msg}</h4></div>
    <form class="form-horizontal" action="<c:url value='/AdminAddDrugServlet'/>" enctype="multipart/form-data" method="post"
          onsubmit="return checkForm()">
        <input type="hidden" name="method" value="addChild"/>

        <div class="form-group">
            <label class="col-sm-2 control-label">药品名称</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="text" name="drugName" id="drugName" value="${drug.drugName}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">牌子</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="text" name="sign" id="sign" value="${drug.sign}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">药品描述</label>
            <div class="col-sm-10">
                <textarea class="form-control input" type="text" name="drugDesc" id="drugDesc" >${drug.drugDesc}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">成本</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="number" min="0" step="0.01" name="cost" id="cost" value="${drug.cost}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">价格</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="number" min="0" step="0.01" name="price" id="price" value="${drug.price}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">库存</label>
            <div class="col-sm-10">
                <input class="form-control input"
                       type="number" min="0" name="inventories" id="inventories" value="${drug.inventories}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">发货地</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="shipAddress" name="shipAddress" value="${drug.shipAddress}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">药品图片</label>
            <div class="col-sm-6">
                <input type="file" id="image_b" name="image_b">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">药品分类</label>
            <div class="col-sm-10">
                <select name="pid" id="pid" onchange="loadChildren()">
                    <option value="">====请选择1级分类====</option>
                    <c:forEach items="${parents }" var="parent">
                        <option value="${parent.cid }">${parent.cname }</option>
                    </c:forEach>
                </select>
                <select name="cid" id="cid">
                    <option value="">====请选择2级分类====</option>
                </select>
                <input type="hidden" id="cid2" name="cid2" value="====请选择2级分类====">
                <input type="checkbox" name="state">是否上架
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
