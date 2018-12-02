
/* 
$(function() {
	// alert("asdfjlasjflsdjj");
	var wid = '4';
	// var wid = getQueryString("wid");
	$.ajax({
		type: 'get',
		url: aiMatch_path + "/AiReplay/wtts/findByTsId?id=" + wid,
		cache: false,
		processData: false,
		contentType: false,
	}).success(function(res) {
		if (res != null) {
			vm.list = res.body;
			// appendWizardVHtml();
			
		}
	}).error(function() {
		alert("服务忙，请联系管理员");
	});
}) */


var wtbh = getQueryString("wtbh");

var fucsbh = getQueryString("zrbh");

$("#Aitable").bootstrapTable({ // 对应table标签的id
	url: aiMatch_path + "/AiReplay/wtts/findByWtbhAndFucsbh?wtbh=" + wtbh+"&fucsbh="+fucsbh, 
	cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
	striped: true,  //表格显示条纹，默认为false
	pagination: true, // 在表格底部显示分页组件，默认false
	pageList: [10,20,50], // 设置页面可以显示的数据条数
	pageSize: 10, // 页面数据条数
	pageNumber: 1, // 首页页码
	search:true,
	showRefresh:true,
	toolbar: true,
	//sidePagination: 'server', // 设置为服务器端分页
	 queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
		return {
			pageSize: params.limit, // 每页要显示的数据条数
			offset: params.offset, // 每页显示数据的开始行号
			sort: params.sort, // 要排序的字段
			sortOrder: params.order, // 排序规则
			
		}
	}, 
	sortName: 'tsId', // 要排序的字段
	sortOrder: 'asc', // 排序规则
	columns: [
			{
 	     checkbox: true, // 显示一个勾选框
 	      align: 'center' // 居中显示
			},
	
			{
				title: "序号",
				align: 'center',
				valign: 'middle',
				formatter: function (value, row, index) {
					return (index+1);								  
				}
			},{
				field: 'zbbh', // 返回json数据中的name
				title: '装备编号', // 表格表头显示文字
				align: 'center', // 左右居中
				valign: 'middle' // 上下居中
			}, {
				field: 'csbh',
				title: '测试编号',
				align: 'center',
				valign: 'middle'
			}, {
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
			}
			, 
			{
				field: 'zrsm',
				title: '转入说明',
				align: 'center',
				valign: 'middle'
			}
			, 
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
			},{
				title: "操作",
				align: 'center',
				valign: 'middle',
				width: 200, // 定义列的宽度，单位为像素px
				formatter: function(value, row, index) {
					
					if (row.sfzr == "是") {
						return '<button class="btn btn-primary btn-sm" onclick="toRepair(\'' +
							row.xlbh +
							'\')">转入修理</button>&nbsp<button class="btn btn-primary btn-sm" onclick="detail(\'' +
							row.tsId +
							'\')">详情</button>	';

					} else {
						return '<button class="btn btn-primary btn-sm" onclick="nextStep(\'' + row.wtbh + '\',\''+row.zrbh+'\')">下一步</button>&nbsp<button class="btn btn-primary btn-sm" onclick="detail(\'' +
							row.tsId +
							'\')">详情</button>';
					}
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


function detail(id){	
	$("#myModal").modal();
	$.ajax({
		type: 'get',
		url: aiMatch_path+"/AiReplay/wtts/findByTsId?id="+id,
		cache: false,
		processData: false,
		contentType: false,
		}).success(function (data) {
			 //  String csbh, String csrw, String csjg, String zrbh, String zrsm, String sfzr
			//window.location.reload();	
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
		}).error(function () {
			alert("服务忙，请联系管理员");
		}); 
				
};


function toRepair(xlbh){
	window.location.href = "AiMatchWtxl.html?xlbh=" + xlbh ;
}

function nextStep(wtbh, zrbh) {
	window.location.href = "AiMatchDetailWtts.html?wtbh=" + wtbh + "&zrbh=" + zrbh;
}


function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&*])(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}


