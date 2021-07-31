<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login Bill Board</title>

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
	var billBoard = null;
	function init(){
		billBoard = new BillBoard($('#BillboardTable'));
		FBUtilAdapter.initPostPrecondition();
	}
	/**
	  *  The image click listener, and the images are of the billBoard.
	  **/
	function ImageClickListener(){}
	ImageClickListener.executeBiilBoardSorting = function(actionUrl, sortType){
		billBoard.sortBillboard(actionUrl, sortType);
	};
	ImageClickListener.changeSortingImage = function(mainImage){
		billBoard.changeSortingImage(mainImage);
	};

	function BillBoard(obj){
		var table = obj;
		var pbbImage = $('#pbb-image');
		var ldsImage = $('#lds-image');
		var ldImage = $('#ld-image');
		var jdImage = $('#jd-image');
		var sortType = "percentage";
		var sortAction = "getPercentageBillboard.action";
		var showAllFlag = false;
		var userType = "Normal";
		
		this.getBillBoard = function(){
			return table;
		};
		// Image related methods.
		this.getPBBImage = function(){
			return pbbImage;
		};
		this.getLDSImage = function(){
			return ldsImage;
		};
		this.getLDImage = function(){
			return ldImage;
		};
		this.getJDImage = function(){
			return jdImage;
		};
		this.getSortType = function(){
			return sortType;
		};
		this.setSortType = function(type){
			sortType = type;
		};
		this.getSortAction = function(){
			return sortAction;
		};
		this.setSortAction = function(action){
			sortAction = action;
		};
		this.getShowAllFlag = function(){
			return showAllFlag;
		};
		this.setShowAllFlag = function(flag){
			showAllFlag = flag;
		};
		this.getUserType = function(){
			return userType;
		};
		this.setUserType = function(type){
			userType = type;
		};
		
		this.init();
	}
	/**
	  * Init the billboard information.
	  */
	BillBoard.prototype.init = function(){
		var thisObj = this;
		$('#showAllLabel').click(function(){
			var flag = thisObj.getShowAllFlag();
			flag ? $(this).html('顯示所有') : $(this).html('顯示前9名');
			thisObj.setShowAllFlag(!flag);
			ImageClickListener.executeBiilBoardSorting(thisObj.getSortAction(),thisObj.getSortType());
		});
		this.sortBillboard('getPercentageBillboard.action','percentage'); // Default sorting
		// init image event
		this.getPBBImage().click(function(){
				ImageClickListener.executeBiilBoardSorting('getPercentageBillboard.action','percentage');
				ImageClickListener.changeSortingImage(thisObj.getPBBImage());
		});
		this.getLDSImage().click( function(){
				ImageClickListener.executeBiilBoardSorting('getLeftDaysBillboard.action','leftDays');
				ImageClickListener.changeSortingImage(thisObj.getLDSImage());
		});
		this.getLDImage().click(function(){
				ImageClickListener.executeBiilBoardSorting('getEndDateBillboard.action','endDate');
				ImageClickListener.changeSortingImage(thisObj.getLDImage());
		});
		this.getJDImage().click(function(){
				ImageClickListener.executeBiilBoardSorting('getJoinDateBillboard.action','joinDate');
				ImageClickListener.changeSortingImage(thisObj.getJDImage());
		});
	};
	/**
	  * Change to billd borad images. The selected one will
	  * show sort_desc.gif, and others will show refresh.gif.
	  **/
	BillBoard.prototype.changeSortingImage = function(selectImage){
		var pbbImage = this.getPBBImage();
		var ldsImage =this.getLDSImage();
		var ldImage = this.getLDImage();
		var jdImage = this.getJDImage();
		var chageToSortDescImage = function(image){
			$(image).attr('src',ResourceUtillity.getSortDescImageSrc());
		};
		var chageToRefreshImage = function(image){
			$(image).attr('src',ResourceUtillity.getRefreshImageSrc());
		};
		var doChangeImage = function(image){
			image == selectImage ? chageToSortDescImage(image) :
				chageToRefreshImage(image);
		};
		// Change images
		doChangeImage(pbbImage);
		doChangeImage(ldsImage);
		doChangeImage(ldImage);
		doChangeImage(jdImage);
	};
	/**
	  * Sort the billboard according to the sortType.
	  **/
	BillBoard.prototype.sortBillboard = function(actionUrl, sortType){
		var thisObj = this;
		this.removeAll();
		this.showLoadingImage();
		this.setSortAction(actionUrl);
		this.setSortType(sortType);
		// Get the sorting result.
		$.ajax({
			  url: actionUrl,
			  type: "POST",
			  dataType: 'xml',
			  success: function(data) {
			  		//Show sorting result.
					thisObj.removeAll();
					thisObj.createBillBoard(data,sortType);
					var userType = thisObj.getUserType();
					if( userType == "Logout" ){
						$('#lagout-user-notify-div').html('<label><font color="red">恭喜您已退伍，可到登出排行榜比老喔! </font></label>');
					} else if( userType == "New" ){
						$('#lagout-user-notify-div').html('<label><font color="red">你尚未設定兵役資訊唷。</font></label>');
					}
			  }, error: function(data) {
			    	window.alert("Link server failure.");
			  }
		});
	};
	/**
	  * Remove the billBoard all tr.
	  **/
	BillBoard.prototype.removeAll = function(){
		var table = this.getBillBoard();
		var trs = table.find('tr');
		for( var i = 1; i < trs.length ; i++ ){
			$(trs[i]).remove();
		}
	};
	BillBoard.prototype.showLoadingImage = function(){
		var table = this.getBillBoard();
		var imageUrl = ResourceUtillity.getLoadingImageSrc();
		table.append("<tr bgcolor=\"#ffffff\" style=\"text-align: left;\">"
				+ "<td colspan=\"6\"><img src=\"" + imageUrl + "\">&nbsp;Loding...</td></tr>");
	};
	/**
	  * Create the billdboard content. The xmlObj is the info source.
	  **/
	BillBoard.prototype.createBillBoard = function(xmlObj, sortType){
		var root = $(xmlObj).find('root');
		var userType = root.find('userType').text();
		this.setUserType(userType);
		var users = root.children('user');
		var table = this.getBillBoard();
		var count = 0, size;
		size = this.getShowAllFlag() ? users.length : 9;
		var thisObj = this;

		var current_user_id = null;
		FB.getLoginStatus(function(response) {
			current_user_id = response.authResponse.userID;
		});
		
		users.each( function(i){
			if( count >= size ) return;
			
			var user = $(this);
			var isLogin = user.attr('isLogin');
			var order = user.find('order').text();
			var name = user.find('name').text();
			//var img = user.find('img').text();
			var uid = user.find('uid').text();
			var joinDate = user.find('joinDate').text();
			var endDate = user.find('endDate').text();
			var leftDays = user.find('leftDays').text();
			var percentage = parseInt((user.find('percentage').text()) * 100) / 100;

			var trBgColor = (isLogin == "true") ? "lemonchiffon" : "#ffffff";
			
			var getSelectOrderCls = function(target){
				return (sortType == target) ? "SelectOrder-Cls" : "NotSelectOrder-Cls";
			};

			if( current_user_id == uid ){
				$('#lagout-user-notify-div').html('<label>在<font color="red">'+users.length+'</font>位朋友之中 ，您排名第<font color="red">' + order + '</font>位。</label>');
			}
			
			var title = "";
			if( percentage > 95 && order == "1" ) title = "我最老!";
			else if( percentage > 85 ) title = "老!";
			else if( percentage < 30 ) title = "菜!";

			var spic_withlogo = FacebookUtillity.getUserSqurePicWithLogo(uid);
			
			var spic_withlogo_trid = uid + "-tr";
			var trString = '<tr bgcolor="' + trBgColor + '" style="text-align: right;">' +
				'<td align="center"><span>' + order + '</span></td>' + 
				'<td align="left" valign="middle" id="' + spic_withlogo_trid + '">' + spic_withlogo + 
				name + '&nbsp;<font color="red"><b>' + title + '</b></font>' + thisObj.createSpeakerImageHTML(uid, name) + '</td>' + 
				'<td align="center"><span><font class="' + getSelectOrderCls('joinDate') + '">' + joinDate + '</font></span></td>' + 
				'<td align="center"><span><font class="' + getSelectOrderCls('endDate') + '">' + endDate + '</span></td>' + 
				'<td align="center"><span><font class="' + getSelectOrderCls('percentage') + '">' + percentage + '</span></td>' + 
				'<td align="center"><span><font class="' + getSelectOrderCls('leftDays') + '">' + leftDays + '</span></td></tr>';
			table.append(trString);
			FacebookUtillity.parseXFBML(spic_withlogo_trid);
			thisObj.bindSpeakerOnclickListener(uid, name + "距離退伍還有" + leftDays + "天! 趴數為" + percentage + "%");
			count++;
		} );
		FB.Canvas.setAutoGrow();
	};

	// Create Speaker imsage html.
	BillBoard.prototype.createSpeakerImageHTML = function(uid, name){
		return '<img id="' + uid + 
			'-speaker-img" style="cursor:pointer" src="images/speaker.png" ' +
			 'title="發佈訊息至' + name + '塗鴉版上"/>';
	};

	// Create a image onclick listener binding to post message.
	BillBoard.prototype.bindSpeakerOnclickListener = function(uid, message){
		var id = uid + "-speaker-img";
		$('#' + id).click(function(){
			FBUtilAdapter.postMessageToFriendsWall(
					'',
					ResourceKeeper.getLogoURL(),
					ResourceKeeper.getAppURL(),
					'還要繼續熬呢!',
					' ',message,uid,null);
		});
	};
	
