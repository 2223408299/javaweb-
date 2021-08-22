<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>支付准备</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<jsp:include page="../top.jsp"></jsp:include>
<div class="container" style="margin-top: 10px">
    <div class="row" style="text-align: center;margin-top: 60px">
        <div style="margin-top: 9px">
            您的账号余额为<h3 style="color: red">${mypurse.balance}元</h3>
        </div>
        <c:choose>
            <c:when test="${empty totals }">
                <c:choose>
                    <c:when test="${empty order }">
                        <div>
                            该订单${orderItem.orderItemId }需要支付<h3 style="color: red">${orderItem.subtotal }元</h3>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            该订单${order.oid }需要支付<h3 style="color: red">${order.total }元</h3>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div>
                    这些订单需要支付<h3 style="color: red">${totals }元</h3>
                </div>
            </c:otherwise>
        </c:choose>
        <div style="text-align: center"><h4 style="color: red">${msg}</h4></div>
    </div>
    <c:choose>
        <c:when test="${empty totals }">
            <c:choose>
                <c:when test="${empty order }">
                    <div class="row" style="text-align: center;margin-top: 200px">
                        <a href="<c:url value='/OrderServlet?method=payment&orderItemId=${orderItem.orderItemId }&status=2'/>">
                            <button type="button" class="btn btn-danger">确认支付</button>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row" style="text-align: center;margin-top: 200px">
                        <a href="<c:url value='/OrderServlet?method=payment2&orderId=${order.oid }&status=2'/>">
                            <button type="button" class="btn btn-danger">确认支付</button>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <div class="row" style="text-align: center;margin-top: 200px">
                <a href="<c:url value='/OrderServlet?method=payment3&orderItemIds=${orderItemIds }&totals=${totals}&status=2'/>">
                    <button type="button" class="btn btn-danger">确认支付</button>
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
