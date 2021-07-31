
function CowndownNewsManager(){
	var newsList;

	this.setNewsDiv = function(newslist){
		newsList = newslist;
	};
	this.getNewsDiv= function(){
		return  newsList;
	};

	this.init();
}

CowndownNewsManager.prototype.init = function(){
	this.setNewsDiv(new NewsList($('#news-div')));
	var obj = this;
	$(document).everyTime( 60000, function(){
		obj.updateNews();
	} );
};

CowndownNewsManager.prototype.updateNews = function(){
	this.getNewsDiv().removeAll();
	this.getNewsDiv().update();
};

function NewsList(obj){
	var divObj = obj;
	var news_ol;
	
	this.setNewsOL = function(newsOl){
		news_ol = newsOl;
	};
	this.getNewOL = function(){
		return news_ol;
	};
	
	this.init();
}

NewsList.prototype.init = function(){
	this.setNewsOL($('#news-ol'));
	this.update();
};

NewsList.prototype.update = function(){
	var obj = this;
	this.showLoadingImage();
	$.ajax({
		  url: 'getNews.action',
		  type: "POST",
		  dataType: 'xml',
		  success: function(data) {
			obj.removeAll();
			obj.createNewsList(data);
			FB.Canvas.setAutoGrow();
		  }, error: function(data) {
			obj.showMessage('Get news information failed.');
		  }
	});
};

NewsList.prototype.createNewsList = function(xmlObj){
	var root = $(xmlObj).find('root');
	var ol = this.getNewOL();
	var news = root.children('new');

	if( news.length == 0 ){
		ol.append("<li><label>沒有新鮮事喔!</lable></li>");
	}
	var thisObj = this;
	
	news.each( function(i){
		var the_new = $(this);
		var user =  the_new.find('user').text();
		var uid = the_new.find('uid').text();
		var isNewUser =   the_new.find('isNewUser').text();
		var leftDays =   the_new.find('leftdays').text();
		var joinDays =   the_new.find('joindays').text();
		
		var pic_image = FacebookUtillity.getUserSqurePicWithLogo(uid);
		var speaker_image = thisObj.createSpeakerImageHTML(uid, user);
		var message = user + "&nbsp;";

		if( leftDays < 10 && leftDays > 0 ){
			message += "剩下 " + leftDays + "天退伍喔!";
		} else if( leftDays < 0 &&  leftDays > -10 ){
			leftDays *= -1;
			message += "已經退伍" + leftDays + "天了!";
		} else if( leftDays == 0  ){
			message += "今天就退伍了耶, 快給他祝賀!";
		} else if( leftDays < 110 && leftDays > 100 ){
			leftDays -= 100;
			message += "還有" + leftDays + "天就要破百了!";
		} else if( leftDays < 100 && leftDays > 90 ){
			leftDays -= 100;
			leftDays *= -1;
			message += "破百已經" + leftDays + "天了! 快恭喜他吧!";
		} else if( joinDays < 0 && joinDays > -10  ){
			joinDays *= -1;
			message += "再" + joinDays + "天就要登入了...";
		} else if( joinDays < 37 &&  joinDays >= 0 ){
			message += "入伍" + joinDays + "天, 是全台灣最菜的兵喔!";
		} else {
			return;
		}
		var liid = uid + "-li";
		ol.append("<li id=\"" + liid + "\" name=\"news-li\">" + pic_image +
				speaker_image + "<label>" + message + "</lable></li>");
		FacebookUtillity.parseXFBML(liid);
		thisObj.bindSpeakerOnclickListener(uid, message);
	});
};

// Create Speaker imsage html.
NewsList.prototype.createSpeakerImageHTML = function(uid, name){
	return '<img id="' + uid + 
		'-speaker-img" style="cursor:pointer" src="images/speaker.png" title="發佈訊息至' +
		name + '塗鴉版上"/>';
};

// Create a image onclick listener binding to post message.
NewsList.prototype.bindSpeakerOnclickListener = function(uid, message){
	var id = uid + "-speaker-img";
	$('#' + id).click(function(){
		FBUtilAdapter.postMessageToFriendsWall(
				'',
				ResourceKeeper.getLogoURL(),
				ResourceKeeper.getAppURL(),
				'新鮮事',
				' ',message,uid,null);
	});
};

NewsList.prototype.removeAll = function(){
	var ol = this.getNewOL();
	ol.html("");
};

NewsList.prototype.showLoadingImage = function(){
	var imageUrl = ResourceUtillity.getLoadingImageSrc();
	this.getNewOL().append("<li class='join-date'><img src='" + imageUrl + "'/>&nbsp;Loading...</li>");
};

NewsList.prototype.showMessage = function(msg){
	this.getNewOL().html("");
	this.getNewOL().append("<li class='join-date'>"+msg+"</li>");
};

