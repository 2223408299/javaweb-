<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>用户登录</title>
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<%--    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/login.css'/>">--%>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="<c:url value='/js/jquery-1.11.3.min.js'/>"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<c:url value='/jsps/js/user/login.js'/>"></script>

    <script type="text/javascript">
        $(function () {/*Map<String(Cookie名称),Cookie(Cookie本身)>*/
            // 获取cookie中的用户名
            var loginname = window.decodeURI("${cookie.loginname.value}");
            if ("${requestScope.form.loginname}") {
                loginname = "${requestScope.form.loginname}";
            }
            $("#loginname").val(loginname);
        });
    </script>
    <style type="text/css">
        .error{
            color: red;
        }
    </style>
</head>
<body style="background-color: #0c8ec3">
<div class="col-sm-6 col-sm-offset-3" style="margin-top: 150px;border: 3px solid #D2D3D5;background-color: white">
    <div style="text-align: center"><h3>会员登录</h3></div>
    <div class=" col-sm-2">
        <a  href="index.jsp">网站主页</a>
    </div>
    <div class="col-sm-offset-8 col-sm-2">
        <a href="<c:url value='/jsps/user/regist.jsp'/>">前往注册</a>
    </div>
    <form class="form-horizontal" action="<c:url value='/UserServlet'/>" method="post" id="loginForm">
        <input type="hidden" name="method" value="login"/>

        <div class="col-sm-12" style="text-align: center">
            <label class="error" style="color: red" id="msg">${msg }</label>
        </div>
        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">用户名</label>
            <div class="col-sm-4">
                <input class="form-control input"
                       type="text" name="loginname" id="loginname">
            </div>
            <div class="col-sm-4">
                <label id="loginnameError" class="error">${errors.loginname }</label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">密码</label>
            <div class="col-sm-4">
                <input class="form-control input" type="password" name="loginpass" id="loginpass" value="${form.loginpass }">
            </div>
            <div class="col-sm-4">
                <label id="loginpassError" class="error">${errors.loginpass }</label>
            </div>
        </div>

        <div class="row">
            <div style="text-align: center">
                <img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/>
                <a href="javascript:_hyz()">换一张</a>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 col-sm-offset-1 control-label">验证码</label>
            <div class="col-sm-4">
                <input class="form-control input yzm" type="text" name="verifyCode" id="verifyCode" value="${form.verifyCode }">
            </div>
            <div class="col-sm-4">
                <label id="verifyCodeError" class="error">${errors.verifyCode }</label>
            </div>
        </div>

        <div class="form-group" style="margin-top: 5px">
            <div class="col-sm-offset-5 col-sm-3">
                <button type="submit" class="btn btn-primary" id="submit">登录</button>
                <button type="button" class="btn btn-info" onclick="history.go(-1)">返回</button>
            </div>
            <div class="col-sm-2">
                <a href="jsps/user/findpwd.jsp">忘记密码</a>
        </div>
        </div>
    </form>
</div>




<%--<div class="col-xs-4 col-xs-offset-4" align="center" style="border: 5px solid #d1d1cf;margin-top: 150px;">--%>
<%--    <div class="loginTopDiv">--%>
<%--        <span class="loginTop">会员登录</span>--%>
<%--        <span>--%>
<%--                <a href="<c:url value='/jsps/user/regist.jsp'/>" class="registBtn"></a>--%>
<%--              </span>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <form target="_top" action="<c:url value='/UserServlet'/>" method="post" id="loginForm">--%>
<%--            <input type="hidden" name="method" value="login"/>--%>
<%--            <table>--%>
<%--                <tr>--%>
<%--                    <td width="50"></td>--%>
<%--                    <td><label class="error" id="msg">${msg }</label></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td width="50">用户名</td>--%>
<%--                    <td><input class="input" type="text" name="loginname" id="loginname"/></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td height="20">&nbsp;</td>--%>
<%--                    <td><label id="loginnameError" class="error">${errors.loginname }</label></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>密　码</td>--%>
<%--                    <td><input class="input" type="password" name="loginpass" id="loginpass"--%>
<%--                               value="${form.loginpass }"/></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td height="20">&nbsp;</td>--%>
<%--                    <td><label id="loginpassError" class="error">${errors.loginpass }</label></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>验证码</td>--%>
<%--                    <td>--%>
<%--                        <input class="input yzm" type="text" name="verifyCode" id="verifyCode"--%>
<%--                               value="${form.verifyCode }"/>--%>
<%--                        <img id="VerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/>--%>
<%--                        <a href="javascript:_hyz()">换一张</a>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td height="20px">&nbsp;</td>--%>
<%--                    <td><label id="verifyCodeError" class="error">${errors.verifyCode }</label></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>&nbsp;</td>--%>
<%--                    <td align="left">--%>
<%--                        <input type="image" id="submit" src="<c:url value='/images/login1.jpg'/>" class="loginBtn"/>--%>
<%--                    </td>--%>
<%--                    <td>--%>
<%--                        <a href="jsps/user/findpwd.jsp">忘记密码</a>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </table>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--</div>--%>
</body>
</html>
	