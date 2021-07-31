function FBUtilAdapter(){}	

FBUtilAdapter.flag_publish_fb = false;

FBUtilAdapter.postMessageToFriendsWall = function(post_message,
		post_picture, post_link, post_name, post_caption, post_desc, touid, callback){
	
	if( !this.checkPostPrecondition() ){
		this.checkFailureAction();
		return;
	}
	
	FacebookUtillity.postMessageToFriendsWall(post_message,
			post_picture, post_link, post_name, post_caption, post_desc, touid, callback);
};

FBUtilAdapter.postMessageToWall = function(post_message,
		post_picture, post_link, post_name, post_caption, post_desc, callback){
	
	if( !this.checkPostPrecondition() ){
		this.checkFailureAction();
		return;
	}
	
	FacebookUtillity.postMessageToWall(post_message,
			post_picture, post_link, post_name, post_caption, post_desc, callback);
};

FBUtilAdapter.checkPostPrecondition = function(){
	if( this.flag_publish_fb ){
		return this.flag_publish_fb;
	}
	
	var result = false;
	$.ajax({
		  url: 'checkPublishFBPermission.action',
		  type: "POST",
		  dataType: 'html',
		  async: false,
		  success: function(data) {
			result = data == 'true';
		  }, error: function(data) {
			window.alert("Link server failure.");
		  }
	});
	this.flag_publish_fb = result;
	return result;
};

FBUtilAdapter.checkFailureAction = function(){
	if( $('#iframe_ccmd_fb100358_100767').attr('src') == undefined ){
		window.alert("無法正常顯示廣告會影響到功能，請檢查是否有開啟廣告封鎖。!");   
    } else {
    	window.alert("請多多支持我們的贊助商喔!");	
    }
	FB.Canvas.scrollTo(0,0);
	var options = {};
	$( '#ad' ).show( 'pulsate', options, 500 );
};

FBUtilAdapter.initPostPrecondition = function(){
	document.getElementById('ad').onmouseover = function() {
		$.ajax({
			  url: 'enablePublishFBPermission.action',
			  type: "POST",
			  dataType: 'html',
			  success: function(data) {
				// ignore
			  }, error: function(data) {
				window.alert("Link server failure.");
			  }
		});
	};	
};

FBUtilAdapter.parseXFBMLandBindPublish = function(domID, msg, uid){
	function publishFB(){
		if( !FBUtilAdapter.checkPostPrecondition() ){
			FBUtilAdapter.checkFailureAction();
			return;
		}
		
		FacebookUtillity.postMessageToFriendsWall(
				'',
				ResourceKeeper.getLogoURL(),
				ResourceKeeper.getAppURL(),
				'梯數的戰敗者',
				' ',msg,uid,null);
	}
	
	FacebookUtillity.parseXFBMLandBindPublish(domID, msg, uid, publishFB);
};
