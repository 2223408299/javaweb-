$(function () {
    $("#formDiv").css("display", "none");
    $("#upPicture").css("display", "none");
    $("#show").css("display", "");
    var a = true;
    // 操作和显示切换
    $("#box").click(function () {
        // $("#show").hide();
        // $("#formDiv").show();
        if (a) {
            $("#show").css("display", "none");
            $("#formDiv").css("display", "");
            $("#upPicture").css("display", "none");
            a = false;
        } else {
            $("#formDiv").css("display", "none");
            $("#show").css("display", "");
            $("#upPicture").css("display", "none");
            a = true;
        }
    });
    var b = true;
    $("#box2").click(function () {
        // $("#show").hide();
        // $("#formDiv").show();
        if (b) {
            $("#show").css("display", "none");
            $("#upPicture").css("display", "");
            $("#formDiv").css("display", "none");
            b = false;
        } else {
            $("#upPicture").css("display", "none");
            $("#show").css("display", "");
            $("#formDiv").css("display", "none");
            b = true;
        }
    });
});

function checkForm() {
    if(!$("#drugName").val()) {
        alert("药品名称不能为空!");
        return false;
    }
    if(!$("#sign").val()) {
        alert("药品牌子不能为空!");
        return false;
    }
    if(!$("#drugDesc").val()) {
        alert("药品描述不能为空!");
        return false;
    }
    if(!$("#cost").val()) {
        alert("药品成本不能为空!");
        return false;
    }
    if(!$("#price").val()) {
        alert("药品价格不能为空!");
        return false;
    }
    if(!$("#shipAddress").val()) {
        alert("药品发货地不能为空!");
        return false;
    }
    if(!$("#pid").val()) {
        alert("药品一级分类不能为空!");
        return false;
    }
    if(!$("#cid").val()) {
        alert("药品二级分类不能为空!");
        return false;
    }
    return true;
}
function loadChildren() {
    /*
    1. 获取pid
    2. 发出异步请求，功能之：
      3. 得到一个数组
      4. 获取cid元素(<select>)，把内部的<option>全部删除
      5. 添加一个头（<option>请选择2级分类</option>）
      6. 循环数组，把数组中每个对象转换成一个<option>添加到cid中
    */
    // 1. 获取pid
    var pid = $("#pid").val();
    // 2. 发送异步请求
    $.ajax({
        async: true,
        cache: false,
        url: "/shop_drug_war_exploded/AdminDrugServlet",
        data: {method: "ajaxFindChildren", pid: pid},
        type: "POST",
        dataType: "json",
        success: function (arr) {
            // 3. 得到cid，删除它的内容
            $("#cid").empty();//删除元素的子元素
            $("#cid").append($("<option>====请选择2级分类====</option>"));//4.添加头
            // 5. 循环遍历数组，把每个对象转换成<option>添加到cid中
            for (var i = 0; i < arr.length; i++) {
                var option = $("<option>").val(arr[i].cid).text(arr[i].cname);
                $("#cid").append(option);
            }
        }
    });
}