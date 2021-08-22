<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>订单管理详情</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" style="margin-top: 10px">
    <c:choose>
        <c:when test="${orderItem.status eq 1 }">
            <div class="row">
                <div class="col-sm-2">
                    <h4 style="color: #078d07">拍下商品</h4>
                </div>
                <div class="col-sm-1" style="margin-left: 90px">
                    <h4>付款</h4>
                </div>
                <div class="col-sm-2" style="margin-left: 140px">
                    <h4>卖家发货</h4>
                </div>
                <div class="col-sm-2" style="margin-left: 70px">
                    <h4>确认收货</h4>
                </div>
                <div class="col-sm-1" style="margin-left: 85px">
                    <h4>评价</h4>
                </div>
            </div>
            <div class="row" style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/pxsp.png">
            </div>
        </c:when>
    </c:choose>
    <div class="row" style="margin-top: 80px">
        <div class="col-sm-4 col-sm-offset-1" style="border:1px solid #000;height: 378px;">
            <h4>订单信息</h4>
            <hr>
            收货人:${orderItem.order.myAddress.consignee }
            <hr>
            手机号码:${orderItem.order.myAddress.phone }
            <hr>
            收货地址:${orderItem.order.myAddress.address }
            <hr>
            订单编号:${orderItem.orderItemId }
            <hr>
            下单时间:${orderItem.ordertime }
            <hr>
        </div>
        <div class="col-sm-7" style="text-align: center;border:1px solid #000;width: 600px;height: 378px;">
            <c:choose>
                <c:when test="${orderItem.status eq 1 }">
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <h4>订单状态:商品已拍下，等待买家付款</h4>
                    <hr>
                    <h4>您可以 &nbsp</h4>
                    <a onclick="return confirm('您是否真要取消该订单？')"
                       href="<c:url value='/AdminOrderServlet?method=updateStatus&status=6&orderItemId=${orderItem.orderItemId }'/>">
                        <button type="button" class="btn btn-default">取消订单</button>
                    </a>
                </c:when>
                <c:when test="${orderItem.status eq 2 }">
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <h4>订单状态:商品已付款，等待卖家发货</h4>
                    <hr>
                    <h4>您可以 &nbsp</h4>
                    <a href="<c:url value='/AdminOrderServlet?method=updateStatus&status=3&orderItemId=${orderItem.orderItemId }'/>">
                        <button type="button" class="btn btn-danger">发货</button>
                    </a>
                </c:when>
                <c:when test="${orderItem.status eq 3 }">
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <h4>订单状态:商品已发货，等待买家确认收货</h4>
                </c:when>
                <c:when test="${orderItem.status eq 4 }">
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <h4>订单状态:商品已确认收货，等待买家评价</h4>
                </c:when>
                <c:when test="${orderItem.status eq 5 }">
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <h4>订单状态:交易已完成</h4>
                    <hr>
                    <h4>您可以 &nbsp</h4>
                    <a href="<c:url value='/CommentServlet?method=findCommentByOrderItemId&orderItemId=${orderItem.orderItemId }'/>">
                        <button type="button" class="btn btn-danger">查看评价</button>
                    </a>
                </c:when>
                <c:when test="${orderItem.status eq 6 }">
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <h4>订单状态:商品已取消</h4>
                    <hr>
                    <h4>您可以 &nbsp</h4>
                    <a href="<c:url value='/AdminOrderServlet?method=payment&orderItemId=${orderItem.orderItemId }'/>">
                        <button type="button" class="btn btn-danger">删除订单</button>
                    </a>
                </c:when>
            </c:choose>
            <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
        </div>
    </div>
    <table class="table table-hover" style="text-align: center;">
        <tr>
            <th style="text-align: center ">商品名称</th>
            <th style="text-align: center ">商品图片</th>
            <th style="text-align: center ">单价</th>
            <th style="text-align: center ">数量</th>
            <th style="text-align: center ">交易状态</th>
        </tr>
        <tr style="height: 150px">
            <td width="12%" style="vertical-align: middle">
                ${orderItem.drug.drugName }
            </td>
            <td width="23%" style="vertical-align: middle">
                    <img border="0" width="100" height="100" align="top"
                         src="<c:url value='/${orderItem.drug.image_b }'/>"/>
            </td>
            <td width="10%" style="vertical-align: middle">
                <h4 class="currPrice" style="color: red">&yen;${orderItem.drug.price }</h4>
            </td>
            <td width="10%" style="vertical-align: middle">
                <h4>${orderItem.quantity }</h4>
            </td>
            <td width="10%" style="vertical-align: middle">
                <c:choose>
                    <c:when test="${orderItem.status eq 1 }">(等待付款)</c:when>
                    <c:when test="${orderItem.status eq 2 }">(准备发货)</c:when>
                    <c:when test="${orderItem.status eq 3 }">(等待确认)</c:when>
                    <c:when test="${orderItem.status eq 4 }">(交易成功)</c:when>
                    <c:when test="${orderItem.status eq 5 }">(已取消)</c:when>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td colspan="5" style="vertical-align: middle;" align="right">
                需付款: &nbsp&nbsp
                <span class="price_n" style="color: red">&yen;
                    <span class="subTotal" style="color: red"
                          id="${orderItem.orderItemId }Subtotal">${orderItem.subtotal }</span></span>
                &nbsp&nbsp&nbsp
                &nbsp&nbsp&nbsp
            </td>
        </tr>
    </table>
</div>
</body>
</html>
