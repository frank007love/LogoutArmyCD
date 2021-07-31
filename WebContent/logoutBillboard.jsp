<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Logout Bill Board</title>

<link href="css/highlighter-form/styles.css" type="text/css"
	rel="stylesheet" />
<link href="css/highlighter-form/forms.css" type="text/css"
	rel="stylesheet" />
<link href="css/highlighter-form/AccessKeyHighlighter.css"
	type="text/css" rel="stylesheet" />

<!-- JQuery libraries  -->
<link type="text/css" href="css/custom-theme1/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.9.custom.min.js"></script>

<!-- My libraries -->
<script type="text/javascript" src="js/resource.js"></script>
<script type="text/javascript" src="js/commonUtil.js"></script>
<script type="text/javascript" src="js/FBUtillity.js"></script>
<script type="text/javascript" src="js/fb-util-adapter.js"></script>
<style type="text/css">
.table-stats {
	font-size: 10pt;
	border: 1pt solid #999999;
	padding: 2pt;
	margin: 2pt;
}

.NotSelectOrder-Cls {
	color: black;
}

.SelectOrder-Cls {
	color: red;
}

.Image-Moveon {
	cursor: pointer;
}
</style>

<script type="text/javascript">

$(document).ready(function(){
	new LogoutBillBoard();
	FB.Canvas.setAutoGrow();
	//FB.XFBML.parse();
	FBUtilAdapter.initPostPrecondition();
});

function LogoutBillBoard(){
	var mUserInfoPanel = null;
	var mTopBillboard = null;
	var mLastBillboard = null;
	var mLoseTable = null;
	this.setUserInfoPanel = function(panel){
		mUserInfoPanel = panel; 
	};
	this.getTopBillboard = function(){
		return mTopBillboard;
	};
	this.setTopBillboard = function(billBoard){
		mTopBillboard = billBoard;
	};
	this.setLastBillboard = function(billBoard){
		mLastBillboard = billBoard;
	};	
	this.setLoseTable = function(loseTable){
		mLoseTable = loseTable;
	};		
	this.init();
};

LogoutBillBoard.prototype.init = function(){
	var thisObj = this;
	var topBillBoard = new BillboardTable('TopBillboardTable');
	var lastBillboard = new BillboardTable('LastBillboardTable');
	var loseTable = new LoseTable('LoseTable');
	
	this.setTopBillboard(topBillBoard);
	this.setLastBillboard(lastBillboard);
	this.setLoseTable(loseTable);

	topBillBoard.showLoading();
	lastBillboard.showLoading();
	loseTable.showLoading();
	
	$.ajax({
		  url: 'getLogoutInformation.action',
		  type: 'POST',
		  dataType: 'xml',
		  success: function(data) {
		  		//console.log('init table');
		  		var root = $(data).find('logoutInformation');

		  		// init user info panel
		  		var totalLogout = root.find('totalLogout').text();
		  		var logoutOrder = root.find('logoutOrder').text();
		  		var loginuser = root.find('loginuser');
		  		thisObj.updateUserInfoPanel(totalLogout, logoutOrder, loginuser);

		  		// init top billboard 
		  		var toplist = root.find('toplist');
		  		var users = toplist.children('user');
		  		users.each( function(i){
		  			var user = $(this);
					var order = user.find('order').text();
					var name = user.find('name').text();
					var uid = user.find('uid').text();
					var joinDate = user.find('joinDate').text();
					var endDate = user.find('endDate').text();
					topBillBoard.addRowData(order, uid, name, joinDate, endDate);
		  		});
		  		topBillBoard.show();

		  		// init last billboard
		  		var lastlist = root.find('lastlist');
		  		users = lastlist.children('user');
		  		users.each( function(i){
		  			var user = $(this);
					var order = user.find('order').text();
					var name = user.find('name').text();
					var uid = user.find('uid').text();
					var joinDate = user.find('joinDate').text();
					var endDate = user.find('endDate').text();
					lastBillboard.addRowData(order, uid, name, joinDate, endDate);
		  		});
		  		lastBillboard.show();

				// init lose table
				var loselist = root.find('loselist');
		  		users = loselist.children('user');
		  		users.each( function(i){
		  			var user = $(this);
					var order = user.find('order').text();
					var name = user.find('name').text();
					var uid = user.find('uid').text();
					var joinDate = user.find('joinDate').text();
					var endDate = user.find('endDate').text();
					loseTable.addRowData(order, uid, name, joinDate, endDate);
		  		});				
		  		loseTable.show();
		  }, error: function(data) {
		    	window.alert("Link server failure.");
		  }
	});
};

LogoutBillBoard.prototype.updateUserInfoPanel = function(totalLogout, logoutOrder, loginuser){
	var userInfoPanel = new UserInfoPanel('user-info-panel', totalLogout, logoutOrder, loginuser);
	this.setUserInfoPanel(userInfoPanel);
	userInfoPanel.show();
};

