	/**
	 * This is a MilliSecondTranslator,
	 */
	function MilliSecondTranslator(){}
	MilliSecondTranslator.milliToSecond = 1000;
	MilliSecondTranslator.milliToMinute = MilliSecondTranslator.milliToSecond * 60;
	MilliSecondTranslator.milliToHour = MilliSecondTranslator.milliToMinute * 60;
	MilliSecondTranslator.milliToDay = MilliSecondTranslator.milliToHour * 24;
	
	// Translate milliseconds to seconds
	MilliSecondTranslator.translateToSeconds = function(milli){
		return parseInt(milli / MilliSecondTranslator.milliToSecond);
	};
	// Translate milliseconds to munites
	MilliSecondTranslator.translateToMunites = function(milli){
		return parseInt(milli / MilliSecondTranslator.milliToMinute);
	};
	// Translate milliseconds to hours
	MilliSecondTranslator.translateToHours = function(milli){
		return parseInt(milli / MilliSecondTranslator.milliToHour);
	};
	// Translate milliseconds to days
	MilliSecondTranslator.translateToDays = function(milli){
		return parseInt(milli / MilliSecondTranslator.milliToDay);
	};
	// return milli
	MilliSecondTranslator.translateToMilliSeconds = function(milli){
		return milli;
	};
	/**
	 * It's a milli secnod translator factory.
	 * It's used to create a suitable translator by type.
	 */
	function MilliSecondTranslatorFactory(){}
	MilliSecondTranslatorFactory.createTranslator = function(type){
		if( type == TimeType.DAY ){
			return MilliSecondTranslator.translateToDays;
		} else if( type == TimeType.HOUR ){
			return MilliSecondTranslator.translateToHours;
		} else if( type == TimeType.MUNITE ){
			return MilliSecondTranslator.translateToMunites;
		} else if ( type == TimeType.SECOND ){
			return MilliSecondTranslator.translateToSeconds;
		} else {
			return MilliSecondTranslator.translateToMilliSeconds;
		}
	};

	function TimeType(){}
	TimeType.DAY = "Day";
	TimeType.HOUR = "Hour";
	TimeType.MUNITE = "Munite";
	TimeType.SECOND = "Second";
	TimeType.MILLISECOND = "Milli-Second";
	