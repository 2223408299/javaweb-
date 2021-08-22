function checkForm() {
    if (!$("#loginname").val()) {
        alert("用户名不能为空!");
        return false;
    }
    if ($("#loginname").val().length < 3 || $("#loginname").val().length > 20) {
        alert("用户名长度必须在3 ~ 20之间！");
        return false;
    }
    if(!validateLoginname()){
        alert("用户名已存在!");
        return false;
    }
    if (!$("#loginpass").val()) {
        alert("密码不能为空!");
        return false;
    }
    if (!$("#reloginpass").val()) {
        alert("确认密码不能为空!");
        return false;
    }
    if ($("#reloginpass").val() != $("#loginpass").val()) {
        alert("两次密码输入不一致!");
        return false;
    }
    if (!$("#email").val()) {
        alert("email不能为空!");
        return false;
    }
    if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#email").val())){
        alert("email格式不正确!")
        return false;
    }
    if(!validateEmail()){
        alert("email已存在!")
        return false;
    }
    return true;
}

function validateLoginname() {
    var value = $("#loginname").val();//获取输入框内容
    /*
     * 3. 是否注册校验
     */
    var flag = true;
    $.ajax({
        url: "/shop_drug_war_exploded/UserServlet",//要请求的servlet
        data: {method: "ajaxValidateLoginname", loginname: value},//给服务器的参数
        type: "POST",
        dataType: "json",
        async: false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
        cache: false,//是否缓存
        success: function (result) {
            if (!result) {//如果校验失败
                flag = false;
            }
        }
    });
    return flag;
}

/*
 * Email校验方法
 */
function validateEmail() {
    var value = $("#email").val();//获取输入框内容
    /*
     * 3. 是否注册校验
     */
    var flag = true;
    $.ajax({
        url:"/shop_drug_war_exploded/UserServlet",//要请求的servlet
        data:{method:"ajaxValidateEmail", email:value},//给服务器的参数
        type:"POST",
        dataType:"json",
        async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
        cache:false,
        success:function(result) {
            if(!result) {//如果校验失败
                flag = false;
            }
        }
    });
    return flag;
}