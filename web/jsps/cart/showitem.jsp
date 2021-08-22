<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="<c:url value='/js/round.js'/>"></script>
    <script type="text/javascript">
        //计算合计
        $(function () {
            var total = 0;
            $(".subtotal").each(function () {
                total += Number($(this).text());
            });
            $("#total").text(round(total, 2));
        });

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

<div class="container" style="margin-top: 10px">
    <form id="form1" action="<c:url value='OrderServlet'/>" method="post">
        <input type="hidden" name="cartItemIds" value="${cartItemIds }"/>
        <input type="hidden" name="method" value="createOrder"/>
        <table class="table table-hover" style="text-align: center;">
            <tr>
                <td colspan="2" align="left">
                    <h3>确认订单信息</h3>
                </td>
            </tr>
            <tr>
                <th style="text-align: center ">商品名称</th>
                <th style="text-align: center ">商品图片</th>
                <th style="text-align: center ">单价</th>
                <th style="text-align: center ">数量</th>
                <th style="text-align: center ">小计</th>
            </tr>
            <c:forEach items="${cartItemList }" var="cartItem">
                <tr style="height: 150px">
                    <td width="20%" style="vertical-align: middle">
                        <a href="<c:url value='/DrugServlet?method=load&drugid=${cartItem.drug.drugId }'/>">
                            <span>${cartItem.drug.drugName }</span></a>
                    </td>
                    <td width="25%" style="vertical-align: middle">
                        <a class="linkImage"
                           href="<c:url value='/DrugServlet?method=load&drugid=${cartItem.drug.drugId }'/>">
                            <img border="0" width="100" height="100" align="top"
                                 src="<c:url value='/${cartItem.drug.image_b }'/>"/></a>
                    </td>
                    <td width="20%" style="vertical-align: middle">
                        <h4 class="currPrice" style="color: red">&yen;${cartItem.drug.price }</h4>
                    </td>
                    <td width="15%" style="vertical-align: middle">
                        <h4>${cartItem.quantity }</h4>
                    </td>
                    <td width="20%" style="vertical-align: middle">
                    <span class="price_n" style="color: red">&yen;<span class="subTotal" style="color: red"
                                                                        id="${cartItem.cartItemId }Subtotal">${cartItem.subtotal }</span></span>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="5" align="right">
                    <span>总计：</span><span class="price_t" style="color: red;font-size: larger">&yen;
                <span id="total" style="color: red;font-size: larger"></span></span>
                </td>
            </tr>
            <tr>
                <td colspan="1" align="left">
                    <h3>选择收货地址</h3>
                </td>
            </tr>
            <c:forEach items="${myAddressList }" var="myAddress">
                <tr>
                    <td colspan="5" align="left">
                        <div class="radio">
                            <c:choose>
                                <c:when test="${myAddress.state eq 1}">
                                    <label>
                                        <input type="radio" name="addressId" id="addressId" value="${myAddress.addressId}" checked>
                                        收货人:${myAddress.consignee}
                                        手机号码:${myAddress.phone}
                                        收货地址:${myAddress.address}
                                    </label>
                                </c:when>
                                <c:otherwise>
                                    <label>
                                        <input type="radio" name="addressId" id="addressId" value="${myAddress.addressId}">
                                        收货人:${myAddress.consignee}
                                        手机号码:${myAddress.phone}
                                        收货地址:${myAddress.address}
                                    </label>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="5" align="right">
                    <button type="submit" class="btn btn-danger" id="submit">提交订单</button>
                </td>
            </tr>
        </table>
    </form>
    <div class="row">
        <button type="button" class="btn btn-primary btn-primary" data-toggle="modal"
                data-target="#myModal">
            新增收货地址
        </button>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">新增收货地址</h4>
                    </div>

                    <div class="modal-body">
                        <form class="form-horizontal" action="<c:url value='/UserServlet'/>"
                              method="post" id="form2" onsubmit="return checkForm()">
                            <input type="hidden" name="method" value="addmyaddress"/>
                            <input type="hidden" name="state" value="1">
                            <input type="hidden" name="cartItemList" value="${cartItemList }">
                            <input type="hidden" name="cartItemIds" value="${cartItemIds }">
                            <input  type="hidden" name="total" value="${total}">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">收货人</label>
                                <div class="col-sm-10">
                                    <input class="form-control input"
                                           type="text" name="consignee" id="consignee">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">手机号码</label>
                                <div class="col-sm-10">
                                    <input class="form-control input"
                                           type="number" name="phone" id="phone">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">收货地址</label>
                                <div class="col-sm-10">
                                    <input class="form-control input"
                                           type="text" name="address" id="address">
                                </div>
                            </div>
                            <div class="form-group" style="margin-left: 100px">
                                <input type="checkbox" name="state2">是否为默认地址
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                        </button>
                        <a href="javascript:$('#form2').submit();">
                            <button type="button" class="btn btn-primary">确认</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
