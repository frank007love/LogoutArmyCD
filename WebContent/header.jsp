<%@page import="org.tonylin.util.net.URLConnectionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String currentURL = request.getRequestURL().toString();
	String []splitArrays = currentURL.split("/");
	String currentPage = splitArrays[splitArrays.length-1];
	
	SystemConfig sys_config = SystemConfig.getInstance();
	String apName = sys_config.getAP_Name();
	String apId = sys_config.getAp_id();
	String []tabsName = {"我的計時器", "登入排行榜" ,"登出排行榜","邀請朋友","討論區"};
	String forumUrl = sys_config.getForumUrl();
	String []tabsLink = { "counter", "loginBillboard", "logoutBillboard", "#", "forum"};
	String []tabsId = { "counter", "loginBillboard", "logoutBillboard", "inviteFriend", "forum"};
%>
<div id='ad'>
<% if(request.getScheme().equals("http")){ %>
<div id='ccmd100358_100767'><script type="text/javascript">var btt_100358_100767_ = Math.random(); document.write ( "<scr" + "ipt lang" + "uage="+"'javas"+"cript' "+"src="+"'http://"+"tw.ad.bt"+"nibbler"+".com"+"/a/b"+"tput3/?"+"m=100358"+"&z=100767"+"&w=728"+"&h=90"+"&ch=100360"+"&trac"+"k=y&t="+ btt_100358_100767_ + "&pub=100666' ></s"+"cript>" );</script></div>
<% } else { 
	String randomValue = String.valueOf(Math.random());
	String adUrl = "http://tw.ad.btnibbler.com/a/btput3/?m=100358&z=100767&w=728&h=90&ch=100360&track=y&t=" + randomValue + "&pub=100666";
	String adScript = URLConnectionUtil.getWebContent(adUrl);
%>
<div id='ccmd100358_100767'><script type="text/javascript">var btt_100358_100767_ = <%=randomValue%>; <%=adScript%></script></div>
<% } %>
</div>
<%@page import="com.franklin.logoutarmycd.SystemConfig"%><style type="text/css">
@import url(css/tab.css);
</style>
<script src="//connect.facebook.net/en_US/all.js""></script>
<div id="fb-root"></div>
<div align="left">
<table border="0">
<tr>
<td valign="top"><div class="fb-like" data-href="http://www.facebook.com/armyCountdownForum" data-send="false" data-width="450" data-show-faces="true"></div></td>
<td></td>
</tr>
</table>
</div>

<div class="ui-widget" align="left">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .3em;"> 
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
		<span id="server-info">請將討論區加到我的最愛，以便接受更新資訊與諮詢。</span>
		</p>
	</div>
</div>

<link type="text/css" href="css/custom-theme1/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery-timer.js" type="text/javascript"></script>
<script type="text/javascript">

var server_info = new Array('由於使用者越來越多，主機越來越不穩。希望用戶們能多多支持以更換新主機。',
		'支援安全性瀏覽(https)。',
		'將用戶資料轉移至資料庫。',
		'請將討論區加到我的最愛，以便接受更新資訊與諮詢。',
		'Facebook認證問題已修復完成，有問題可至討論區發佈讓我得知。');

var index = 0;
$(document).everyTime( 5000, function(){
	$('#server-info').html(server_info[index++]);
	if( index >= server_info.length  ){
		index = 0;
	}
} );

</script>

<div id="tabsF">
<ul>
<%
	for( int i = 0 ; i < tabsName.length ; i++ ){
		String id = "none";
		if( currentPage.startsWith(tabsLink[i]) || (currentPage.equals(apName) && i == 0)  ){
			id = "current";
		}
		out.println("<li id='" + id + "'><a id='"+ tabsId[i] +"' href='" + tabsLink[i] + ".action'><span>" +
			tabsName[i] + "</span></a></li>");
	}
%>
</ul>
</div>
<script type="text/javascript">
document.getElementById('inviteFriend').onclick = function() {
	  FB.ui({
	    method: 'apprequests',
	    message: '讓我們一起來倒數吧!'
	  }, function(){});
	};
</script>
    