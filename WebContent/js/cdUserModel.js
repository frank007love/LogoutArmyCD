	/**
	  * Store user countdown data
	  *
	  * @param j_date Date type
	  * @param j_date Date type
	  **/
	function UserCDData(j_date, jp_year, jp_month, jp_date, rd_days, ord_days ){
		var joinDate = j_date;
		var joinPeriodYear = jp_year;
		var joinPeriodMonth = jp_month;
		var joinPeriodDate = jp_date;
		var reduceDays = rd_days;
		var otherReduceDays = ord_days;

		// Join amry date related methods.
		this.getJoinFullDate = function(){
			return joinDate;
		};
		this.setJoinFullDate = function(date){
			joinDate = date;
		};
		this.getJoinYear = function(){
			return joinDate.getFullYear();
		};
		this.getJoinMonth = function(){
			return joinDate.getMonth() + 1 ;
		};
		this.getJoinDate = function(){
			return joinDate.getDate();
		};

		// Join amry period date methods.
		this.setJoinPeriodYear = function(year){
			joinPeriodYear = year;
		};
		this.setJoinPeriodMonth = function(month){
			joinPeriodMonth = month;
		};
		this.setJoinPeriodDate = function(date){
			joinPeriodDate = date;
		};
		this.getJoinPeriodYear = function(){
			return joinPeriodYear;
		};
		this.getJoinPeriodMonth = function(){
			return joinPeriodMonth;
		};
		this.getJoinPeriodDate = function(){
			return joinPeriodDate;
		};

		// Recude amry days related methods.
		this.getReduceDays = function(){
			return reduceDays;
		};
		this.setReduceDays = function(days){
			reduceDays = days;
		};
		this.getOtherReduceDays = function(){
			return otherReduceDays;
		};
		this.setOtherReduceDays = function(days){
			otherReduceDays = days;
		};
	}
	/**
	  * Get the percentage from join to now.
	  * nowDuration / leftDuration
	  **/
	UserCDData.prototype.calculatePercentage = function(){
		var armyDayDuration = this.getAllDuration(TimeType.MILLISECOND);
		var nowDuration = this.getPastDuration(TimeType.MILLISECOND);
		var result = 0;
		if( armyDayDuration != NaN && armyDayDuration >= 0 )
			result = parseInt((nowDuration / armyDayDuration) * 10000 ) / 100;

		result = result >= 100 ? 100 : result;
		result = result < 0 ? 0 : result;
		return result;
	};
	UserCDData.prototype.getPastDuration = function(type){
		var nowDate = new Date();
		var milliSeconds = nowDate.getTime() - this.getJoinFullDate().getTime();
		var translator = MilliSecondTranslatorFactory.createTranslator(type);

		return translator.call(this, milliSeconds);
	};
	UserCDData.prototype.getAllDuration = function(type){
		var milliSeconds = this.getEndDate().getTime() - this.getJoinFullDate().getTime();
		var translator = MilliSecondTranslatorFactory.createTranslator(type);

		return translator.call(this, milliSeconds);
	};
	/**
	  *  Get the army end date.
	  **/
	UserCDData.prototype.getEndDate = function(){	
		// Join army time
		var startYear = this.getJoinYear();
		var startMonth = this.getJoinMonth();
		var startDate = this.getJoinDate();

		// Join army period
		var joinPerYear = this.getJoinPeriodYear();
		var joinPerMonth = this.getJoinPeriodMonth();
		var joinPerDate = this.getJoinPeriodDate();

		// ReduceDays
		var reduceDays = this.getReduceDays();
		var otherReduceDays = this.getOtherReduceDays();
		
		// Calculate end date.
		var endYear = parseInt(startYear, 10) + parseInt(joinPerYear, 10);
		var endMonth = parseInt(startMonth, 10) + parseInt(joinPerMonth, 10);
		var endDate = parseInt(startDate, 10) + parseInt(joinPerDate, 10) - 
			parseInt(reduceDays, 10) - parseInt(otherReduceDays, 10);

		return new Date(endYear, endMonth-1, endDate);
	};
	/**
	  * Calculate the time that user left the army.
	  * 
	  *	@return milli_secondTime
	  **/
	UserCDData.prototype.calculateLeftTime = function(type){
		var endDate = this.getEndDate();
		var nowDate = new Date();
		var milli_secondTime = endDate.getTime() - nowDate.getTime();
		var translator = MilliSecondTranslatorFactory.createTranslator(type);
		var result = translator.call(this, milli_secondTime);
		if( result <= 0 ) result = 0;
		return result;
	};
	UserCDData.prototype.calculateLogoutTime = function(){
		var endDate = this.getEndDate();
		var nowDate = new Date();
		var milli_secondTime = nowDate.getTime() - endDate.getTime();
		var translator = MilliSecondTranslatorFactory.createTranslator(TimeType.DAY);
		var result = translator.call(this, milli_secondTime);
		if( result <= 0 ) result = 0;
		return result;
	};
	
	UserCDData.prototype.getTitle = function(){
		var leftDay = this.calculateLeftTime(TimeType.DAY);
		var past = this.getPastDuration(TimeType.DAY);
		var percent = this.calculatePercentage();
		var reduceDay = this.getReduceDays() + this.getOtherReduceDays();
		if( leftDay == 0 && reduceDay != 0 ) 
			return "榮譽假都是假的, 退伍才是真的";
		else if( leftDay == 0 && reduceDay == 0 ){
			return "滿靶退伍, 我崇拜你";
		}
		if( past <= 0  ){
			return "還沒入伍就用, 真捧場"; 
		}
		if( past <= 37 ){
			return "新兵戰士, 上下樓梯, 一步一階, 步步踏實";
		} else if( percent >= 85 ){
			return "有沒有人告訴你, 老兵八字輕?";
		} else if( past > 180 && leftDay > 100 ){
			return "威! 別忘記打一兵阿!";
		} else if( leftDay == 100 ){
			return "今天剛好滿百, 要慶祝一下嗎?";
		} else if( leftDay < 100 ){
			return "破百嚕, 還要吃" + leftDay + "顆饅頭阿~";
		} else if( past > 365 ) {
			return "破冬了, 志願役嗎, 祝你20年";
		}
		return "";
	};
	