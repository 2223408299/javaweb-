$(function() {
	$("#cnt").blur(function() {
		var quantity = $("#cnt").val();
		var drugId = $("#drugId").val();
		if(!/^[1-9]\d*$/.test(quantity)) {
			alert("数量必须是合法整数！");
			$("#cnt").val("1");
		}else{
			$.ajax({
				url:"/shop_drug_war_exploded/CartItemServlet",//要请求的servlet
				data:{method:"ajaxValidateInventories", quantity:quantity,drugId:drugId},//给服务器的参数
				type:"POST",
				dataType:"json",
				async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
				cache:false,//是否缓存
				success:function(result) {
					if(!result) {//如果校验失败
						alert("数量大于该商品的库存量！！");
						$("#cnt").val("1");
					}
				}
			});
		}

	});
	// $("#content").mouseleave(function(){
	// 	var value = $("#content").val();
	// 	var bool = true;
	// 	if(!value) {// 非空校验
	// 		alert("评价内容不能为空!");
	// 	} else if(value.length > 100) {//长度校验
	// 		alert("评价内容不能超过100字!");
	// 	}else if(value.indexOf("垃圾")!=-1){
	// 		alert("评论内容涉嫌违规!")
	// 	}
	// });
});
function checkForm() {
	var value = $("#content").val();
	var bool = true;
	if(!value) {// 非空校验
		alert("评价内容不能为空!");
		bool = false;
	} else if(value.length > 100) {//长度校验
		alert("评价内容不能超过100字!");
		bool = false;
	}else if(value.indexOf("垃圾")!=-1){
		alert("评论内容涉嫌违规!")
		bool = false;
	}
	return bool;
}