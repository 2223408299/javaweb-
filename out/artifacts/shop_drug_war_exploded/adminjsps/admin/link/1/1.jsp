<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/5/10
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>梦想标志Swiper汇总</title>
    <link href="http://cdn.bootcss.com/highlight.js/8.0/styles/monokai_sublime.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/heightlength.css">
    <link rel="stylesheet" href="../css/mxjs.css">

</head>

<body>

<div class="container">
    <div class="jsbg">
        <div class="col-lg-12 mxlength">
            <div class="mx-1">
                <h2>jQuery选择器</h2>
                <p>1.ID的box进行触发</p>
                <p>2.class进行触发</p>
                <p>3.span等标签进行触发</p>
                <p>4.#box 子级 的进行触发</p>
                <p>5.class，ID等可多个</p>
                <p>6.*是全局选中</p>
                <p>7.可以是ul li 下的* 所有选中</p>
                <p>8.div当前含有class为box</p>
            </div>
        </div>
        <div class="col-lg-12  ">
      <pre class="mScroll">
        <code class="lang-javascript">
	$("#box").css({ background :"#000" })

	$(".box").css({ background :"#000" })

	$("span").css({ background :"#000" })

	$("#box p").css({ background :"#000" })

	$(".box").eq().css({ background :"#000" })

	$(".box, #box2, span, div").css({ background :"#000" })

	$(".box  ul  li  a").css({ background :"#000" })

	$("*").css({ background :"#000" })

	$("ul li *").css({ background :"#000" })

	$("div.box").css({ background :"#000" })
		</code>
    </pre>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/highlight.js/8.0/highlight.min.js"></script>
<script src="../js/smoothscroll.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<script src="../js/mking.js"></script>
</body>
</html>
</title>
</head>
<body>

</body>
</html>
