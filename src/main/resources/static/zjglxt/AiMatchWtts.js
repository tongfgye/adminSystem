function queryParams(params) {
	var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		pageSize: params.limit, //页面大小
		pageNumber: params.pageNumber, //页码
		wtms: $("#wtms").val(),
		//endDate: $("#end_time").val(),
		sort: params.sort, //排序列名
		sortOrder: params.order //排位命令（desc，asc）
	};
	return temp;
	/* var param = {};
    $('#searchF').find('[name]').each(function () {
        var value = $(this).val();
        if (value != '') {
            param[$(this).attr('name')] = value;
        }
    });

    param['pageSize'] = params.limit;   //页面大小
    param['pageNumber'] = params.offset;   //页码

    return param; */

}

$(document).on('click', ".queryButton", function() {
	$('#ts_table').bootstrapTable('refresh');
});

$("#ts_table")
	.bootstrapTable({ // 对应table标签的id
		url: aiMatch_path + "/AiReplay/wtts/findByWtms", // 获取表格数据的urlfindByStateAndReState
		cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
		striped: true, // 表格显示条纹，默认为false
		pagination: true, // 在表格底部显示分页组件，默认false
		pageList: [10, 20, 50], // 设置页面可以显示的数据条数
		pageSize: 10, // 页面数据条数
		pageNumber: 1, // 首页页码
		search: true,
		showRefresh: true,
		toolbar: true,
		// sidePagination: 'server', // 设置为服务器端分页
		/* queryParams: function(params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
			return {
				pageSize: params.limit, // 每页要显示的数据条数
				offset: params.offset, // 每页显示数据的开始行号
				sort: params.sort, // 要排序的字段
				sortOrder: params.order, // 排序规则
				// dataId: $("#dataId").val() // 额外添加的参数
			}
		} ,*/
		sortName: 'tsId', // 要排序的字段
		sortOrder: 'asc', // 排序规则
		columns: [{
				checkbox: true, // 显示一个勾选框
				align: 'center' // 居中显示
			},

			{
				title: "序号",
				align: 'center',
				valign: 'middle',
				formatter: function(value, row, index) {
					return (index + 1);
				}
			},
			{
				field: 'zbbh', // 返回json数据中的name
				title: '装备编号', // 表格表头显示文字
				align: 'center', // 左右居中
				valign: 'middle' // 上下居中
			},
			{
				field: 'wtbh',
				title: '问题编号',
				align: 'center',
				valign: 'middle'
			}, {
				field: 'wtms',
				title: '问题描述',
				align: 'center',
				valign: 'middle'
			},
			/* {
				field: 'csrwText',
				title: '测试任务',
				align: 'center',
				valign: 'middle'
			}, */

			{
				field: 'zrbh',
				title: '转入编号',
				align: 'center',
				valign: 'middle'
			},
			{
				field: 'zrsm',
				title: '转入说明',
				align: 'center',
				valign: 'middle'
			},
			{
				field: 'sfzr',
				title: '是否转入修理',
				align: 'center',
				valign: 'middle'
			},
			{
				field: 'xlbh',
				title: '修理编号',
				align: 'center',
				valign: 'middle'
			},
			{
				field: 'createTime',
				title: '创建时间',
				align: 'center',
				valign: 'middle'
			},
			{
				title: "操作",
				align: 'center',
				valign: 'middle',
				width: 200, // 定义列的宽度，单位为像素px
				formatter: function(value, row, index) {
					
						return '<button class="btn btn-primary btn-sm" onclick="nextStep(\'' + row.wtbh + '\',\''+row.zrbh+'\')">下一步</button>';

					
				}
			}
		],
		onLoadSuccess: function() { // 加载成功时执行
			console.info("加载成功");
		},
		onLoadError: function() { // 加载失败时执行
			console.info("加载数据失败");
		},
		onDblClickRow: function(row) { //
			window.location.href = "AiMatchDetailWtts.html?wtbh=" + row.wtbh + "&fucsbh=" + row.zrbh;
		},

	});

function nextStep(wtbh, zrbh) {
	window.location.href = "AiMatchDetailWtts.html?wtbh=" + wtbh + "&zrbh=" + zrbh;
}

function refuse(id) {
	$.ajax({
		type: 'get',
		url: system_export_path + "/deleteWtts/" + id,
		cache: false,
		processData: false,
		contentType: false,
	}).success(function(data) {
		alert("删除申请已提交，请耐心等待管理员审核");
		window.location.reload();
	}).error(function() {
		alert("提交删除申请失败");
	});
}

function toRepair(xlbh) {
	$.ajax({
		type: 'get',
		url: system_export_path + "/findByXlbh/" + xlbh,
		cache: false,
		processData: false,
		contentType: false,
	}).success(function(data) {
		if (data == null || data == "") {
			alert("未找到相应的修理步骤！");
		} else {
			$("#wid").val(data.wid);
			$("#zbbh2").val(data.zbbh);
			$("#wtbh").val(data.wtbh);
			$("#wtnr").val(data.wtnr);
			$("#xlbh2").val(data.xlbh);
			$("#xlbz").val(data.xlbz);
			CKEDITOR.instances.xlbz.setData(data)
			$("#myModal2").modal();
		}

	}).error(function() {
		alert("未找到相应的修理步骤！");
	});

}

