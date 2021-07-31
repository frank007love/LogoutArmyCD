/*
 * Depend on 'commonUtil.js' 
 **/
    /**
     * Handle user click the save button on the CDArmyForm.
     */
	function SaveBtnClickListener(){}
	SaveBtnClickListener.execute = function(){
		CDManager.notifyCountDownChange(true);
	};
    /**
     * Handle user click the post button on the CDArmyForm.
     */
	function PostBtnClickListener(){}
	PostBtnClickListener.execute = function(){
		CDManager.postCountDownInfoToWall();
	};	
	/**
	 * The change event listener for the CDArmyForm's combobox.
	 */
	function ComboBoxOnChangeListener(){}
	ComboBoxOnChangeListener.execute = function(){
		CDManager.notifyCountDownChange(false);
	};

	function CountDownManager(){
		var userCDData;
		var armyCDForm;
		var messagePanel;
		
		this.setArmyCDFrom = function(formObj){
			armyCDForm = formObj;
		};
		this.setMessagePanel = function(panelObj){
			messagePanel = panelObj;
		};		
		this.getMessagePanel = function(){
			return messagePanel;
		};				
		this.getArmyCDForm = function(){
			return armyCDForm;
		};
		this.setUserCDData = function(cdData){
			userCDData = cdData;
		};
		this.getUserCDData = function(){
			return userCDData;
		};
		this.init();
	}
	CountDownManager.prototype.init = function(){
		this.setArmyCDFrom(new ArmyCDForm($('#army-cd-form')));
		this.getArmyCDForm().addSaveBtnClickListener(SaveBtnClickListener.execute);
		this.getArmyCDForm().addPostBtnClickListener(PostBtnClickListener.execute);
		//window.alert("Complete ArmyCDForm initializtion.");
		this.setMessagePanel(new MessagePanel($('#highlight-div')));
		//window.alert("Complete MessagePanel initializtion.");
		this.setUserCDData(new UserCDData());
		var thisObj = this;
		// init user amry info
		$.ajax({
			  url: 'getArmyInfo.action',
			  type: "POST",
			  dataType: 'xml',
			  success: function(data) {
				 thisObj.initUserArmyInfo(data);
			  }, error: function(data) {
				  window.alert("Link server failure.");
			  }
		});
	};	
	
	/**
	  * Setting User amry information in the form.
	  * The source data(xml object) is responsed from server.
	  **/
	CountDownManager.prototype.initUserArmyInfo = function(xmlObj){
		var root = $(xmlObj).find('root');
		var newUserFlag = root.text().match('NewUser');
		if( newUserFlag ){
			this.getMessagePanel().showNewUserPanel();
		} else {
			var armyCDForm = this.getArmyCDForm();
			var year, month, date;
			// Join date
			var joinDateText = root.find('joinDate').text();
			var joinDateSplitResult = joinDateText.split("-");
			year = joinDateSplitResult[0];
			month = joinDateSplitResult[1];
			date = joinDateSplitResult[2];
			var joinDate = new Date(year, month-1, date);
			
			// Join period date
			var joinPeriodDateXmlObj = root.find('joinPeriodDate');
			year = joinPeriodDateXmlObj.find('year').text();
			month = joinPeriodDateXmlObj.find('month').text();
			date = joinPeriodDateXmlObj.find('date').text();

			// Reduce date
			var reduceDays = root.find('reduceDays').text();
			var otherReduceDays = root.find('otherReduceDays').text();

			// Update armyCDForm
			armyCDForm.updateCDLabel(joinDate, year, month
					, date, reduceDays, otherReduceDays);

			// Setting User Countdown data
			this.updateUserCDData(joinDate, year, month, date, reduceDays, otherReduceDays);
			this.getMessagePanel().showOldUserPanel();
			var obj = this;
			obj.updateMessagePanel();
			$(document).everyTime( 1000, function(){
				obj.updateMessagePanel();
			} );
		}
	};
	CountDownManager.prototype.updateUserCDData = function(joinDate, joinPeriodYear,joinPeriodMonth,joinPeriodDate,
			reduceDays, otherReduceDays){
		var userData = this.getUserCDData();
		userData.setJoinFullDate(joinDate);
		userData.setJoinPeriodYear(joinPeriodYear);
		userData.setJoinPeriodMonth(joinPeriodMonth);
		userData.setJoinPeriodDate(joinPeriodDate);
		userData.setReduceDays(reduceDays);
		userData.setOtherReduceDays(otherReduceDays);
	};
	CountDownManager.prototype.updateMessagePanel = function(){
		var userData = this.getUserCDData();
		var leftDays = userData.calculateLeftTime(TimeType.DAY);
		var title = userData.getTitle();
		if( leftDays > 0 ){
			var leftSeonds = userData.calculateLeftTime(TimeType.SECOND);
			var percentage = userData.calculatePercentage();
			this.getMessagePanel().updateCDMessage(title, leftDays, leftSeonds, percentage);
		} else {
			var logoutDays = userData.calculateLogoutTime();
			this.getMessagePanel().updateLogoutMessage(title, logoutDays);
		}
	};
	CountDownManager.prototype.notifyCountDownChange = function(saveFlag){
		var form = this.getArmyCDForm();
		// Form information validation
		if(!form.dataValidation()){
			return;
		}
		// Update data
		var joinDate = new Date(form.getJoinYearVal(), 
				form.getJoinMonthVal()-1, form.getJoinDateVal());
		this.updateUserCDData(joinDate, form.getJoinPeriodYearVal(),
				form.getJoinPeriodMonthVal(), form.getJoinPeriodDateVal(), 
				form.getReduceDayVal(), form.getOtherReduceDayVal());
		this.getMessagePanel().showOldUserPanel();
		this.updateMessagePanel();
		
		if( saveFlag ) {
			this.saveCounterSetting();
		} else {
			form.showUnSaveText();
		}
	};
	/**
	  * Post the cd info to the facebook wall.
	  **/
	CountDownManager.prototype.postCountDownInfoToWall = function(){
	   var message = this.getMessagePanel().getCountDownMessage();
	   FBUtilAdapter.postMessageToWall('', 
			   ResourceKeeper.getLogoURL(),
			   ResourceKeeper.getAppURL(),
			   '好好關注你的兄弟吧!',
			   ' ',
			   message,
			   null);
	};
	/**
	  * Save the countdown setting to server.
	  **/
	CountDownManager.prototype.saveCounterSetting = function(){
		var form = this.getArmyCDForm();
		form.showLoadingImage();
		var userData = this.getUserCDData();
		$.ajax({
			  url: 'saveCounterSetting.action',
			  type: "POST",
			  dataType: 'html',
		      data: ({
		    	  joinYear : userData.getJoinYear(),
		    	  joinMonth : userData.getJoinMonth(),  
			      joinDate : userData.getJoinDate(),
			      joinPerYear: userData.getJoinPeriodYear(),
			      joinPerMonth: userData.getJoinPeriodMonth(),
			      joinPerDate: userData.getJoinPeriodDate(),
			      reduceDays: userData.getReduceDays(),
			      otherReduceDays: userData.getOtherReduceDays()
			  }), success: function(data) {
					form.hideLoadingImage();
			  }, error: function(data) {
			   	    window.alert("Link server failure.");
			   	 	form.hideLoadingImage();
			  }
		});
	};

	/**
	  * The army information form.
	  **/
	function ArmyCDForm(formObj){
		var obj = formObj;
		var userCDData = null;
		var joinDateText = $('#joinDate');
		var joinPeriodYearCombo = $('#join-period-year');
		var joinPeriodMonthCombo = $('#join-period-month');
		var joinPeriodDateCombo = $('#join-period-date');
		var reduceDayCombo = new ReduceDayComboxBox($('#reduce_day'), [0, 14, 22, 30]);
		var otherReduceDayCombo = new ReduceDayComboxBox($('#otherReduce_day'), [0, 30]);
		
		var saveBtn = $('#SaveBtn');
		var postBtn = $('#PostBtn');
		var saveLoadingLabel = new SaveLoadingLabel($("#save-loading-label"));
		
		var reduceDayComboPreVal = 0;
		var otherReduceDayComboPreVal = 0;

		this.getForm = function(){
			return obj;
		};
		this.setForm = function(newForm){
			obj = newForm;
		};
		this.getJoinDateText = function(){
			return joinDateText;
		};
		this.getJoinPeriodYearCombo = function(){
			return joinPeriodYearCombo;
		};
		this.getJoinPeriodMonthCombo = function(){
			return joinPeriodMonthCombo;
		};
		this.getJoinPeriodDateCombo = function(){
			return joinPeriodDateCombo;
		};
		this.getReduceDayCombo = function(){
			return reduceDayCombo;
		};
		this.getOtherReduceDayCombo = function(){
			return otherReduceDayCombo;
		};
		this.getSaveBtn = function(){
			return saveBtn;
		};
		this.getPostBtn = function(){
			return postBtn;
		};
		this.getSaveLoadingLabel = function(){
			return saveLoadingLabel;
		};
		this.getReduceDayComboPreVal = function(){
			return reduceDayComboPreVal;
		};
		this.setReduceDayComboPreVal = function(val){
			reduceDayComboPreVal = val;
		};
		this.getOtherReduceDayComboPreVal = function(){
			return otherReduceDayComboPreVal;
		};
		this.setOtherReduceDayComboPreVal = function(val){
			otherReduceDayComboPreVal = val;
		};		
		this.init();
	}
	ArmyCDForm.prototype.init = function(){
		this.initComboBox();
		this.initButtons();
		var joinDateText = this.getJoinDateText();
		joinDateText.datepicker({
			changeMonth: true,
			changeYear: true,
			disabled: true
		});
		joinDateText.datepicker('option', {dateFormat: 'yy-mm-dd'});
		joinDateText.change(ComboBoxOnChangeListener.execute);
		
		this.getSaveLoadingLabel().hide();
	};
	
	ArmyCDForm.prototype.showFeedBack = function(msg, id, time){
		$('#'+id).text(msg);
		$('#'+id).show();
		if( time != null ){
			setTimeout(function(){
				$('#'+id).hide();
			}, time);
		}
	};
	
	/**
	  * Initialize the buttons on ArmyCDForm.
	  **/
	ArmyCDForm.prototype.initButtons = function(){
		this.getSaveBtn().button();
		this.getPostBtn().button();
	};
	ArmyCDForm.prototype.initComboBox = function(){
		$(this.getJoinPeriodYearCombo()).change(ComboBoxOnChangeListener.execute);
		$(this.getJoinPeriodMonthCombo()).change(ComboBoxOnChangeListener.execute);
		$(this.getJoinPeriodDateCombo()).change(ComboBoxOnChangeListener.execute);
		
		var form = this;
		
		var ordCombo = this.getOtherReduceDayCombo();
		ordCombo.addListener('change', function(){
			if( this.value == "其它" ){
				$('#Class-OReduce-Day-Div').dialog({
					close: function(){
						//recover reduce day val
						form.setOtherReduceDayVal(form.getOtherReduceDayComboPreVal());
					},
					resizable: false,
					width: 200,
					modal: true,
					buttons: [
								{
									text: '儲存',
									id: 'CORD-Save-Btn',
									click: function(){
									var text = $('#CORD-Text').val();
									if( NumberUtillity.notNagativeInteger(text) ){
										form.setOtherReduceDayVal(text);
										ComboBoxOnChangeListener.execute();
										$('#Class-OReduce-Day-Div').dialog('close');
									} else {
										$('#Class-OReduce-Day-Div').dialog('close');
										form.showFeedBack('請輸入正確數字!', 'other_reduce_day_err', 2000);
									}
								}
								},{
									text: '取消',
									id: 'CORD-Cancel-Btn',
									click: function(){
										$('#Class-OReduce-Day-Div').dialog('close');
								}
								}]
				});		
				return;
			}
			form.setOtherReduceDayComboPreVal(form.getReduceDayVal());
			ComboBoxOnChangeListener.execute();
		});
		
		this.setReduceDayComboPreVal(this.getReduceDayVal());
		var rdCombo = this.getReduceDayCombo();
		rdCombo.addListener('change', function(){
			if( this.value == "其它" ){
				$('#Class-Reduce-Day-Div').dialog({
					close: function(){
						//recover reduce day val
						form.setReduceDayVal(form.getReduceDayComboPreVal());
					},
					resizable: false,
					width: 200,
					modal: true,
					buttons: [
								{
									text: '儲存',
									id: 'CRD-Save-Btn',
									click: function(){
										var text = $('#CRD-Text').val();
										if( NumberUtillity.notNagativeInteger(text) ){
											form.setReduceDayVal(text);
											ComboBoxOnChangeListener.execute();
											$('#Class-Reduce-Day-Div').dialog('close');
										} else {
											$('#Class-Reduce-Day-Div').dialog('close');
											form.showFeedBack('請輸入正確數字!', 'reduce_day_err', 2000);
										}
									}
								},{
									text: '取消',
									id: 'CRD-Cancel-Btn',
									click: function(){
										$('#Class-Reduce-Day-Div').dialog('close');
									}
								}]					
				});
				return;
			}
			form.setReduceDayComboPreVal(form.getReduceDayVal());
			ComboBoxOnChangeListener.execute();
		});
	};
	ArmyCDForm.prototype.getJoinFullDateVal = function(){
		return this.getJoinDateText().val();
	};
	ArmyCDForm.prototype.getJoinYearVal = function(){
		return this.getJoinFullDateVal().split("-")[0];
	};
	ArmyCDForm.prototype.getJoinMonthVal = function(){
		return this.getJoinFullDateVal().split("-")[1];
	};
	ArmyCDForm.prototype.getJoinDateVal = function(){
		return this.getJoinFullDateVal().split("-")[2];
	};
	ArmyCDForm.prototype.getJoinPeriodYearVal = function(){
		return this.getJoinPeriodYearCombo().val();
	};
	ArmyCDForm.prototype.getJoinPeriodMonthVal = function(){
		return this.getJoinPeriodMonthCombo().val();
	};	
	ArmyCDForm.prototype.getJoinPeriodDateVal = function(){
		return this.getJoinPeriodDateCombo().val();
	};		
	ArmyCDForm.prototype.getReduceDayVal = function(){
		return this.getReduceDayCombo().getValue();
	};
	ArmyCDForm.prototype.setReduceDayVal = function(val){
		this.getReduceDayCombo().insertVal(val);
		this.setReduceDayComboPreVal(val);
	};	
	ArmyCDForm.prototype.getOtherReduceDayVal = function(){
		return this.getOtherReduceDayCombo().getValue();
	};			
	ArmyCDForm.prototype.setOtherReduceDayVal = function(val){
		this.getOtherReduceDayCombo().insertVal(val);
		this.setOtherReduceDayComboPreVal(val);
	};		
	ArmyCDForm.prototype.addSaveBtnClickListener = function(listener){		
		this.getSaveBtn().bind({
			click : listener
		});
	};
	ArmyCDForm.prototype.addPostBtnClickListener = function(listener){		
		this.getPostBtn().bind({
			click : listener
		});
	};	
	
	/**
	  * Validate the armyCDForm data fields.
	  **/
	ArmyCDForm.prototype.dataValidation = function(){
		var joinDatetext = this.getJoinDateText();
		if( joinDatetext.val() == "" ){
			this.showFeedBack("入伍時間不可為空!", "joinDate_err");
			joinDatetext.focus();
			return false;
		}
		if( joinDatetext.val().match(/^((19|20)?[0-9]{2}[- -.](0?[1-9]|1[012])[- -.](0?[1-9]|[12][0-9]|3[01]))*$/) == null){
			this.showFeedBack("入伍時間格式錯誤!", "joinDate_err");
			joinDatetext.focus();
			return false;
		} else {
			this.showFeedBack("", "joinDate_err");
		}
		
		
		if( NumberUtillity.isInteger(this.getReduceDayVal()) ){
			return true;
		}
		$(this.getReduceDayCombo()).focus();
		if( NumberUtillity.isInteger(this.getOtherReduceDayVal()) ){
			return true;
		}
		$(this.getOtherReduceDayCombo()).focus();
		
		return false;
	};
	/**
	  * Update Countdown Form Labls.
	  * @param joinDate join date storage
	  **/
	ArmyCDForm.prototype.updateCDLabel = function(joinDate, joinPeriodYear,
			joinPeriodMonth, joinPeriodDate, ReduceDays, OtherReduceDays){
		// update join date
		var joinDateString = joinDate.getFullYear() + "-" + (joinDate.getMonth()+1)
			+ "-" + joinDate.getDate();
		this.getJoinDateText().val(joinDateString);
		// update join period date
		this.getJoinPeriodYearCombo().val(joinPeriodYear);
		this.getJoinPeriodMonthCombo().val(joinPeriodMonth);
		this.getJoinPeriodDateCombo().val(joinPeriodDate);
		// update reduce days
		this.setReduceDayVal(ReduceDays);
		this.setOtherReduceDayVal(OtherReduceDays);
	};
	ArmyCDForm.prototype.showLoadingImage = function(){
		this.getSaveLoadingLabel().show();
	};
	ArmyCDForm.prototype.hideLoadingImage = function(){
		this.getSaveLoadingLabel().hide();
	};
	ArmyCDForm.prototype.showUnSaveText = function(){
		this.getSaveLoadingLabel().showUnSaveText();
	};
	

	/**
	 * This is a save loading label class.
	 * It's used to notify user that the request is handling.
	 */
	function SaveLoadingLabel(obj){
		var labelObj = obj;
		this.getLabel = function(){
			return labelObj;
		};
	}
	SaveLoadingLabel.prototype.show = function(){
		var loadingImageUrl = ResourceUtillity.getLoadingImageSrc();
		this.getLabel().html("<img src=\"" + loadingImageUrl + "\" title=\"Saving\"></img>");
	};
	SaveLoadingLabel.prototype.hide = function(){
		this.getLabel().html("");
	};
	SaveLoadingLabel.prototype.showUnSaveText = function(){
		this.getLabel().html("<font color=\"red\">儲存?</font>");
	};	
	
	/**
	 * This is a reduce day combo box class.
	 * It cotains 0, 14, 22, 30 initialization data.
	 * It offers the user customize his reduce days.
	 */
	function ReduceDayComboxBox(combo, initValues){
		var comboObj = combo;
		var values= new Array();
		
		this.getValues = function(){
			return values;
		};
		this.getComboBox = function(){
			return comboObj;
		};

		this.init(initValues);
	}
	ReduceDayComboxBox.prototype.init = function(initValues){
		// Init the combox value
		var values = this.getValues();
		$(initValues).each(function(){
			values[this] = this;
		});
		
		this.createComboBoxUI(22);
	};
	ReduceDayComboxBox.prototype.insertVal = function(val){
		var values = this.getValues();
		var length = values.length;
		values[val] = val;
		this.createComboBoxUI(val);
	};
	ReduceDayComboxBox.prototype.createComboBoxUI = function(select){
		var comboObj = this.getComboBox();
		var values = this.getValues();
		this.clear();
		$(values).each(function(){
			var value = parseInt(this);
			if( !NumberUtillity.isInteger(value) ) return;
			var selectString = select == value ? 'selected="selected"' : '';
			comboObj.append('<option value="' + value + '" ' + selectString + '>' + value + '</option>');
		});
		comboObj.append('<option value="其它">其它 </option>');
	};
	ReduceDayComboxBox.prototype.clear = function(){
		var comboObj = this.getComboBox();
		var children = comboObj.children();
		$(children).each(function(){
			$(this).remove();
		});
	};
	ReduceDayComboxBox.prototype.getValue = function(){
		return this.getComboBox().val();
	};
	ReduceDayComboxBox.prototype.focus = function(){
		this.getComboBox().focus();
	};
	ReduceDayComboxBox.prototype.addListener = function(event, listener){
		this.getComboBox().bind(event, listener);
	};
	
	