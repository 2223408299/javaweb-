<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xlink" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>评论管理</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="adminjsps/admin/js/comment/list.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/pager/pager.css'/>">
</head>
<body>

<div class="container">

    <div class="row" style="text-align: center">
        <h1>评论管理</h1>
    </div>
    <c:choose>
    <c:when test="${empty pb.beanList }">
        <table style="margin-top: 200px;margin-left: 350px">
            <tr>
                <td>
                    <h1>暂时没有符合条件的的评论!</h1>
                </td>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
    <div class="row">
        <div style="margin-top: 50px" class="col-sm-1">
            <a href="javascript:batchDelete();">
                <button type="button" class="btn btn-danger">批量删除</button>
            </a>
        </div>
        <div class="col-sm-4 col-sm-offset-7" style="margin-top: 43px" s>
            <form class="navbar-form navbar-left"
                  action="<c:url value='/CommentServlet?method=findBycontent&state=${state}'/>"
                  method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="请输入评论内容" name="content">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>

    <div class="row" style="margin-top: 10px">
        <table class="table table-hover" style="text-align: center;">
            <tr>
                <th style="text-align: center " width="5%">
                    <input type="checkbox" id="selectAll" checked="checked"/><label
                        for="selectAll">全选</label>
                </th>
                    <%--                <th style="text-align: center ">评论编号</th>--%>
                <th style="text-align: center ">评论时间</th>
                    <%--                <th style="text-align: center ">对应订单编号</th>--%>
                <th style="text-align: center ">药品名称</th>
                <th style="text-align: center ">药品图片</th>
                <th style="text-align: center ">评论内容</th>
                <c:choose>
                    <c:when test="${state eq 1}">
                        <th style="text-align: center ">回复内容</th>
                    </c:when>
                </c:choose>
                <th style="text-align: center ">操作</th>
            </tr>
            <c:forEach items="${pb.beanList }" var="comment">
                <tr>
                    <td width="5%" style="vertical-align: middle">
                        <input value="${comment.commentId }" type="checkbox" name="checkboxBtn"
                               checked="checked"/>
                        <input type="hidden" id="state" value="${comment.state}">
                    </td>
                    <td style="vertical-align: middle">${comment.commentTime }</td>
                        <%--                    <td style="vertical-align: middle">${comment.orderItem.orderItemId}</td>--%>
                    <td style="vertical-align: middle">${comment.drug.drugName}</td>
                    <td style="vertical-align: middle">
                        <img border="0" width="100" height="100" align="top"
                             src="<c:url value='/${comment.drug.image_b }'/>"/>
                    </td>
                    <td style="vertical-align: middle">${comment.content}</td>
                    <td style="vertical-align: middle">${comment.reply}</td>
                    <td style="vertical-align: middle">
                        <a href="<c:url value='/CommentServlet?method=findCommentByCommentId&commentId=${comment.commentId}'/>">
                            <button type="button" class="btn btn-primary">订单详情</button>
                        </a>
                        <a onclick="return confirm('您是否真要删除该评论？')"
                           href="<c:url value='/CommentServlet?method=deleteComment&commentId=${comment.commentId}&drugId=${comment.drug.drugId }&state=${comment.state}'/>">
                            <button type="button" class="btn btn-danger">删除</button>
                        </a>
                        <c:choose>
                            <c:when test="${comment.state eq 0 }">
                                <button type="button" class="btn btn-primary btn-primary" data-toggle="modal"
                                        data-target="#myModal">
                                    回复
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
                                                <h4 class="modal-title" id="myModalLabel">回复评价</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<c:url value='/CommentServlet?method=replyComment&state=${comment.state}'/>"
                                                      method="post" id="form1">
                                                    <input type="hidden" name="commentId" value="${comment.commentId}"/>

                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">回复内容</label>
                                                        <div class="col-sm-10">
                                                    <textarea class="form-control input" rows="5" name="reply"
                                                              id="reply" placeholder="回复长度须小于100"></textarea>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                                                </button>
                                                <a href="javascript:$('#form1').submit();">
                                                    <button type="button" class="btn btn-primary">确认</button>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
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
