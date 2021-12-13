function Utils() {
}

let utils = new Utils();

Date.prototype.format = function(format)
{
  var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(), //day
    "h+" : this.getHours(), //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3), //quarter
    "S" : this.getMilliseconds() //millisecond
  }
  if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)
    if(new RegExp("("+ k +")").test(format))
      format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
  return format;
}

Utils.prototype.getDeviceInfo = function () {
  let Browser;
  const explorer = navigator.userAgent;
  // IE  判断浏览器是否支持ActiveX控件，如果浏览器支持ActiveX控件可以利用,
  if (!!window.ActiveXObject || "ActiveXObject" in window) {
    Browser = 'IE';
  } else if (window.document.documentMode) {
    Browser = 'IE';
  } else if (explorer.indexOf("Edg") >= 0) {
    Browser = 'Edge';
  } else if (explorer.indexOf("Firefox") >= 0) {
    Browser = 'Firefox';
  } else if (explorer.indexOf("Chrome") >= 0) {
    Browser = 'Chrome';
  } else if (explorer.indexOf("Opera") >= 0) {
    Browser = 'Opera';
  } else if (explorer.indexOf("Safari") >= 0) {
    Browser = 'Safari';
  } else if (explorer.indexOf("Netscape") >= 0) {
    Browser = 'Netscape';
  } else {
    Browser = 'Un-know Device';
  }
  return Browser;
}

Utils.prototype.getProductType = function(productId){
  let productIdStr = productId + '';
  let productIndex = productIdStr.substr(0, 1);
  switch (productIndex) {
    case "1":
      return 1; //书籍

    case "4":
      return 2; //电影

    case "7":
      return 3; //音乐

    default:
      return 0;
  }
}

export default utils;
