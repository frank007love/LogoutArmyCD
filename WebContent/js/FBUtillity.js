/**
 * Depend on http://connect.facebook.net/zh_TW/all.js
 */
function FacebookUtillity(){}
FacebookUtillity.postMessageToWall = function(post_message,
		post_picture, post_link, post_name, post_caption, post_desc, callback){
  	var publish = {
  			display: 'iframe',
			method: 'feed',
	        picture: post_picture,
	        link: post_link,
	        name: post_name,
	        caption: post_caption,
	        description: post_desc,
	        message: post_message
      	};
   FB.ui(publish, callback);
};

FacebookUtillity.postMessageToFriendsWall = function(post_message,
		post_picture, post_link, post_name, post_caption, post_desc, touid, callback){
  	var publish = {
  			display: 'iframe',
			method: 'feed',
	        picture: post_picture,
	        link: post_link,
	        name: post_name,
	        caption: post_caption,
	        description: post_desc,
	        message: post_message,
	        to: touid
      	};
   FB.ui(publish, callback);
};

FacebookUtillity.getUserSqurePicWithLogo = function(uid, aLink){
	var linked = aLink != null ? aLink : 'true';
	
  	return "<fb:profile-pic size=\"square\" uid=\"" + uid + 
  		"\" facebook-logo=\"true\" linked=\"" + linked + "\"></fb:profile-pic>";
}; 



function sleep(milliSeconds) {
	var startTime = new Date().getTime();
	while (new Date().getTime() < startTime + milliSeconds)
		;
}

FacebookUtillity.parseXFBML = function(domID){
	FB.XFBML.parse($("#" + domID)[0], function(){
		setTimeout(function(){
			$("#" + domID).find('a[class="fb_link"]').attr('target','_blank');
		}, 3000);
	});
};

FacebookUtillity.parseXFBMLandBindPublish = function(domID, msg, uid, click){
	FB.XFBML.parse($("#" + domID)[0], function(){
		setTimeout(function(){
			
			$("#" + domID).find('img').click(click == null ? function(){
				FacebookUtillity.postMessageToFriendsWall(
						'',
						ResourceKeeper.getLogoURL(),
						ResourceKeeper.getAppURL(),
						'梯數的戰敗者',
						' ',msg,uid,null);
			} : click);
		}, 3000);
	});
};


