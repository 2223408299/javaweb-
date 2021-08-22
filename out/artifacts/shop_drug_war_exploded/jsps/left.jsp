<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>菜单分类</title>
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="jsps/css/style.css">
</head>
  
  <body>
<%--  <div class="row">--%>
<%--	  <div class="col-xs-3">--%>
  		<div class=top>
  			<p class="title" align="center" style="color:white">全部商品分类</p>
  		</div>
    	<div class="box" id="menu">
    		<c:forEach items="${parents}" var="parent">
    			<div class="box-item">
       			 <p class="title" align="center">${parent.cname}</p>
       			 <ul class="box-item-content">
       			 	  <c:forEach items="${parent.children}" var="child">
          			  <li align="center"><a href="<c:url value='/DrugServlet?method=findByCategory&cid=${child.cid}'/>"
											target="_parent" style="color:white">${child.cname}</a></li>
          			  </c:forEach>
			        </ul>
			 	</div>
			 </c:forEach>  
		</div>
<%--	  </div>--%>
<%--	  <div class="col-xs-9">--%>
<%--		  <div id="carousel-example-generic" class="carousel slide" data-ride="carousel"--%>
<%--			   data-interval="2000" align="center">--%>
<%--			  <!-- Indicators -->--%>
<%--			  <ol class="carousel-indicators">--%>
<%--				  <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>--%>
<%--				  <li data-target="#carousel-example-generic" data-slide-to="1"></li>--%>
<%--				  <li data-target="#carousel-example-generic" data-slide-to="2"></li>--%>
<%--			  </ol>--%>

<%--			  <!-- Wrapper for slides -->--%>
<%--			  <div class="carousel-inner" role="listbox">--%>
<%--				  <div class="item active">--%>
<%--					  <a href=""><img src="./images/lbt1.jpg"></a>--%>
<%--					  <div class="carousel-caption">--%>
<%--					  </div>--%>
<%--				  </div>--%>
<%--				  <div class="item">--%>
<%--					  <a href=""><img src="./images/lbt2.jpg"></a>--%>
<%--					  <div class="carousel-caption">--%>
<%--					  </div>--%>
<%--				  </div>--%>
<%--				  <div class="item">--%>
<%--					  <a href=""><img src="./images/lbt3.jpg"></a>--%>
<%--					  <div class="carousel-caption">--%>
<%--					  </div>--%>
<%--				  </div>--%>
<%--			  </div>--%>

<%--			  <!-- Controls -->--%>
<%--			  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">--%>
<%--				  <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>--%>
<%--				  <span class="sr-only">Previous</span>--%>
<%--			  </a>--%>
<%--			  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">--%>
<%--				  <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>--%>
<%--				  <span class="sr-only">Next</span>--%>
<%--			  </a>--%>
<%--		  </div>--%>
<%--	  </div>--%>
<%--  </div>--%>
  </body>
</html>
