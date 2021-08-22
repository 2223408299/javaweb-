<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单管理</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="adminjsps/admin/js/order/list.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/drug/listcate.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="row">
            <div class="navbar">
                <div class="nav">
                    <ul>
                        <li>
                            <c:choose>
                                <c:when test="${st eq 1 }">
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=1'/>"
                                       style="color: red">未付款</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=1'/>"
                                    >未付款</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${st eq 2 }">
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=2'/>"
                                       style="color: red">已付款</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=2'/>"
                                    >已付款</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${st eq 3 }">
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=3'/>"
                                       style="color: red">已发货</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=3'/>"
                                    >已发货</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${st eq 4 }">
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=4'/>"
                                       style="color: red">已确认收货</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=4'/>"
                                    >已确认收货</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${st eq 5 }">
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=5'/>"
                                       style="color: red">已评价</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=5'/>"
                                    >已评价</a>
                                </c:otherwise>
                            </c:choose>

                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${st eq 6 }">
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=6'/>"
                                       style="color: red">已取消</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/AdminOrderServlet?method=findByStatus&status=6'/>"
                                    >已取消</a>
                                </c:otherwise>
                            </c:choose>

                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <div class="row" style="margin-top: 40px">
        <c:choose>
        <c:when test="${empty pb.beanList }">
            <table style="margin-top: 250px;margin-left: 500px">
                <tr>
                    <td>
                        <img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
                    </td>
                    <td>
                        <h4>暂时没有该类的订单!</h4>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
        <div class="col-sm-4 col-md-offset-8" style="margin-top: 43px" s>
            <form class="navbar-form navbar-left"
                  action="<c:url value='/AdminOrderServlet?method=findOrderByUidOrOrderItemId&status=${st }'/>"
                  method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户ID或者订单编号" name="uid">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>

    <div class="row">
        <%--                border-collapse:separate; border-spacing:0px 50px;--%>
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
            <tr>
                <td colspan="3" align="left">
                    &nbsp&nbsp
                    &nbsp&nbsp
                    &nbsp
                    &nbsp
                    <c:choose>
                        <c:when test="${st eq 2 }">
                            <input type="checkbox" id="selectAll" checked="checked"/><label
                                for="selectAll">全选</label>
                            &nbsp&nbsp
                            <a onclick="return confirm('您是否真要对所选的订单合并发货？')"
                               href="javascript:batchUpdate();">
                                <input type="hidden" id="status" value="${st}"/>
                                <button type="button" class="btn btn-danger">合并发货</button>
                            </a>
                        </c:when>
                        <c:when test="${st eq 6 }">
                            <input type="checkbox" id="selectAll" checked="checked"/><label
                                for="selectAll">全选</label>
                            &nbsp&nbsp
                            <a onclick="return confirm('您是否真要对所选的订单合并删除？')"
                               href="javascript:batchDelete();">
                                <input type="hidden" id="status" value="${st}"/>
                                <button type="button" class="btn btn-danger">合并删除</button>
                            </a>
                        </c:when>
                    </c:choose>
                </td>
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
                <tr style="height: 150px;">
                    <td width="15%" style="vertical-align: middle">
                            ${orderItem.drug.drugName }
                    </td>
                    <td width="25%" style="vertical-align: middle">
                        <img border="0" width="100" height="100" align="top"
                             src="<c:url value='/${orderItem.drug.image_b }'/>"/>
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
                            <c:when test="${orderItem.status eq 2 }">(等待发货)</c:when>
                            <c:when test="${orderItem.status eq 3 }">(等待确认)</c:when>
                            <c:when test="${orderItem.status eq 4 }">(等待评价)</c:when>
                            <c:when test="${orderItem.status eq 5 }">(已完成)</c:when>
                            <c:when test="${orderItem.status eq 6 }">(已取消)</c:when>
                        </c:choose>
                    </td>
                    <td width="15%" style="vertical-align: middle">
                        <a href="<c:url value='/AdminOrderServlet?method=load&orderItemId=${orderItem.orderItemId }'/>">
                            <button type="button" class="btn btn-primary">订单详情</button>
                        </a>
                        <c:choose>
                            <c:when test="${orderItem.status eq 1 }">
                                <a onclick="return confirm('您是否真要取消该订单？')"
                                   href="<c:url value='/AdminOrderServlet?method=updateStatus&status=6&orderItemId=${orderItem.orderItemId }'/>">
                                    <button type="button" class="btn btn-default">取消订单</button>
                                </a>
                            </c:when>
                            <c:when test="${orderItem.status eq 2 }">
                                <a href="<c:url value='/AdminOrderServlet?method=updateStatus&status=3&orderItemId=${orderItem.orderItemId }'/>">
                                    <button type="button" class="btn btn-danger">发货</button>
                                </a>
                            </c:when>
                            <c:when test="${orderItem.status eq 5 }">
                                <a href="<c:url value='/CommentServlet?method=findCommentByOrderItemId&orderItemId=${orderItem.orderItemId }'/>">
                                    <button type="button" class="btn btn-danger">查看评价</button>
                                </a>
                            </c:when>
                            <c:when test="${orderItem.status eq 6 }">
                                <a href="<c:url value='/AdminOrderServlet?method=payment&orderItemId=${orderItem.orderItemId }'/>">
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
</body>
</html>
