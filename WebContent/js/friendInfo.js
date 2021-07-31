
	function FriendInfoManager(){
		var friendinfo_ol;

		this.getFriendInfoOl = function(){
			return friendinfo_ol;
		};

		this.setFriendInfoOl = function(obj){
			friendinfo_ol = obj;
		};
		this.init();
	}

	FriendInfoManager.prototype.init = function(){
		this.setFriendInfoOl($('#friendinfo-ol'));
		this.showLoadingImage();
		var obj = this;
		$.ajax({
			  url: 'getFriendInfo.action',
			  type: "POST",
			  dataType: 'xml',
			  success: function(xmlObj) {
				 var root = $(xmlObj).find('friendinfo');
				 obj.showMessage(root);
				 var total = root.children('total').text();
				 var news = root.children('new').text();
				 var logout = root.children('logout').text();
				 var other = root.children('army').text();
				 obj.showMessage("已安裝 &nbsp;"+total+"&nbsp;員，登入&nbsp;"+other+"&nbsp;員，登出&nbsp;"+logout+"&nbsp;員，未設定&nbsp;"+news+"&nbsp;員");
			  }, error: function(xmlObj) {
				 obj.showMessage("Get friends' information failed.");
			  }
		});
	};
	
	FriendInfoManager.prototype.showMessage = function(msg){
		this.getFriendInfoOl().html("");
		this.getFriendInfoOl().append("<li>"+msg+"</li>");
	};

	FriendInfoManager.prototype.showLoadingImage = function(){
		var imageUrl = ResourceUtillity.getLoadingImageSrc();
		this.getFriendInfoOl().append("<li class='join-date'><img src='" + imageUrl + "'/>&nbsp;Loading...</li>");
	};

	FriendInfoManager.prototype.removeAll = function(){
		var ol = this.getFriendInfoOl();
		ol.html("");
	};