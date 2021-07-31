<%@page import="com.franklin.logoutarmycd.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<title>Session Out Page</title>
<% 
	String indexUrl = SystemConfig.getInstance().getFacebookApUrl();
%>
<script type="text/javascript">
	function init(){
		var browser = jQuery.browser;
		var ieFlag = false;
		jQuery.each(browser, function(i,val){
			if( $.browser.msie ){
				ieFlag = true;
			}
		});
		if( ieFlag ){
			$('#ie-warning-div').append('由於您為IE使用者, 且IE造成Struts2 Session問題。' +
					'<br>請您至工具>網際網路選項>隱私權>打開第一方與第三方Cookie設置。'+
					'<br>完成後請點擊<button onclick="redirect();">&nbsp;首頁</button>');
		} else {
			redirect();
		}	
	}
	function redirect(){
		window.top.location = "<%=indexUrl%>";
	}
</script>
</head>
<body onload="init();">
<h3><font color="red">Session out of time, redirect to the home page.</font></h3>
<h3><font><div id="ie-warning-div"></div></font></h3>
</body>
</html>