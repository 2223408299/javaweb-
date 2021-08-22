<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>药品管理</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="adminjsps/admin/js/drug/list.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
    <style type="text/css">
        b:hover {
            color: #ff0000;
        }
    </style>
</head>
<%
    String condition = (String) request.getAttribute("condition");
    session.setAttribute("condition", condition);
%>
<body>
<div class="container">

    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="row">
            <%@include file="/adminjsps/admin/drug/listcate.jsp" %>
        </div>
    </nav>
    <%--    <div class="row" style="height: 30%">--%>
    <%--        <%@include file="/adminjsps/admin/drug/listcate.jsp" %>--%>
    <%--&lt;%&ndash;        <iframe frameborder="0" src="<c:url value='/AdminDrugServlet?method=findCategoryAll'/>" name="top" width="100%" height="50%"></iframe>&ndash;%&gt;--%>
    <%--    </div>--%>
    <div class="row">
        <c:choose>
        <c:when test="${empty pb.beanList }">
            <table style="margin-top: 270px;margin-left: 350px">
                <tr>
                    <td>
                        <h1>暂时没有符合条件的商品!</h1>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
        <div style="margin-top: 100px" class="col-sm-1">
            <a href="javascript:batchDelete();">
                <button type="button" class="btn btn-danger">批量删除</button>
            </a>
        </div>
        <div style="margin-top: 100px" class="col-sm-1">
            <c:if test="${condition3 eq 'putaway' }">
                <a href="javascript:batchSoldout();">
                    <button type="button" class="btn btn-danger">批量下架</button>
                </a>
            </c:if>
            <c:if test="${condition3 eq 'soldout' }">
                <a href="javascript:batchPutaway();">
                    <button type="button" class="btn btn-danger">批量上架</button>
                </a>
            </c:if>
        </div>
        <div style="margin-top: 100px" class="col-sm-1">
            <a href="<c:url value='/AdminDrugServlet?method=addPre'/>">
                <button type="button" class="btn btn-primary">添加药品</button>
            </a>
        </div>
        <div class="col-sm-4" style="margin-top: 93px" s>
            <form class="navbar-form navbar-left"
                  action="<c:url value='/AdminDrugServlet?method=findByDrugname&condition3=${condition3}'/>"
                  method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="药品名称" name="drugname">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
        <div class="col-sm-2 col-sm-offset-3" style="margin-top: 100px">
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false">
                排序 <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <li>
                    <a href="<c:url value='/AdminDrugServlet?method=findBydrugDesc&condition2=price&condition3=${condition3}'/>"
                    ><b>价格降序</b></a></li>
                <li>
                    <a href="<c:url value='/AdminDrugServlet?method=findByAsc&condition2=price&condition3=${condition3}'/>"
                    ><b>价格升序</b></a></li>
                <li>
                    <a href="<c:url value='/AdminDrugServlet?method=findBydrugDesc&condition2=sales&condition3=${condition3}'/>"
                    ><b>销量降序</b></a></li>
                <li>
                    <a href="<c:url value='/AdminDrugServlet?method=findByAsc&condition2=sales&condition3=${condition3}'/>"
                    ><b>销量升序</b></a></li>
                <li>
                    <a href="<c:url value='/AdminDrugServlet?method=findBydrugDesc&condition2=inventories&condition3=${condition3}'/>"
                    ><b>库存降序</b></a></li>
                <li>
                    <a href="<c:url value='/AdminDrugServlet?method=findByAsc&condition2=inventories&condition3=${condition3}'/>"
                    ><b>库存升序</b></a></li>
            </ul>

            <button type="button" class="btn btn-primary btn-primary" data-toggle="modal" data-target="#myModal">
                筛选
            </button>

            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">全部筛选</h4>
                        </div>
                        <div class="modal-body">
                            <form action="<c:url value='/AdminDrugServlet?method=findByCombination&condition3=${condition3}'/>"
                                  method="post"
                                  id="form1">
                                <input type="hidden" name="condition" value="${condition}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">牌子</label>
                                    <div class="col-sm-10">
                                        <input class="form-control input"
                                               type="text" name="sign" id="sign">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">发货地</label>
                                    <div class="col-sm-10">
                                        <input class="form-control input"
                                               type="text" name="shipAddress" id="shipAddress">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">价格范围</label>
                                    <div class="col-sm-2">
                                        <input class="form-control input"
                                               type="text" name="minprice" id="minprice">
                                    </div>
                                    <div class="col-sm-2">
                                        <input class="form-control input"
                                               type="text" name="maxprice" id="maxprice">
                                    </div>
                                </div>
                                    <%--                                <table align="center">--%>
                                    <%--                                    <tr>--%>
                                    <%--                                        <td>牌子：</td>--%>
                                    <%--                                        <td><input type="text" name="sign"/></td>--%>
                                    <%--                                    </tr>--%>
                                    <%--                                    <tr>--%>
                                    <%--                                        <td>发货地：</td>--%>
                                    <%--                                        <td><input type="text" name="shipAddress"/></td>--%>
                                    <%--                                    </tr>--%>
                                    <%--                                    <tr>--%>
                                    <%--                                        <td>价格范围：</td>--%>
                                    <%--                                        <td><input type="text" name="minprice"/></td>--%>
                                    <%--                                        <td>--</td>--%>
                                    <%--                                        <td><input type="text" name="maxprice"/></td>--%>
                                    <%--                                    </tr>--%>
                                    <%--                                </table>--%>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <a href="javascript:$('#form1').submit();">
                                <button type="button" class="btn btn-primary">确认</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="row" style="margin-top: 10px">
        <table class="table table-hover" style="text-align: center;" border="6">
            <tr>
                <th style="text-align: center ">
                    <input type="checkbox" id="selectAll" checked="checked"/><label
                        for="selectAll">全选</label>
                </th>
                <th style="text-align: center ">药品名称</th>
                <th style="text-align: center ">药品主图</th>
                <th style="text-align: center ">价格</th>
                <th style="text-align: center ">销量</th>
                <th style="text-align: center ">库存</th>
                <th style="text-align: center ">牌子</th>
                <th style="text-align: center ">发货地</th>
                <th style="text-align: center ">操作</th>
            </tr>
            <c:forEach items="${pb.beanList }" var="drug">
                <tr>
                    <td width="7%" style="vertical-align: middle">
                        <input value="${drug.drugId }" type="checkbox" name="checkboxBtn" checked="checked"/>
                    </td>
                    <td width="11%" style="vertical-align: middle">${drug.drugName}</td>
                    <td width="16%" style="vertical-align: middle"><img
                            src="<c:url value='/${drug.image_b }'/>" width="100" height="100" border="0"/></td>
                    <td width="9%" style="vertical-align: middle">${drug.price}</td>
                    <td width="9%" style="vertical-align: middle">${drug.sales}</td>
                    <td width="9%" style="vertical-align: middle">${drug.inventories}</td>
                    <td width="11%" style="vertical-align: middle">${drug.sign}</td>
                    <td width="11%" style="vertical-align: middle">${drug.shipAddress}</td>
                    <td width="17%" style="vertical-align: middle">
                        <a href="<c:url value='/AdminDrugServlet?method=load&drugId=${drug.drugId }'/>">
                            <button type="button" class="btn btn-primary">查看</button>
                        </a>
                        <a onclick="return confirm('您是否真要删除该商品？')"
                           href="<c:url value='/AdminDrugServlet?method=delete&drugId=${drug.drugId }'/>">
                            <button type="button" class="btn btn-danger">删除</button>
                        </a>
                        <c:if test="${condition3 eq 'putaway' }">
                            <a onclick="return confirm('您是否真要下架该商品？')"
                               href="<c:url value='/AdminDrugServlet?method=updateState&drugId=${drug.drugId }&condition3=${condition3}'/>">
                                <button type="button" class="btn btn-danger">下架</button>
                            </a>
                        </c:if>
                        <c:if test="${condition3 eq 'soldout' }">
                            <a onclick="return confirm('您是否真要上架该商品？')"
                               href="<c:url value='/AdminDrugServlet?method=updateState&drugId=${drug.drugId }&condition3=${condition3}'/>">
                                <button type="button" class="btn btn-danger">上架</button>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div style="float:left; width: 100%; text-align: center;">
    <hr/>
    <br/>
    <%@include file="/jsps/pager/pager.jsp" %>
</div>
</c:otherwise>
</c:choose>
</body>
</html>
