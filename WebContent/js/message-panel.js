
	/**
	 * CountDown message panel.
	 * 
	 * @param panelObj
	 * @return
	 */
	function MessagePanel(panelObj){
		var cdMsgSpan = $('#cd-message');
		var lgMsgSpan = $('#logout-message');
		var newuserpanel = $('#newuser-mp');
		var olduserpanel = $('#olduser-mp');
		var titleLabel = $('#cd_title');
		var cdDaysLabel = $('#cd_days_label');
		var cdSecondLabel = $('#cd_second_label');
		var cdPercentLabel = $('#cd_percent_label');
		var lgDaysLabel = $('#logout_days_label');

		//Label Text related methods.
		this.setTitleText = function(text){
			titleLabel.text(text);
		};
		this.setCDDaysText = function(text){
			cdDaysLabel.text(text);
		};
		this.setSecondText = function(text){
			cdSecondLabel.text(text);
		};
		this.setPercentageText = function(text){
			cdPercentLabel.text(text);
		};
		this.setLogoutDaysText = function(text){
			lgDaysLabel.text(text);
		};
		this.getTitleText = function(){
			return titleLabel.text();
		};
		this.getSecondText = function(){
			return cdSecondLabel.text();
		};
		this.getCDDaysText = function(){
			return cdDaysLabel.text();
		};
		this.getPercentageText = function(){
			return cdPercentLabel.text();
		};
		this.getLogoutDaysText = function(){
			return lgDaysLabel.text();
		};
		
		//Panel related methods.
		this.getNewUserPanel = function(){
			return newuserpanel;
		};
		this.getOldUserPanel = function(){
			return olduserpanel;
		};		
		this.getCDMsgSpan = function(){
			return cdMsgSpan;
		}
		this.getLogoutMsgSpan = function(){
			return lgMsgSpan;
		}
		
		this.init();
	}
	MessagePanel.prototype.init = function(){
		this.getOldUserPanel().hide();
		this.getNewUserPanel().hide();
	};
	MessagePanel.prototype.showNewUserPanel = function(){
		this.getOldUserPanel().hide();
		this.getNewUserPanel().show();
	};
	MessagePanel.prototype.showOldUserPanel = function(){
		this.getNewUserPanel().hide();
		this.getOldUserPanel().show();
	};
	MessagePanel.prototype.getCountDownMessage = function(){
		var title = this.getTitleText();
		var days = this.getCDDaysText();
		var seconds = this.getSecondText();
		var percents = this.getPercentageText();
		
		if( days <= 0 ){
			var days = this.getLogoutDaysText();
			return title + " 你已經退伍" + days + "日了!";
		}
		return title + " 距離退伍剩餘" + days + "天, 剩下" + seconds + "秒, 為" + percents + "%";
	};
	MessagePanel.prototype.updateCDMessage = function(title, days, secondes, percentage){
		this.setTitleText(title);
		this.setCDDaysText(days);
		this.setSecondText(secondes);
		this.setPercentageText(percentage);
		this.getLogoutMsgSpan().hide();
		this.getCDMsgSpan().show();
	};
	
	MessagePanel.prototype.updateLogoutMessage = function(title, days){
		this.setTitleText(title);
		this.setLogoutDaysText(days);
		this.getCDMsgSpan().hide();
		this.getLogoutMsgSpan().show();
	};

	
	