function UserInfoPanel(panelId, total, userOrder, loginuser){
	var mPanel = $('#'+panelId);
	var mTotal = total;
	var mOrder = userOrder;
	var mLoginUser = loginuser;

	this.getPanel = function(){
		return mPanel;
	};
	this.getOrder = function(){
		return mOrder;
	};
	this.getTotal = function(){
		return mTotal;
	};
	this.getLoginUser = function(){
		return mLoginUser;
	};
	this.getId = function(){
		return $(mLoginUser).find('uid').text();
	};
	this.getName = function(){
		return $(mLoginUser).find('name').text();
	};
	this.getLeftDays = function(){
		return $(mLoginUser).find('leftDays').text();
	};
	this.getJoinDate = function(){
		return $(mLoginUser).find('joinDate').text();
	};	
};

UserInfoPanel.prototype.show = function(){
	var order = this.getOrder();
	var panel = this.getPanel();
	if( order == '0' ){
		panel.html('');
	} else {
		var total = this.getTotal();
		var msg = '在<font color="red">' + total + '</font>位退伍已朋友中' + 
		'，您排名第<font color="red">' + order + '</font>位。';
		var uid = this.getId();
		var speaker_id = uid + '-speaker-img-uip';
		var speaker = '<img id="' + speaker_id + '" style="cursor:pointer" src="images/speaker.png" ' +
		 'title="發佈訊息至' + this.getName() + '塗鴉版上"/>';
		panel.html(msg+speaker);

		var publish_msg = "我"+this.getJoinDate()+"入伍，我已經退伍"+(this.getLeftDays()*-1)+"天了!";
		$('#' + speaker_id).click(function(){
			FBUtilAdapter.postMessageToFriendsWall(
					'',
					ResourceKeeper.getLogoURL(),
					ResourceKeeper.getAppURL(),
					'往事只能回味',
					' ',publish_msg,uid,null);
		});
	}
};



function BillboardTable(tableID){
	var mTable = $('#'+tableID);
	var mRowDataList = new Array();

	this.getTable = function(){
		return mTable;
	};
	this.getRowDataList = function(){
		return mRowDataList;
	};

};

BillboardTable.prototype.addRowData = function(order, id, name, joinDate, leftDate){
	var rowDataList = this.getRowDataList();
	rowDataList[rowDataList.length] = new BillboardRowData(order, id, name, joinDate, leftDate);
	//console.log('Billboard data order=%s,id=%s,name=%s,join=%s,left=%s',
	//		order, id, name, joinDate, leftDate);
};

BillboardTable.prototype.showLoading = function(){
	this.removeAll();
	
	var table = this.getTable();
	var imageUrl = ResourceUtillity.getLoadingImageSrc();
	table.append("<tr bgcolor=\"#ffffff\" style=\"text-align: left;\">"
			+ "<td colspan=\"6\"><p/>&nbsp;<img src=\"" + imageUrl + "\">&nbsp;Loding...<p/></td></tr>");	
};

BillboardTable.prototype.removeAll = function(){
	var table = this.getTable();
	var trs = table.find('tr');
	for( var i = 1; i < trs.length ; i++ ){
		$(trs[i]).remove();
	}
};

BillboardTable.prototype.show = function(){
	this.removeAll();
	
	var rowDataList = this.getRowDataList();
	var table = this.getTable();
	for( var i = 0 ; i < rowDataList.length ; i++ ){
		var rowData = rowDataList[i];
		var spic_withlogo = FacebookUtillity.getUserSqurePicWithLogo(rowData.getId());
		var spic_withlogo_trid = rowData.getId() + "-tr";
		var trString = '<tr bgcolor="#ffffff" style="text-align: right;">' +
			'<td align="center"><span>' + rowData.getOrder() + '</span></td>' + 
			'<td align="left" valign="middle" id="' + spic_withlogo_trid + '">' + spic_withlogo + 
			rowData.getName() + '&nbsp;' + this.createSpeakerImageHTML(rowData.getId(), rowData.getName()) + '</td>' + 
			'<td align="center"><span>' + rowData.getJoinDate() + '</span></td>' + 
			'<td align="center"><span>' + rowData.getLeftDate() + '</span></td>';
		table.append(trString);
		//console.log('Append %s to table.', trString);
		
		FacebookUtillity.parseXFBML(spic_withlogo_trid);

		this.bindSpeakerOnclickListener(rowData.getId(), rowData.getName() + " " + rowData.getJoinDate() + "入伍，在所有人之中排第" + rowData.getOrder() + "名!");
	}
	if( rowDataList.length == 0 ){
		var trString = '<tr bgcolor="#ffffff"><td colspan="4"><p/><h3>&nbsp;&nbsp;無資料!</h3><p/></td></tr>';
		table.append(trString);
	}
};

