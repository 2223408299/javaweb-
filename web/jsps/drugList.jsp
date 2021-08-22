<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>热门商品</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/drug/list.css'/>">
    <script type="text/javascript" src="<c:url value='/jsps/js/drug/list.js'/>"></script>
</head>
<body>
<div>
    <ul>
        <c:forEach items="${drugList }" var="drug">
            <li id="liid">
                <div class="inner" style="margin-left: 80px">
                    <a class="pic" target="_parent" href="<c:url value='/DrugServlet?method=load&drugid=${drug.drugId }'/>"><img
                            src="<c:url value='/${drug.image_b }'/>" width="200" height="200" border="0"/></a>
                    <p class="price">
                        <span class="price_n">&yen;${drug.price }</span>

                    </p>
                    <p><a id="drugname" title="${drug.drugDesc }" target="_parent"
                          href="<c:url value='/DrugServlet?method=load&drugid=${drug.drugId }'/>">${drug.drugName }</a>
                    </p>
                        <%-- url标签会自动对参数进行url编码 --%>

                    <p>
                        <span>牌子：${drug.sign }</span>
                    </p>
                    <p>
                        <span>发货地：${drug.shipAddress }</span>
                    </p>
                    <p>
                        <span>销量：</span>${drug.sales }
                    </p>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>