</script>
</head>
<body onload="init();">

<div align="center"><jsp:include page="header.jsp"></jsp:include></div>
<br>
<br>
<br>
<div id="lagout-user-notify-div"></div>
<br>
<div>
<table id="BillboardTable" cellspacing="0" align="Center" border="1"
	class="table-stats"
	style="background-color: #CECFCE; border-width: 1px; border-style: solid; width: 97%; border-collapse: collapse;">
	<tr bgcolor="#cccccc" style="text-align: center; font-weight: bold;">
		<td width="30">排名</td>
		<td width="40%" align="left">使用者&nbsp;<label>
			<a href="#" style="text-decoration:none;" id="showAllLabel">顯示所有</a>
		</label></td>
		<td width="13%">入伍日期 <img id="jd-image" src="images/common/refresh.gif"
			class="Image-Moveon" title="照入伍日期排行"/></td>
		<td width="13%">退伍日期<img id="ld-image" src="images/common/refresh.gif"
			class="Image-Moveon" title="照退伍日期排行"/></td>
		<td width="13%">%數<img id="pbb-image" src="images/common/sort_desc.gif"
			class="Image-Moveon" title="照%數排行"/></td>
		<td width="13%">剩餘天數<img id="lds-image" src="images/common/refresh.gif"
			class="Image-Moveon" title="照剩餘天數排行"/></td>
	</tr>
</table>
</div>
<br>
<div align="center"><jsp:include page="footer.jsp"></jsp:include></div>

</body>
</html>