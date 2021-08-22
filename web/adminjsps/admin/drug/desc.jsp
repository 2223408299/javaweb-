<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/14
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>药品详细</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/drug/drugDesc.css'/>">
    <script type="text/javascript" src="adminjsps/admin/js/drug/desc.js"></script>

</head>
<body>
<div style="margin-top: 50px;text-align: center">
    <input type="checkbox" id="box"><label for="box">修改</label>
    <input type="checkbox" id="box2"><label for="box">更换药品图片</label>
</div>
<div id="show">
    <div class="row">
        <div class="divBookName col-sm-5" style="text-align: center">${drug.drugName }</div>
    </div>
    <div class="row" style="text-align: center">
        <div class="col-sm-4 col-sm-offset-1" style="text-align: center">
            <img align="top" src="<c:url value='/${drug.image_b }'/>" width="350" height="350" class="img_image_w"/>
        </div>
        <div class="col-sm-7">
            <table>
                <tr>
                    <td>
                        商品编号：${drug.drugId  }
                    </td>
                </tr>
                <tr>
                    <td>
                        成本：<span class="price_n">&yen;${drug.cost }</span>
                    </td>
                </tr>
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
                        库存：${drug.inventories }
                    </td>
                </tr>
                <tr>
                    <td>
                        累计评价：${drug.evaluate }
                    </td>
                </tr>
                <tr>
                    <td>
                        所属一级分类：
                        <c:forEach items="${parents }" var="parent">
                            <c:if test="${drug.category.parent.cid eq parent.cid }">${parent.cname }</c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        所属二级分类：
                        <c:forEach items="${children }" var="child">
                            <c:if test="${drug.category.cid eq child.cid }">${child.cname }</c:if>
                        </c:forEach>
                    </td>
                </tr>
            </table>
        </div>
<%--        <a href="${pageContext.request.contextPath}//adminjsps/admin/drug/upDrugPicture.jsp"><button type="button" class="btn btn-primary" >更换药品图片</button></a>--%>
        <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
    </div>
</div>

<div class="col-sm-6 col-sm-offset-3" style="margin-top: 30px; border: 3px solid #D2D3D5;" id="formDiv">
    <div style="text-align: center"><h4>添加商品</h4></div>
    <div style="text-align: center"><h4 style="color: red">${msg}</h4></div>
    <form class="form-horizontal" action="<c:url value='/AdminDrugServlet'/>" method="post"
          onsubmit="return checkForm()">
        <input type="hidden" name="method" value="edit"/>
        <input type="hidden" name="drugId" value="${drug.drugId }"/>

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
                <textarea class="form-control input"
                          type="text" name="drugDesc" id="drugDesc">${drug.drugDesc}
                </textarea>
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
            <label class="col-sm-2 control-label">药品分类</label>
            <div class="col-sm-10">
                <select name="pid" id="pid" onchange="loadChildren()">
                    <option value="">====请选择1级分类====</option>
                    <c:forEach items="${parents }" var="parent">
                        <option value="${parent.cid }"
                                <c:if test="${drug.category.parent.cid eq parent.cid }">selected="selected"</c:if>>
                                ${parent.cname }</option>
                    </c:forEach>
                </select>

                <select name="cid" id="cid">
                    <option value="">====请选择2级分类====</option>
                    <c:forEach items="${children }" var="child">
                        <option value="${child.cid }"
                                <c:if test="${drug.category.cid eq child.cid }">selected="selected"</c:if>>
                                ${child.cname }</option>
                    </c:forEach>
                </select>
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


<div class="col-sm-6 col-sm-offset-3" style="margin-top: 100px; border: 3px solid #D2D3D5;" id="upPicture">
    <div style="text-align: center" ><h4>更换药品图片</h4></div>
    <div style="text-align: center"><h4 style="color: red">${msg}</h4></div>
    <form class="form-horizontal" action="<c:url value='/AdminUpDrugPicture'/>" enctype="multipart/form-data" method="post"
          onsubmit="return checkForm()">
        <input type="hidden" name="drugId" value="${drug.drugId }"/>

        <div class="form-group">
            <label class="col-sm-2 control-label">药品图片</label>
            <div class="col-sm-6">
                <input type="file" id="image_b" name="image_b">
            </div>
        </div>
        <div class="form-group">
            <div style="text-align: center">
                <button type="submit" class="btn btn-primary" id="submit">确认</button>
                <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
