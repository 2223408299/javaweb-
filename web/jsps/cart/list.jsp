<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>我的购物车</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="<c:url value='/js/round.js'/>"></script>
    <script src="<c:url value='/jsps/js/cart/list.js'/>"></script>
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
                <td><a href="<c:url value='/UserServlet?method=myinformation'/>"><h5 style="margin-left: 3rem;">个人信息</h5></a></td>
            </tr>
            <tr class="danger">
                <td><a href="<c:url value='/CartItemServlet?method=myCart'/>"><h5 style="margin-left: 3rem;">我的购物车</h5>
                </a></td>
            </tr>
            <tr>
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
        <c:choose>
            <c:when test="${empty cartItemList }">
                <table align="center" style="margin-top: 150px">
                    <tr>
                        <td>
                            <img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
                        </td>
                        <td>
                            <h4>您的购物车中暂时没有商品</h4>
                        </td>
                    </tr>
                </table>
            </c:when>
            <c:otherwise>
                <table class="table table-hover" style="text-align: center;">
                    <tr>
                        <th style="text-align: center "></th>
                        <th style="text-align: center ">商品名称</th>
                        <th style="text-align: center ">商品图片</th>
                        <th style="text-align: center ">单价</th>
                        <th style="text-align: center ">数量</th>
                        <th style="text-align: center ">小计</th>
                        <th style="text-align: center ">操作</th>
                    </tr>
                    <c:forEach items="${cartItemList }" var="cartItem">
                        <tr style="height: 150px">
                            <td width="10%" style="vertical-align: middle">
                                <input value="${cartItem.cartItemId }" type="checkbox" name="checkboxBtn"
                                       checked="checked"/>
                            </td>
                            <td width="16%" style="vertical-align: middle">
                                <a href="<c:url value='/DrugServlet?method=load&drugid=${cartItem.drug.drugId }'/>">
                                    <span>${cartItem.drug.drugName }</span></a>
                            </td>
                            <td width="22%" style="vertical-align: middle">
                                <a class="linkImage"
                                   href="<c:url value='/DrugServlet?method=load&drugid=${cartItem.drug.drugId }'/>">
                                    <img border="0" width="100" height="100" align="top"
                                         src="<c:url value='/${cartItem.drug.image_b }'/>"/></a>
                            </td>
                            <td width="10%" style="vertical-align: middle">
                                <h4 class="currPrice" style="color: red">&yen;${cartItem.drug.price }</h4>
                            </td>
                            <td width="15%" style="vertical-align: middle">
                                <a class="jian btn btn-default" id="${cartItem.cartItemId }Jian">-</a>
                                <input style="width: 40px;height: 35px" readonly="readonly" id="${cartItem.cartItemId }Quantity"
                                       type="text" value="${cartItem.quantity }"/>
                                <input type="hidden" id="drugId" value="${cartItem.drug.drugId }">
                                <a class="jia btn btn-default" id="${cartItem.cartItemId }Jia">+</a>
                            </td>
                            <td width="12%" style="vertical-align: middle">
                    <span class="price_n" style="color: red">&yen;<span class="subTotal" style="color: red"
                                                                        id="${cartItem.cartItemId }Subtotal">${cartItem.subtotal }</span></span>
                            </td>
                            <td width="15%" style="vertical-align: middle">
                                <a onclick="return confirm('您是否真要删除该商品？')"
                                   href="<c:url value='/CartItemServlet?method=batchDelete&cartItemIds=${cartItem.cartItemId }'/>">
                                    <button type="button" class="btn btn-danger">删除</button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <form id="jieSuanForm" action="<c:url value='/CartItemServlet'/>" method="post">
                    <input type="hidden" name="cartItemIds" id="cartItemIds"/>
                    <input type="hidden" name="total" id="hiddenTotal"/>
                    <input type="hidden" name="method" value="loadCartItems"/>
                </form>

            </c:otherwise>
        </c:choose>
    </div>
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="row">
            <div class="col-sm-3 col-sm-offset-3">
                <input type="checkbox" id="selectAll" checked="checked"/><label
                    for="selectAll">全选</label>
                <a href="javascript:batchDelete();">
                    <button type="button" class="btn btn-danger">批量删除</button>
                </a>
            </div>
            <div class="col-sm-2 col-sm-offset-3">
                <span>总计：</span><span class="price_t" style="color: red;font-size: larger">&yen;
                <span id="total" style="color: red;font-size: larger"></span></span>
                <a href="javascript:jiesuan();" id="jiesuan" class="jiesuan">
                    <button type="button" class="btn btn-danger" id="jiesuanbtn">结算</button>
                </a>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
