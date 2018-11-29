////页面加载事件
//$(function() {
//	var usernamelogin = cookiesManager.get('username');
//	var islogin = cookiesManager.get('islogin');
//	if (usernamelogin == "" || islogin != "1") {
//		window.location.href = "login.html";
//	}
//})

// 注册事件
$("#register").click(function() {

	var username = $("#username").val().trim();
	var userpassword = $("#userpassword").val().trim();
	var userrealpassword = $("#userrealpassword").val().trim();
	var useremail = $("#useremail").val().trim();
	var useraddress = $("#useraddress").val().trim();
	var userage = $("#userage").val().trim();
	if (username == "") {
		alert("请输入用户名");
	}
	// else if (useremail == "") {
	// alert("请输入邮件地址");
	// } else if (useraddress == "") {
	// alert("请输入用户地址");
	// } else if (userpassword == "") {
	// alert("请输入密码");
	// } else if (userage == "") {
	// alert("输入年龄。。。");
	// } else if (userage > 150 || userage < 1) {
	// alert("输入年龄不合法。。。");
	// }
	else if (userrealpassword == "") {
		alert("请输入确认密码");
	} else if (userpassword != userrealpassword) {
		alert("两次输入的密码不一致。。。");

	} else {
		$.ajax({
			type : 'post',
			url : login_path + "/users/register",
			data : {
				"username" : username,
				"userpassword" : userpassword,
				"userrealpassword" : userrealpassword,
				"useremail" : useremail,
				"useraddress" : useraddress,
				"userage" : userage
			},
		// cache: false,
		// processData: false,
		// contentType: false,

		}).success(function(data) {
			if (data == "sucess") {
				alert("注册成功。。。");
				window.location.href = "login.html";
			} else {
				alert("用户已存在，请更换用户名，重新注册。。。");
			}
		}).error(function() {
			alert("注册失败。。。");
		});
	}
});