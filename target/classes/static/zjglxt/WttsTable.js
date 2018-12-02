function queryParams(params) {
	var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		pageSize: params.limit,   //页面大小
		pageNumber: params.pageNumber,  //页码
		reState: $("#reState").val(),
		//state: $("#state").val(),
		//endDate: $("#end_time").val(),
		sort: params.sort,  //排序列名
		sortOrder: params.order//排位命令（desc，asc）
	};
	return temp;
}

$(document).on('click', ".queryButton",function(){
  $('#ts_table').bootstrapTable('refresh');
});



$("#ts_table").bootstrapTable({ // 对应table标签的id
	url: system_export_path+"/findByStateAndReState", // 获取表格数据的urlfindByStateAndReState
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
	/* queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
		return {
			pageSize: params.limit, // 每页要显示的数据条数
			offset: params.offset, // 每页显示数据的开始行号
			sort: params.sort, // 要排序的字段
			sortOrder: params.order, // 排序规则
			dataId: $("#dataId").val() // 额外添加的参数
		}
	}, */
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
			},
			{
				field: 'state',
				title: '状态',
				align: 'center',
				valign: 'middle',
				formatter: function (value, row, index) {
					var text = '-';
					if (row.state == 0) {
							text = "新增";
					} else if (row.state == 1) {
							text = "修改";
					} else if (row.state == 2) {
							text = "删除";
					}else if (row.state == 3) {
							text = "生效";
					}
					return text;						  
				}
			},
			{
				field: 'reState',
				title: '审核状态',
				align: 'center',
				valign: 'middle',
				formatter: function (value, row, index) {
					var text = '-';
					if (value == 0) {
							text = "待审核";
					} else if (value == 1) {
							text = "审核通过";
					} else if (value == 2) {
							text = "审核拒绝";
					}
					return text;						  
				}
			},{
				title: "操作",
				align: 'center',
				valign: 'middle',
				width: 200, // 定义列的宽度，单位为像素px
				formatter: function (value, row, index) {
					if (row.reState == 1) {
								return '<button class="btn btn-primary btn-sm" onclick="toRepair(\'' + row.xlbh + '\')">转入</button>	&nbsp<button class="btn btn-primary btn-sm" onclick="update(\'' + row.tsId + '\')">修改</button>	&nbsp<button class="btn btn-primary btn-sm" onclick="refuse(\'' + row.tsId + '\')">删除</button>' ;								
							
					}else if (row.reState == 0) {
								return "-";
					}
					else if (row.reState == 2) {
						return '<button class="btn btn-primary btn-sm" onclick="update(\'' + row.tsId + '\')">修改</button>	&nbsp<button class="btn btn-primary btn-sm" onclick="refuse(\'' + row.tsId + '\')">删除</button>' ;								
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


function refuse(id){
		$.ajax({
			type: 'get',
			url: system_export_path+"/deleteWtts/"+id,
			cache: false,
			processData: false,
			contentType: false,
			}).success(function (data) {
					alert("删除申请已提交，请耐心等待管理员审核");
					window.location.reload();
			}).error(function () {
				alert("提交删除申请失败");
			}); 
}

function toRepair(xlbh){
	$.ajax({
		type: 'get',
		url: system_export_path+"/findByXlbh/"+xlbh,
		cache: false,
		processData: false,
		contentType: false,
		}).success(function (data) {
			if(data==null || data ==""){
				alert("未找到相应的修理步骤！");
			}else{
				$("#wid").val(data.wid);			
				$("#zbbh2").val(data.zbbh);
				$("#wtbh").val(data.wtbh);
				$("#wtnr").val(data.wtnr);
				$("#xlbh2").val(data.xlbh);
				$("#xlbz").val(data.xlbz);
				CKEDITOR.instances.xlbz.setData(data)
				$("#myModal2").modal();
			}
			
		}).error(function () {
			alert("未找到相应的修理步骤！");
		}); 
	

	
	
}

function update(id){	
	$("#myModal").modal();
	$.ajax({
		type: 'get',
		url: system_export_path+"/findByTsId?id="+id,
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

function deleteWtts(id){	
	if(confirm("确认要删除当前记录吗？")){		
		$.ajax({
			type: 'get',
			url: system_export_path+"/deleteWtts/"+id,
			cache: false,
			processData: false,
			contentType: false,
			}).success(function (data) {
				alert("删除成功！");
				window.location.reload();
			}).error(function () {
				alert("服务忙，请联系管理员");
			}); 
				
	};
}
	


//toDo_-->
$("#myModal").on("hidden.bs.modal", function() {
	$(this).removeData("bs.modal");
});



$(function() {
	$("#upload").click(function () {
			
			//判断文件上传类型
			var fileSize = 0;
			//文件类型
			var filetypes = [".xls"];
			var filepath = document.getElementById("file").value;
			if(filepath == null || filepath==""){
				alert("请选择要上传excel格式的文件");
				return false;
			}
			var isnext = false;
			var fileend = filepath.substring(filepath.lastIndexOf("."));
			if (filetypes && filetypes.length > 0) {
				for (var i = 0; i < filetypes.length; i++) {
					if (filetypes[i] == fileend) {
						isnext = true;
						break;
					}
				}
			}
			if (!isnext) {
				alert('不接受此文件类型,请上传excel格式的文件！\r\n');
				return false;
			}

			//判断上传文件的大小
			fileSize = document.getElementById("file").files[0].size;
			var size = fileSize / 1024;
			var filemaxsize = 1024 * 2;//2M
			if (size > filemaxsize) {
				alert('附件大小不能大于' + filemaxsize / 1024 +'M！\r\n', );
				return false;
			}

			else {
			   var formData = new FormData($('#uploadForm')[0]);
							$.ajax({
								type: 'post',
								url: system_export_path+"/uploadWtts",
								data: formData,
								cache: false,
								processData: false,
								contentType: false,
							}).success(function (data) {
								// if (data == "success") {
								// window.location.reload();
								// alert("文件导入成功");
								// } else {
								// alert("上传失败");
								// }
								if (data.code == "1") {

									alert(data.successCount + "\n"
											+ data.failCount + "\n"
											+ data.message);
								} else {
									alert("上传失败");
								}

								
							}).error(function () {
								alert("上传失败");
							});
			}
	})
});
function newSave(){
	 $("#myModal").modal();
		$("#tsId").val("");
		$("#zbbh").val("");
		$("#csbh").val("");
		$("#csrw").val("");
		$("#csjg").val("");
		$("#zrbh").val("");
		$("#zrsm").val("");
		$("#sfzr").val("");
		$("#xlbh").val("");
		$("#csrwText").val("");
}
/*$("#newSave").click(function(){
		 $("#myModal").modal();
		$("#wid").val("");
		$("#zbbh").val("");
		$("#wtbh").val("");
		$("#wtnr").val("");
		$("#xlbh").val("");
		$("#xlbz").val("");
										
	})					
*/					
/*$(function(){
	
	
	
})
*/								
								
function updateForm(){	
	
	var zbbh=$("#zbbh").val().trim();
	var csbh = $("#csbh").val().trim();
	// var csrw = $("#csrw").val().trim();
	var csjg = $("#csjg").val().trim();
	var zrbh = $("#zrbh").val().trim();
	var zrsm = $("#zrsm").val().trim();
	var sfzr = $("#sfzr").val().trim();
	
	var csrw=$("#csrw").val(CKEDITOR.instances.csrw.getData().trim());// CKEDITOR.instances.xlbh.getData();
	var csrwText=$("#csrwText").val(CKEDITOR.instances.csrw.document.getBody().getText().trim());
	
	if(zbbh ==""){
		alert("装备编号不能为空");
		return;
	}
	if(csbh ==""){
		alert("测试编号不能为空");
		return;
	}
	if(csrw ==""){
		alert("测试任务不能为空");
		return;
	}
	if(csjg ==""){
		alert("测试结果不能为空");
		return;
	}
	if(zrbh ==""){
		alert("转入编号不能为空");
		return;
	}
	if(zrsm ==""){
		alert("转入说明不能为空");
		return;
	}
	if(sfzr ==""){
		alert("是否转入不能为空");
		return;
	}
var formData = new FormData($('#saveOrUpdate')[0]);
	$.ajax({
		type: 'post',
		url: system_export_path+"/wttsSave",
		data: formData,
		cache: false,
		processData: false,
		contentType: false,
	}).success(function (data) {
		alert("提交成功了！！");
		window.location.reload();
	}).error(function () {
		alert("提交失败，请联系管理员");
	});
}

//$(function() {
//	var name = cookiesManager.get('username');
//	if(null == name || name == ""){
//		window.location.href="../login/login.html";
//	}
//})
