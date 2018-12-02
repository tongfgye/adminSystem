var vm = new Vue({
	el: '#app',
	data: {
		list: 'master',
	}
})

$(function() {
	// alert("asdfjlasjflsdjj");
	var wid = '4';
	// var wid = getQueryString("wid");
	$.ajax({
		type: 'get',
		url: aiMatch_path + "/findByTsId?id=" + wid,
		cache: false,
		processData: false,
		contentType: false,
	}).success(function(res) {
		if (res != null) {
			vm.list = res.body;
			// appendWizardVHtml();
			initializeDelay();
		}
	}).error(function() {
		alert("服务忙，请联系管理员");
	});
})

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&*])(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}

function appendWizardVHtml() {
	
}
