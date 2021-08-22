<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评论详情</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" style="margin-top: 10px">
    <div class="row" style="margin-top: 80px">
        <div class="col-sm-4 col-sm-offset-1" style="border:2px solid #000;height: 378px;">
            <h4>评论对应订单和药品信息</h4>
            <hr>
            对应订单编号:${comment.orderItem.orderItemId }
            <hr>
            下单时间:${comment.orderItem.ordertime }
            <hr>
            对应药品名称:${comment.drug.drugName }
            <hr>
            对应药品图片:<img border="0" width="100" height="100" align="top"
                        src="<c:url value='/${comment.drug.image_b }'/>"/>
            <hr>
        </div>
        <div class="col-sm-7" style="text-align: center;border:2px solid #000;width: 600px;height: 378px;">
            <h4>评论内容</h4>
            <div style="border: red 1px solid">
                买家评论内容:${comment.content}
                <hr>
                评论时间:${comment.commentTime}
            </div>
            <c:choose>
                <c:when test="${comment.state eq 1 }">
                    <div style="border: #06ec06 1px solid">
                        商家回复内容:${comment.reply}
                        <hr>
                        评论时间:${comment.replyTime}
                    </div>
                </c:when>
            </c:choose>
            <h4>您可以 &nbsp</h4>
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
                                                                <textarea class="form-control input" rows="5"
                                                                          name="reply"
                                                                          id="reply"
                                                                          placeholder="回复长度须小于100"></textarea>
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
            <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
        </div>
    </div>
</div>
</body>
</html>