function update(id) {
	$("#myModal").modal();
	$.ajax({
		type: 'get',
		url: system_export_path + "/findByTsId?id=" + id,
		cache: false,
		processData: false,
		contentType: false,
	}).success(function(data) {
		// String csbh, String csrw, String csjg, String zrbh, String zrsm,
		// String sfzr
		// window.location.reload();
		$("#tsId").val(data.tsId);
		$("#zbbh").val(data.zbbh);
		$("#csbh").val(data.csbh);
		$("#csjg").val(data.csjg);
		$("#zrbh").val(data.zrbh);
		$("#zrsm").val(data.zrsm);
		$("#sfzr").val(data.sfzr);
		$("#xlbh").val(data.xlbh);
		$("#csrw").val(data.csrw);
		CKEDITOR.instances.csrw.setData(data);
	}).error(function() {
		alert("服务忙，请联系管理员");
	});

};

$("button")
	.click(
		function() {
			var wtms = "";
			// $.ajax({
			// type : 'get',
			// url : system_export_path + "/findByWtms?wtms=" + wtms,
			// cache : false,
			// processData : false,
			// contentType : false,
			// }).success(function(data) {
			//
			//		
			// }).error(function() {
			// alert("服务忙，请联系管理员");
			// });
			$("#ts_table")
				.bootstrapTable({ // 对应table标签的id
					url: aiMatch_path +
						"/findByWtms?wtms=" + wtms, // 获取表格数据的urlfindByStateAndReState
					cache: false, // 设置为 false 禁用 AJAX
					// 数据缓存， 默认为true
					striped: true, // 表格显示条纹，默认为false
					pagination: true, // 在表格底部显示分页组件，默认false
					pageList: [10, 20, 50], // 设置页面可以显示的数据条数
					pageSize: 10, // 页面数据条数
					pageNumber: 1, // 首页页码
					search: true,
					showRefresh: true,
					toolbar: true,
					// sidePagination: 'server', //
					// 设置为服务器端分页
					queryParams: function(params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
						return {
							pageSize: params.limit, // 每页要显示的数据条数
							offset: params.offset, // 每页显示数据的开始行号
							sort: params.sort, // 要排序的字段
							sortOrder: params.order, // 排序规则
							// dataId: $("#dataId").val() //
							// 额外添加的参数
						}
					},
					sortName: 'tsId', // 要排序的字段
					sortOrder: 'asc', // 排序规则
					columns: [{
							checkbox: true, // 显示一个勾选框
							align: 'center' // 居中显示
						},

						{
							title: "序号",
							align: 'center',
							valign: 'middle',
							formatter: function(value,
								row, index) {
								return (index + 1);
							}
						},
						{
							field: 'zbbh', // 返回json数据中的name
							title: '装备编号', // 表格表头显示文字
							align: 'center', // 左右居中
							valign: 'middle' // 上下居中
						},
						{
							field: 'csbh',
							title: '测试编号',
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'csrwText',
							title: '测试任务',
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'csjg',
							title: '测试结果',
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'zrbh',
							title: '转入编号',
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'zrsm',
							title: '转入说明',
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'sfzr',
							title: '是否转入',
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'xlbh',
							title: '修理编号',
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'createTime',
							title: '创建时间',
							align: 'center',
							valign: 'middle'
						},
						{
							title: "操作",
							align: 'center',
							valign: 'middle',
							width: 200, // 定义列的宽度，单位为像素px
							formatter: function(value,
								row, index) {
								if (row.reState == 1) {
									return '<button class="btn btn-primary btn-sm" onclick="toRepair(\'' +
										row.xlbh +
										'\')">转入</button>	&nbsp<button class="btn btn-primary btn-sm" onclick="update(\'' +
										row.tsId +
										'\')">修改</button>	&nbsp<button class="btn btn-primary btn-sm" onclick="refuse(\'' +
										row.tsId +
										'\')">删除</button>';

								} else if (row.reState == 0) {
									return "-";
								} else if (row.reState == 2) {
									return '<button class="btn btn-primary btn-sm" onclick="update(\'' +
										row.tsId +
										'\')">修改</button>	&nbsp<button class="btn btn-primary btn-sm" onclick="refuse(\'' +
										row.tsId +
										'\')">删除</button>';
								}
							}
						}
					],
					onLoadSuccess: function() { // 加载成功时执行
						console.info("加载成功");
					},
					onLoadError: function() { // 加载失败时执行
						console.info("加载数据失败");
					},
					onDblClickRow: function(row) { //
						window.location.href = "AiMatchDetailWtts.html?wid=" +
							row.tsId;
					},

				});

		});

$(function() {
	var name = cookiesManager.get('username');
	if (null == name || name == "") {
		window.location.href = "../login/login.html";
	}
})
