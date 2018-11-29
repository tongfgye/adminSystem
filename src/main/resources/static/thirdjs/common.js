var cookiesManager = {
    set: function(key, value) {
        if (window.localStorage) {
            window.localStorage.setItem(key, value);
        } else {
            var exdate = new Date();
            var expiredays = 15;
            exdate.setDate(exdate.getDate() + expiredays);
            document.cookie = key+ "=" + escape(value) + ((expiredays == null) ? "" : ";expires="+exdate.toGMTString());
        }
    },
    get: function(key) {
        if (window.localStorage) return window.localStorage.getItem(key);
        else {
            if (document.cookie.length > 0) {
                c_start = document.cookie.indexOf(c_name + "=");
                if (c_start != -1) { 
                    c_start = c_start + c_name.length + 1;
                    c_end = document.cookie.indexOf(";", c_start);
                    if (c_end == -1) c_end = document.cookie.length;
                    return unescape(document.cookie.substring(c_start, c_end));
                } 
            }
            return "";
        }
    },
    del: function(key) {
        if (window.localStorage) localStorage.removeItem(key);
        else {
            var exp = new Date(); 
            exp.setTime(exp.getTime() - 1); 
            var cval = getCookie(name); 
            if(cval != null) document.cookie = name + "=" + cval+";expires=" + exp.toGMTString();
        }
    },
    clearCookie: function() { 
        if (window.localStorage) localStorage.clear();
        else {
            var keys = document.cookie.match(/[^ =;]+(?=\=)/g); 
            if (keys) { 
                for (var i = keys.length; i--;) {
                    document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
                }
            } 
        }
    }
}

function getUrlParaData(paraObj, paras){ 
    var returnValue = paraObj[paras.toLowerCase()];  
    if (typeof(returnValue) == "undefined") {  
        return "";  
    } else {  
        return returnValue; 
    }
}
function getUrlParaObj() {
    var url = location.href;  
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");  
    var paraObj = {};
    for (i = 0; j = paraString[i]; i++) {  
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);  
    } 
    return paraObj;
}

var clickTime = 0;
var spanTimer = 200;
function replaceUrl(url) {
    var nowTime = new Date().getTime();
    if (nowTime - clickTime > spanTimer) {
        clickTime = nowTime;
        setTimeout(function() {
            window.location.replace(url);
        }, spanTimer);
    }
}
function goUrl(url) {
    var nowTime = new Date().getTime();
    if (nowTime - clickTime > spanTimer) {
        clickTime = nowTime;
        setTimeout(function() {
            window.location.href = url; 
        }, spanTimer);
    }
}
function goBack() {
    var nowTime = new Date().getTime();
    if (nowTime - clickTime > spanTimer) {
        clickTime = nowTime;
        setTimeout(function() {
            window.history.go(-1);
        }, spanTimer);
    }
}

//ajax
function requestAjax(url,dataVar,fnName) {
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: dataVar,
        success: function(data) {
            if(data.head.code == 1) {
                fnName(data);
            } else {
                alert(data.head.desc);
            }
        },
        error: function(data) {
            var dataError = data;
        }
    })
}

var login_path = "http://localhost:8092";
var ckeditor_path = "http://localhost:8093";
var system_export_path = "http://localhost:8095";
var aiMatch_path = "http://localhost:8094";