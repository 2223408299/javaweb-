<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的订单</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
    <script src="<c:url value='/jsps/js/order/list.js'/>"></script>
</head>
<body>
<jsp:include page="../top.jsp"></jsp:include>

<div class="container">
    <div class="col-md-2" style="margin-top: 60px">
        <table class="table table-hover">
            <tr>
                <td><h4 style="margin-left: 3rem;">个人中心</h4></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/UserServlet?method=myinformation'/>"><h5 style="margin-left: 3rem;">
                    个人信息</h5></a></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/CartItemServlet?method=myCart'/>"><h5 style="margin-left: 3rem;">我的购物车</h5>
                </a></td>
            </tr>
            <tr class="danger">
                <td><a href="<c:url value='/OrderServlet?method=myOrders'/>"><h5 style="margin-left: 3rem;">我的订单</h5>
                </a></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/UserServlet?method=myaddress'/>"><h5 style="margin-left: 3rem;">我的收货地址</h5>
                </a></td>
            </tr>
            <tr>
                <td><a href="<c:url value='/UserServlet?method=mypurse'/>"><h5 style="margin-left: 3rem;">我的钱包</h5></a>
                </td>
            </tr>
<%--            <tr>--%>
<%--                <td><a href="sbdt.html"><h5 style="margin-left: 3rem;">我的足迹</h5></a></td>--%>
<%--            </tr>--%>
        </table>
    </div>
    <div class="col-md-10">
        <div class="row">
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${st eq 0 }">
                        <a href="<c:url value='/OrderServlet?method=myOrders'/>">
                            <button type="button" class="btn btn-danger change" id="sydd">所有订单</button>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/OrderServlet?method=myOrders'/>">
                            <button type="button" class="btn btn-default">所有订单</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1" style="margin-left: 14px">
                <c:choose>
                    <c:when test="${st eq 1 }">
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=1'/>">
                            <button type="button" class="btn btn-danger change" id="dfk">待付款</button>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=1'/>">
                            <button type="button" class="btn btn-default">待付款</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${st eq 2 }">
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=2'/>">
                            <button type="button" class="btn btn-danger change" id="dfh">待发货</button>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=2'/>">
                            <button type="button" class="btn btn-default">待发货</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${st eq 3 }">
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=3'/>">
                            <button type="button" class="btn btn-danger change" id="dsh">待收货</button>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=3'/>">
                            <button type="button" class="btn btn-default">待收货</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${st eq 4 }">
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=4'/>">
                            <button type="button" class="btn btn-danger change" id="dpj">待评价</button>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=4'/>">
                            <button type="button" class="btn btn-default">待评价</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${st eq 5 }">
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=5'/>">
                            <button type="button" class="btn btn-danger change" id="ywc">已完成</button>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=5'/>">
                            <button type="button" class="btn btn-default">已完成</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${st eq 6 }">
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=6'/>">
                            <button type="button" class="btn btn-danger change" id="yqx">已取消</button>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/OrderServlet?method=findByStatus&status=6'/>">
                            <button type="button" class="btn btn-default">已取消</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <c:choose>
        <c:when test="${empty pb.beanList }">
            <div class="row">
                <table align="center" style="margin-top: 150px">
                    <tr>
                        <td>
                            <img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
                        </td>
                        <td>
                            <h4>您暂时没有该类的订单!</h4>
                        </td>
                    </tr>
                </table>
            </div>
        </c:when>
        <c:otherwise>
        <div class="row" style="margin-left: 50px;margin-top: 10px">
            <c:choose>
                <c:when test="${st eq 1 }">
                    <input type="checkbox" id="selectAll" checked="checked"/><label
                        for="selectAll">全选</label>
                    <input type="hidden" id="status" value="${st}"/>
                    &nbsp&nbsp
                    <a href="javascript:batchPayment();">
                        <button type="button" class="btn btn-danger">合并付款</button>
                    </a>
                </c:when>
                <c:when test="${st eq 3 }">
                    <input type="checkbox" id="selectAll" checked="checked"/><label
                        for="selectAll">全选</label>
                    <input type="hidden" id="status" value="${st}"/>
                    &nbsp&nbsp
                    <a onclick="return confirm('您是否真要对所选的订单合并确认收货？')"
                       href="javascript:batchUpdate();">
                        <button type="button" class="btn btn-danger">合并确认收货</button>
                    </a>
                </c:when>
                <c:when test="${st eq 6 }">
                    <input type="checkbox" id="selectAll" checked="checked"/><label
                        for="selectAll">全选</label>
                    <input type="hidden" id="status" value="${st}"/>
                    &nbsp&nbsp
                    <a onclick="return confirm('您是否真要删除所选中的订单？')"
                       href="javascript:batchDelete();">
                        <button type="button" class="btn btn-danger">合并删除</button>
                    </a>
                </c:when>
            </c:choose>
        </div>
        <div class="row" style="margin-top: 10px">
            <table class="table table-hover" style="text-align: center;">
                <tr>
                    <th style="text-align: center ">商品名称</th>
                    <th style="text-align: center ">商品图片</th>
                    <th style="text-align: center ">单价</th>
                    <th style="text-align: center ">数量</th>
                    <th style="text-align: center ">实付款</th>
                    <th style="text-align: center ">交易状态</th>
                    <th style="text-align: center ">交易操作</th>
                </tr>
                <c:forEach items="${pb.beanList }" var="orderItem">
                    <tr style="height: 50px;background-color: #d5eef8;">
                        <td colspan="1" style="vertical-align: middle">
                            <input value="${orderItem.orderItemId }" type="checkbox" name="checkboxBtn"
                                   checked="checked"/>
                        </td>
                        <td colspan="3" style="vertical-align: middle">
                            订单编号:${orderItem.orderItemId }
                        </td>
                        <td colspan="3" style="vertical-align: middle">
                            下单时间:${orderItem.ordertime }
                        </td>
                    </tr>
                    <tr style="height: 150px">
                        <td width="15%" style="vertical-align: middle">
                            <a href="<c:url value='/DrugServlet?method=load&drugid=${orderItem.drug.drugId }'/>">
                                <span>${orderItem.drug.drugName }</span></a>
                        </td>
                        <td width="25%" style="vertical-align: middle">
                            <a class="linkImage"
                               href="<c:url value='/DrugServlet?method=load&drugid=${orderItem.drug.drugId }'/>">
                                <img border="0" width="100" height="100" align="top"
                                     src="<c:url value='/${orderItem.drug.image_b }'/>"/></a>
                        </td>
                        <td width="11%" style="vertical-align: middle">
                            <h4 class="currPrice" style="color: red">&yen;${orderItem.drug.price }</h4>
                        </td>
                        <td width="11%" style="vertical-align: middle">
                            <h4>${orderItem.quantity }</h4>
                        </td>
                        <td width="11%" style="vertical-align: middle">
                                <span class="price_n" style="color: red">&yen;
                                    <span class="subTotal" style="color: red"
                                          id="${orderItem.orderItemId }Subtotal">${orderItem.subtotal }</span></span>
                        </td>
                        <td width="12%" style="vertical-align: middle">
                            <c:choose>
                                <c:when test="${orderItem.status eq 1 }">(等待付款)</c:when>
                                <c:when test="${orderItem.status eq 2 }">(准备发货)</c:when>
                                <c:when test="${orderItem.status eq 3 }">(等待确认)</c:when>
                                <c:when test="${orderItem.status eq 4 }">(等待评价)</c:when>
                                <c:when test="${orderItem.status eq 5 }">(已完成)</c:when>
                                <c:when test="${orderItem.status eq 6 }">(已取消)</c:when>
                            </c:choose>
                        </td>
                        <td width="15%" style="vertical-align: middle">
                            <a href="<c:url value='/OrderServlet?method=load&orderItemId=${orderItem.orderItemId }'/>">
                                <button type="button" class="btn btn-primary">订单详情</button>
                            </a>
                            <c:choose>
                                <c:when test="${orderItem.status eq 1 }">
                                    <a href="<c:url value='/OrderServlet?method=paymentPre&orderItemId=${orderItem.orderItemId }'/>">
                                        <button type="button" class="btn btn-danger">立即付款</button>
                                    </a>
                                    <a onclick="return confirm('您是否真要取消该订单？')"
                                       href="<c:url value='/OrderServlet?method=updateStatus&status=6&orderItemId=${orderItem.orderItemId }'/>">
                                        <button type="button" class="btn btn-default">取消订单</button>
                                    </a>
                                </c:when>
                                <c:when test="${orderItem.status eq 3 }">
                                    <a onclick="return confirm('您是否真要确认收货？')"
                                       href="<c:url value='/OrderServlet?method=updateStatus&status=4&orderItemId=${orderItem.orderItemId }'/>">
                                        <button type="button" class="btn btn-danger">确认收货</button>
                                    </a>
                                </c:when>
                                <c:when test="${orderItem.status eq 4 }">
                                    <a href="<c:url value='/DrugServlet?method=load&status=5&drugid=${orderItem.drug.drugId }&orderItemId=${orderItem.orderItemId }'/>">
                                        <button type="button" class="btn btn-danger">评价</button>
                                    </a>
                                </c:when>
                                <c:when test="${orderItem.status eq 5 }">
                                    <a href="<c:url value='/DrugServlet?method=load&drugid=${orderItem.drug.drugId }'/>">
                                        <button type="button" class="btn btn-danger">再次购买</button>
                                    </a>
                                </c:when>
                                <c:when test="${orderItem.status eq 6 }">
                                    <a onclick="return confirm('您是否真要删除订单？')"
                                       href="<c:url value='/OrderServlet?method=delect&status=6&orderItemId=${orderItem.orderItemId }'/>">
                                        <button type="button" class="btn btn-danger">删除订单</button>
                                    </a>
                                </c:when>
                            </c:choose>

                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div style="float:left; width: 100%; text-align: center">
                <hr/>
                <br/>
                <%@include file="/jsps/pager/pager.jsp" %>
            </div>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
