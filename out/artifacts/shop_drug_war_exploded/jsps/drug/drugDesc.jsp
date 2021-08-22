<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>药品详细</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/drug/drugDesc.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
    <script src="<c:url value='/jsps/js/drug/drugDesc.js'/>"></script>
    <script type="text/javascript">

    </script>
</head>

<body>
<jsp:include page="../top.jsp"></jsp:include>
<div class="container" style="margin-top: 50px">
    <div class="divBookName row col-sm-offset-2">${drug.drugName }</div>
    <div class="row">
        <div class="col-sm-5 col-sm-offset-1" style="">
            <img align="top" src="<c:url value='/${drug.image_b }'/>" width="350" height="350" border="0" class="img_image_w"/>
        </div>
        <div class="col-sm-6">
            <table>
                <tr>
                    <td>
                        价格：<span class="price_n">&yen;${drug.price }</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        功效：${drug.drugDesc  }
                    </td>
                </tr>
                <tr>
                    <td>
                        牌子：${drug.sign }
                    </td>
                </tr>
                <tr>
                    <td>
                        发货地：${drug.shipAddress }
                    </td>
                </tr>
                <tr>
                    <td>
                        销量：${drug.sales }
                    </td>
                </tr>
                <tr>
                    <td>
                        累计评价：${drug.evaluate }
                    </td>
                </tr>
            </table>
            <c:choose>
                <c:when test="${empty status }">
                    <form id="form1" action="<c:url value='/CartItemServlet'/>" method="post">
                        <input type="hidden" name="method" value="add"/>
                        <input type="hidden" name="drugId" id="drugId" value="${drug.drugId }"/>
                        我要买：<input id="cnt" style="width: 40px;text-align: center;" type="text" name="quantity"
                                   value="1"/>件
                    </form>
                    <a id="btn" href="javascript:$('#form1').submit();">
                        <input type="image" src="<c:url value='/images/jrgwc.png'/>"/></a>
                </c:when>
                <c:otherwise>
                    <p style="color: red">现在查看的是 您所购买商品的信息<br>
                        于${orderItem.ordertime}下单购买了此商品</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="row" style="margin-top: 30px">
        <c:choose>
            <c:when test="${status eq 5 }">
                <form class="form-horizontal" action="<c:url value='/CommentServlet'/>"
                      method="post" onsubmit="return checkForm()">
                    <input type="hidden" name="method" value="addComment"/>
                    <input type="hidden" name="drugId" value="${drug.drugId}"/>
                    <input type="hidden" name="orderItemId" value="${orderItem.orderItemId}"/>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">评价内容</label>
                        <div class="col-sm-10">
                            <textarea class="form-control input" rows="5" name="content" id="content"
                                      placeholder="评价内容长度须小于100"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div style="text-align: center">
                            <button type="submit" class="btn btn-danger" id="submit">提交评价</button>
                            <button type="reset" class="btn btn-default">重置</button>
                        </div>
                    </div>
                </form>
            </c:when>
        </c:choose>
    </div>
    <div class="row col-sm-offset-1" style="margin-top: 60px">
        <span style="font-size: x-large">累计评价</span><span
            style="font-size: larger;color: red;margin-left: 10px">${drug.evaluate}</span>
    </div>
    <div class="row col-sm-offset-1">
        <table class="table" style="border-collapse: collapse">
            <c:forEach items="${pb.beanList }" var="comment">
                <tr style="height: 100px;border: black solid 2px">
                    <td width="60%">
                            ${comment.content}
                        <br><br>
                        <c:choose>
                            <c:when test="${comment.state eq 1 }">
                                <div style="border: 1px red solid">
                                    商家回复:${comment.reply}<br>
                                    回复时间:${comment.replyTime}
                                </div>
                                <br>
                            </c:when>
                        </c:choose>
                    </td>
                    <td width="20%" style="vertical-align: middle">
                            ${comment.commentTime}
                    </td>
                    <td width="20%" align="right" style="vertical-align: middle">
                            ${comment.user.loginname}(会员)
                    </td>
                </tr>
                <br>
            </c:forEach>
        </table>
    </div>

    <div style="float:left; width: 100%; text-align: center; margin-left: 50px;">
        <hr/>
        <br/>
        <%@include file="/jsps/pager/pager.jsp" %>
    </div>
</div>
</body>
</html>
