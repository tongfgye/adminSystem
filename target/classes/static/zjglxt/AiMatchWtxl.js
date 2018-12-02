function queryParams(params) {
	var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		pageSize: params.limit,   //页面大小
		pageNumber: params.pageNumber,  //页码
		sort: params.sort,  //排序列名
		sortOrder: params.order//排位命令（desc，asc）
	};
	return temp;
}

$(document).on('click', ".queryButton",function(){
  $('#table').bootstrapTable('refresh');
});

var xlbh = getQueryString("xlbh");

$("#table").bootstrapTable({ // 对应table标签的id
	url: aiMatch_path+"/AiReplay/wtxl/findByXlbhAndStateAndReStateWtxl?xlbh="+xlbh, // 获取表格数据的url
	cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
	striped: true,  //表格显示条纹，默认为false
	pagination: true, // 在表格底部显示分页组件，默认false
	pageList: [10,20,50], // 设置页面可以显示的数据条数
	pageSize: 10, // 页面数据条数
	pageNumber: 1, // 首页页码
	search:true,
	//sidePagination: 'server', // 设置为服务器端分页
	/* queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
		return {
			pageSize: params.limit, // 每页要显示的数据条数
			offset: params.offset, // 每页显示数据的开始行号
			sort: params.sort, // 要排序的字段
			sortOrder: params.order, // 排序规则
			dataId: $("#dataId").val() // 额外添加的参数
		}
	}, */
	sortName: 'wid', // 要排序的字段
	sortOrder: 'asc', // 排序规则
	columns: [
	 	{
			//field: 'wid', // 返回json数据中的name
			title: '序号', // 表格表头显示文字
			align: 'center', // 左右居中
			valign: 'middle' ,// 上下居中
			formatter: function (value, row, index) {
				return (index+1);								  
			}
		},{
			field: 'zbbh', // 返回json数据中的name
			title: '装备编号', // 表格表头显示文字
			align: 'center', // 左右居中
			valign: 'middle' // 上下居中
		}, {
			field: 'wtbh',
			title: '问题编号',
			align: 'center',
			valign: 'middle'
		}, {
			field: 'wtnr',
			title: '问题内容',
			align: 'center',
			valign: 'middle'
		}, 
		 
		{
			field: 'xlbzText',
			title: '修理步骤',
			align: 'center',
			valign: 'middle'
		},
		{
			field: 'createTime',
			title: '创建时间',
			align: 'center',
			valign: 'middle'
		},{
			title: "操作",
			align: 'center',
			valign: 'middle',
			width: 160, // 定义列的宽度，单位为像素px
			formatter: function (value, row, index) {
				return '<button class="btn btn-primary btn-sm" onclick="detail(\'' + row.wid + '\')">详情</button>';								  
			}
		}
	],
	onLoadSuccess: function(){  //加载成功时执行
		console.info("加载成功");
	},
	onLoadError: function(){  //加载失败时执行
		console.info("加载数据失败");
	}

});




function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&*])(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}



function detail(id){	
	$("#myModal").modal();
	$.ajax({
		type: 'get',
		url: aiMatch_path+"/AiReplay/wtxl/findById?id="+id,
		cache: false,
		processData: false,
		contentType: false,
		}).success(function (data) {
			//window.location.reload();	
			$("#wid").val(data.wid);			
			$("#zbbh").val(data.zbbh);
			$("#wtbh").val(data.wtbh);
			$("#wtnr").val(data.wtnr);
			$("#xlbh").val(data.xlbh);
			$("#xlbz").val(data.xlbz);
			CKEDITOR.instances.xlbz.setData(data);
		}).error(function () {
			alert("服务忙，请联系管理员");
		}); 
				
};



	
	
$(function() {
	var name = cookiesManager.get('username');
	if(null == name || name == ""){
		window.location.href="../login/login.html";
	}
})	