<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的計時器</title>
<!-- JQuery libraries  -->
<link type="text/css" href="css/custom-theme1/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.9.custom.min.js"></script>
<link href="css/highlighter-form/styles.css" type="text/css"
	rel="stylesheet" />
<link href="css/highlighter-form/forms.css" type="text/css"
	rel="stylesheet" />
<link href="css/highlighter-form/AccessKeyHighlighter.css"
	type="text/css" rel="stylesheet" />
	<script src="js/jquery-timer.js" type="text/javascript"></script>
	
<!-- My libraries -->
<script src="js/resource.js" type="text/javascript"></script>
<script src="js/date.js" type="text/javascript"></script>
<script src="js/cdUserModel.js" type="text/javascript"></script>
<script src="js/commonUtil.js" type="text/javascript"></script>
<script src="js/message-panel.js" type="text/javascript"></script>
<script src="js/cdCounter.js" type="text/javascript"></script>
<script src="js/cdNews.js" type="text/javascript"></script>
<script src="js/friendInfo.js" type="text/javascript"></script>
<script type="text/javascript" src="js/FBUtillity.js"></script>
<script type="text/javascript" src="js/fb-util-adapter.js"></script>

<script type="text/javascript">
	var CDManager = null;
	var CDNewsManager = null;
	var friendInfoManager = null;
	//var adFlag = location.protocol.substr(0, 5) == "https" ? true : false;
	var adFlag = false;
	function init(){
		CDManager = new CountDownManager();
		CDNewsManager = new CowndownNewsManager();
		friendInfoManager = new FriendInfoManager();
		FBUtilAdapter.initPostPrecondition();
	}
	
	
</script>
</head>
<body onload="init();">
<div align="center"><jsp:include page="header.jsp"></jsp:include>
</div>
<br>
<br>
<br>
<div class="ui-widget" id="highlight-div">
<div class="ui-state-highlight ui-corner-all"
	style="margin-top: 20px; padding: 0 .7em; display: none" id="newuser-mp">
<p><span class="ui-icon ui-icon-info"
	style="float: left; margin-right: .3em;"></span><strong>尚未設定兵役資訊。</strong></p>	
</div>
<div class="ui-state-highlight ui-corner-all"
	style="margin-top: 20px; padding: 0 .7em; display: none" id="olduser-mp">	
<p><span class="ui-icon ui-icon-info"
	style="float: left; margin-right: .3em;"></span> 
	<strong><label id="cd_title"></label></strong>&nbsp;
	<span id="cd-message">距離退伍時間剩下<label id="cd_days_label"></label>日!!
	共<label id="cd_second_label"></label>秒&nbsp;<label id="cd_percent_label"></label>%</span>
	<span id="logout-message" style="display: none">你已經退伍<label id="logout_days_label"></label>日了!!</span>
	</p>
</div>
</div>
<script type="text/javascript">
  document.write("<div class=\"js\">");

</script>
<div id="pageContainer" class="default">
<form id="army-cd-form" method="get" action="./"
	enctype="application/x-www-form-urlencoded">
<div>
<div class="form customer-form">
<fieldset class="personal-details"><legend
	style="font-size: 1.5em">計時器設定</legend>
<ol>
	<li class="join-date"><label for="joinDate" class="required-field"
		title="This is a required field">入伍時間:</label> <input name="joinDate"
		id="joinDate" type="text" maxlength="32" accesskey="F"
		title="入伍時間" class="text-box" />
		<span id="joinDate_err" style="color: red; position: relative;"></span>
	</li>
	<li class="join-period"><label for="lastName">役期:</label> <select
		id="join-period-year">
		<option value="0">0</option>
		<option value="1" selected="selected">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="8">8</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20">20</option>
		<option value="25">25</option>
		<option value="30">30</option>
	</select>年 <select id="join-period-month">
		<option value="0" selected="selected">0</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
	</select>月 <select id="join-period-date">
		<option value="0" selected="selected">0</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
		<option value="12">12</option>
		<option value="13">13</option>
		<option value="14">14</option>
		<option value="15">15</option>
		<option value="16">16</option>
		<option value="17">17</option>
		<option value="18">18</option>
		<option value="19">19</option>
		<option value="20">20</option>
		<option value="21">21</option>
		<option value="22">22</option>
		<option value="23">23</option>
		<option value="24">24</option>
		<option value="25">25</option>
		<option value="26">26</option>
		<option value="27">27</option>
		<option value="28">28</option>
		<option value="29">29</option>
		<option value="30">30</option>
	</select>日</li>
	<li><label>軍訓折抵:</label> <select
		id="reduce_day">
	</select>日
	<span id="reduce_day_err" style="color: red; position: relative;"></span>
	</li>
	<li><label>成功嶺折抵:</label> <select
		id="otherReduce_day">
	</select>日
	<span id="other_reduce_day_err" style="color: red; position: relative;"></span></li>
</ol>
</fieldset>

<dl class="legend">
	<dt class="required-field" title="This is a required field">此為必填欄位</dt>
	<dd>為必填欄位</dd>
</dl>
<fieldset class="actions">
<ul>
	<li>
		<input type="button" name="SaveBtn" id="SaveBtn" style="font-size: 70.5%"
			value="設置" accesskey="S" title="將資料儲存起來" class="button"/>
		<input type="button" name="PostBtn" id="PostBtn" style="font-size: 70.5%"
			value="發布" accesskey="S" title="發布至Facebook Wall" class="button"/>
		<label id="save-loading-label"></label>
	</li>
</ul>
</fieldset>
</div>
</div>
</form>
 
<div class="form friendinfo-form" align="left" id="friendinfo-div">
	<fieldset><legend
		style="font-size: 1.5em">關於朋友</legend>
		<ol id="friendinfo-ol">
		</ol>
	</fieldset>
</div>
 
<div class="form news-form" align="left" id="news-div">
	<fieldset><legend
		style="font-size: 1.5em">新鮮事</legend>
		<ol id="news-ol">
		</ol>
	</fieldset>
</div>

<script type="text/javascript">
    <!--
        document.write("</div>");
    -->
    </script>

<div align="center"><jsp:include page="footer.jsp"></jsp:include>

<div id="Class-Reduce-Day-Div" title="軍訓折抵" style="display: none;">
	<table width="100%">
	<tr>
		<td><label>天數:</label>&nbsp;<input type="text" size="6" id="CRD-Text"></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="right"><div style="font-size: 80%">
	
	</div></td>
	</table>
</div>
<div id="Class-OReduce-Day-Div" title="成功嶺折抵" style="display: none;">
	<table width="100%">
	<tr>
		<td><label>天數:</label>&nbsp;<input type="text" size="6" id="CORD-Text"></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="right"><div style="font-size: 80%">
	</div></td>
	</table>
</div>

</div>
</div>
</body>
</html>