/**
 * ResourceUtillity is used to manage resource.
 */
function ResourceUtillity(){}
ResourceUtillity.commonFolderUrl = function(){
	return "images/common/";
};
ResourceUtillity.getLoadingImageSrc = function(){
	return ResourceUtillity.commonFolderUrl() + "loading.gif";
};
ResourceUtillity.getSortDescImageSrc = function(){
	return ResourceUtillity.commonFolderUrl() + "sort_desc.gif";
};
ResourceUtillity.getRefreshImageSrc = function(){
	return ResourceUtillity.commonFolderUrl() + "refresh.gif";
};

function NumberUtillity(){}
NumberUtillity.isInteger = function(aVal){
	return (aVal.toString().search(/^-?[0-9]+$/) == 0);
};
NumberUtillity.notNagativeInteger = function(aVal){
	if( this.isInteger(aVal)){
		return aVal >= 0;
	}
	return false;
};

