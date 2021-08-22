$(function () {
    /*
    给全选添加click事件
    */
    $("#selectAll").click(function () {
        /*
        1. 获取全选的状态
        */
        var bool = $("#selectAll").prop("checked");
        /*
        2. 让所有条目的复选框与全选的状态同步
        */
        setItemCheckBox(bool);
    });

    /*
    给所有条目的复选框添加click事件
    */
    $(":checkbox[name=checkboxBtn]").click(function () {
        var all = $(":checkbox[name=checkboxBtn]").length;//所有条目的个数
        var select = $(":checkbox[name=checkboxBtn]:checked").length;//获取所有被选择条目的个数
        if (all == select) {//全都选中了
            $("#selectAll").prop("checked", true);//勾选全选复选框
        } else {
            $("#selectAll").prop("checked", false);//取消全选
        }
    });
})

/*
 * 统一设置所有条目的复选按钮
 */
function setItemCheckBox(bool) {
    $(":checkbox[name=checkboxBtn]").prop("checked", bool);
}

/*
 * 批量删除
 */
function batchDelete() {
    // 1. 获取所有被选中条目的复选框
    // 2. 创建一数组，把所有被选中的复选框的值添加到数组中
    // 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
    var userIdArray = new Array();
    $(":checkbox[name=checkboxBtn]:checked").each(function () {
        userIdArray.push($(this).val());//把复选框的值添加到数组中
    });
    if (confirm("您是否真要删除所选中的用户？")) {
        location = "/shop_drug_war_exploded/AdminUserServlet?method=batchDelete&userIds=" + userIdArray;
    }
}

/*
 * 批量封号
 */
function batchLock() {
    // 1. 获取所有被选中条目的复选框
    // 2. 创建一数组，把所有被选中的复选框的值添加到数组中
    // 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
    var userIdArray = new Array();
    $(":checkbox[name=checkboxBtn]:checked").each(function () {
        userIdArray.push($(this).val());//把复选框的值添加到数组中
    });
    if (confirm("您是否真要对所选中的用户进行封号？")) {
        location = "/shop_drug_war_exploded/AdminUserServlet?method=batchLock&condition2=lock&userIds=" + userIdArray;
    }
}

/*
 * 批量解封
 */
function batchUnlock() {
    // 1. 获取所有被选中条目的复选框
    // 2. 创建一数组，把所有被选中的复选框的值添加到数组中
    // 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
    var userIdArray = new Array();
    $(":checkbox[name=checkboxBtn]:checked").each(function () {
        userIdArray.push($(this).val());//把复选框的值添加到数组中
    });
    if (confirm("您是否真要对所选中的用户进行解封？")) {
        location = "/shop_drug_war_exploded/AdminUserServlet?method=batchLock&condition2=unlock&userIds=" + userIdArray;
    }
}