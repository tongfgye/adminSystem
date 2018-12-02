

$("#table").bootstrapTable({ // 对应table标签的id
	url: aiMatch_path+"/findByStateAndReStateWtxl", // 获取表格数据的url
	cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
	striped: true,  //表格显示条纹，默认为false
	pagination: true, // 在表格底部显示分页组件，默认false
	pageList: [10,20,50], // 设置页面可以显示的数据条数
	pageSize: 10, // 页面数据条数
	pageNumber: 1, // 首页页码
	search:true,
	paginationLoop:false,
	//sidePagination: 'server', // 设置为服务器端分页
 queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
		return {
			pageSize: params.limit, // 每页要显示的数据条数
			offset: params.offset, // 每页显示数据的开始行号
			sort: params.sort, // 要排序的字段
			sortOrder: params.order, // 排序规则
			
		}
	}, 

	sortName: 'wid', // 要排序的字段
	sortOrder: 'desc', // 排序规则
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
			valign: 'middle',
			visible:false
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
				
				return '<button class="btn btn-primary btn-sm" onclick="update(\'' + row.wid + '\')">详情</button>	';								  
				
			}
		}
	],
	onLoadSuccess: function(){  //加载成功时执行
		console.info("加载成功");
	},
	onLoadError: function(){  //加载失败时执行
		console.info("加载数据失败");
	},
	onDblClickRow:function(row){  //
						window.location.href="AiMatchDetail.html?wid="+row.wid;
						},

});


function refuse(id){
		$.ajax({
			type: 'get',
			url: system_export_path+"/deleteWtxl/"+id,
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


function update(id){	
	$("#myModal").modal();
	$.ajax({
		type: 'get',
		url: aiMatch_path+"/findById?id="+id,
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
								url: system_export_path+"/uploadimg1",
								data: formData,
								cache: false,
								processData: false,
								contentType: false,
							}).success(function (data) {
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
	$("#wid").val("");
		$("#zbbh").val("");
		$("#wtbh").val("");
		$("#wtnr").val("");
		$("#xlbh").val("");
		$("#xlbz").val("");
		$("#xlbzText").val("");
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
	var zbbh = $("#zbbh").val().trim();
	var wtbh = $("#wtbh").val().trim();
	var wtnr = $("#wtnr").val().trim();
	var xlbh = $("#xlbh").val().trim();
	var xlbz=$("#xlbz").val(CKEDITOR.instances.xlbz.getData().trim());// CKEDITOR.instances.xlbh.getData();
	var xlbzText=$("#xlbzText").val(CKEDITOR.instances.xlbz.document.getBody().getText().trim());
	if(zbbh ==""){
		alert("装备编号不能为空");
		return;
	}
	if(wtbh ==""){
		alert("问题编号不能为空");
		return;
	}
	if(wtnr ==""){
		alert("装备内容不能为空");
		return;
	}
	if(xlbh ==""){
		alert("修理编号不能为空");
		return;
	}
	if(xlbz ==""){
		alert("修理步骤不能为空");
		return;
	}
	var formData = new FormData($('#saveOrUpdate')[0]);
	$.ajax({
		type: 'post',
		url: system_export_path+"/wtxlSave",
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
	
	
$(function() {
	var name = cookiesManager.get('username');
	if(null == name || name == ""){
		window.location.href="../login/login.html";
	}
})	