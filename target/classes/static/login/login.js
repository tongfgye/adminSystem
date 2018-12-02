$("#login")
		.click(
				function() {

					// var name = cookiesManager.get('username');
					var username = $("#username").val().trim();
					var password = $("#password").val().trim();

					if (username == "") {
						alert("请输入用户名。。。");
					} else if (password == "") {
						alert("请输入密码。。。");
					} else {
						$
								.ajax({
									type : 'post',
									url : login_path + "/users/login",
									data : {
										"name" : username,
										"password" : password
									},
								// cache: false,
								// processData: false,
								// contentType: false,

								})
								.success(
										function(data) {

											// alert("登录成功");
											if (data == "fail") {
												alert("登陆失败");
											} else {
												cookiesManager.set('username',
														username);
												cookiesManager
														.set('islogin', 1);
												window.location.href = "../zjglxt/WttsTable.html?name="
														+ username;

											}

										}).error(function() {
									alert("登陆失败");
								});
					}
				});