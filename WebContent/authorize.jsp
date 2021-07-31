
<%@page import="com.franklin.logoutarmycd.web.SessionKeyEnum"%>
<%@page import="com.franklin.logoutarmycd.SystemConfig"%><%@page import="com.restfb.DefaultWebRequestor"%>
<%@page import="com.restfb.WebRequestor"%>
<% 
	SystemConfig sysConfig = SystemConfig.getInstance();
	String apiKey = sysConfig.getApi_key();
	String secretKey = sysConfig.getSecret_key();
	String apId = sysConfig.getAp_id();
	String ap_url = sysConfig.getApUrl();
	String facebook_url = sysConfig.getFacebookApUrl();
	
	String code = (String)request.getParameter("code");
	String next_page_url = "https://graph.facebook.com/oauth/access_token?client_id=" + apId + 
			"&redirect_uri=" + ap_url + "authorize.jsp&client_secret=" + secretKey +
			"&code=" + code;
	WebRequestor webRequestor = new DefaultWebRequestor();
	String body = webRequestor.executeGet(next_page_url).getBody();
	// proccess access_token body.
	String []splitResilt =  body.split("=");
	String access_token_str = splitResilt[1];
	if( access_token_str.contains("&") ){
		access_token_str = access_token_str.substring(0, access_token_str.indexOf('&'));
	}
	// save the access_token info to the session.
	session.setAttribute(SessionKeyEnum.ACCESS_TOKEN, access_token_str);
	session.setAttribute(SessionKeyEnum.FACEBOOK_CONTROLLER_RESET_FLAG, "true");
	response.sendRedirect(facebook_url);
%>