BillboardTable.prototype.bindSpeakerOnclickListener = function(uid, message){
	var id = uid + "-speaker-img";
	$('#' + id).click(function(){
		FBUtilAdapter.postMessageToFriendsWall(
				'',
				ResourceKeeper.getLogoURL(),
				ResourceKeeper.getAppURL(),
				'梯數比一比',
				' ',message,uid,null);
	});
};

BillboardTable.prototype.createSpeakerImageHTML = function(uid, name){
	return '<img id="' + uid + 
		'-speaker-img" style="cursor:pointer" src="images/speaker.png" ' +
		 'title="發佈訊息至' + name + '塗鴉版上"/>';
};

function BillboardRowData(order, id, name, joinDate, leftDate){
	var mOrder = order;
	var mId = id;
	var mName = name;
	var mJoinDate = joinDate;
	var mLeftDate = leftDate;

	this.getOrder = function(){
		return mOrder;
	};
	this.getId = function(){
		return mId;
	};
	this.getName = function(){
		return mName;
	};
	this.getJoinDate = function(){
		return mJoinDate;
	};
	this.getLeftDate = function(){
		return mLeftDate;
	};
};


function LoseTable(id){
	var mTable = $('#'+id);
	var mId = id;
	var mRowDataList = new Array();

	this.getTable = function(){
		return mTable;
	};
	this.getRowDataList = function(){
		return mRowDataList;
	};
	this.getId = function(){
		return mId;
	};
};

LoseTable.prototype.addRowData = function(order, id, name, joinDate, leftDate){
	var rowDataList = this.getRowDataList();
	rowDataList[rowDataList.length] = new BillboardRowData(order, id, name, joinDate, leftDate);
	//console.log('LoseTable data order=%s,id=%s,name=%s,join=%s,left=%s',
	//		order, id, name, joinDate, leftDate);
};

LoseTable.prototype.show = function(){
	this.removeAll();
	
	var rowDataList = this.getRowDataList();
	var table = this.getTable();
	table.append('<p/>');
	for( var i = 0 ; i < rowDataList.length ; i++ ){
		var rowData = rowDataList[i];
		var spic_withlogo = FacebookUtillity.getUserSqurePicWithLogo(rowData.getId(), 'false');
		var spanId = "span-" + rowData.getId();
		var span = '<span id="' + spanId + '" style="cursor:pointer">' + spic_withlogo + '</span>';
		table.append(span);
		FBUtilAdapter.parseXFBMLandBindPublish(spanId, '一梯退三步，你和我差幾梯?', rowData.getId());
	}
	if( rowDataList.length == 0 ){
		table.append('<h3>&nbsp;&nbsp;還沒有朋友比您還嫩!</h3><p/>');
	} 
};

LoseTable.prototype.removeAll = function(){
	var table = this.getTable();
	var loadingLabel = table.find('label');
	if( loadingLabel != null ){
		loadingLabel.remove();
	};
	
};

LoseTable.prototype.showLoading = function(){
	this.removeAll();
	
	var table = this.getTable();
	var imageUrl = ResourceUtillity.getLoadingImageSrc();
	table.append("<label><p/>&nbsp;<img src=\"" + imageUrl + "\">&nbsp;Loding...<p/></label>");	
};

</script>
</head>
<body>

<div align="center"><jsp:include page="header.jsp"></jsp:include></div>
<br>
<br>
<br>
<br>
<div id="user-info-panel"></div>
<br>
<div>

<fieldset><legend style="font-size: 1.5em">骨灰級</legend>
<table id="TopBillboardTable" cellspacing="0" align="Center" border="1"
	class="table-stats"
	style="background-color: #CECFCE; border-width: 1px; border-style: solid; width: 99%; border-collapse: collapse;">
	<tr bgcolor="#cccccc" style="text-align: center; font-weight: bold;">
		<td width="35">排名</td>
		<td align="left">使用者&nbsp;</td>
		<td width="13%">入伍日期</td>
		<td width="13%">退伍日期</td>
	</tr>
</table>
</fieldset>
<br>
<fieldset><legend style="font-size: 1.5em">嫩芽</legend>
<table id="LastBillboardTable" cellspacing="0" align="Center" border="1"
	class="table-stats"
	style="background-color: #CECFCE; border-width: 1px; border-style: solid; width: 99%; border-collapse: collapse;">
	<tr bgcolor="#cccccc" style="text-align: center; font-weight: bold;">
		<td width="35">排名</td>
		<td align="left">使用者&nbsp;</td>
		<td width="13%">入伍日期 </td>
		<td width="13%">退伍日期</td>
	</tr>
</table>
</fieldset>
<br>

<fieldset id="LoseTable"><legend style="font-size: 1.5em">一梯退三步戰績</legend>

</fieldset>
</div>
<br>
<div align="center"><jsp:include page="footer.jsp"></jsp:include></div>

</body>
</html>