<%@page import="com.restfb.types.NamedFacebookType"%>
<%@page import="com.restfb.json.JsonException"%>
<%@page import="com.restfb.json.JsonObject"%>
<%@page import="com.restfb.Facebook"%>
<%@page import="com.restfb.Parameter"%>
<%@page import="com.restfb.Connection"%>
<%@page import="com.restfb.types.User"%>
<%@page import="com.restfb.DefaultFacebookClient"%>
<%@page import="com.restfb.FacebookClient"%>
<%@page import="com.franklin.logoutarmycd.web.SessionKeyEnum"%>
<%@page import="com.franklin.logoutarmycd.SystemConfig"%>
<%@page import="com.google.code.facebookapi.FacebookWebappHelper"%>
<%@page import="com.google.code.facebookapi.FacebookJaxbRestClient"%>
<%@page import="com.google.code.facebookapi.IFacebookRestClient"%>
<html>
<title>
<head>for testing</head>
</title>
<body>
<%
	SystemConfig sysConfig = SystemConfig.getInstance();
	String apiKey = sysConfig.getApi_key();
	String secret = sysConfig.getSecret_key();

	String access_token = (String) session
			.getAttribute(SessionKeyEnum.ACCESS_TOKEN);

	if (access_token == null) {
		IFacebookRestClient<?> client = new FacebookJaxbRestClient(
				apiKey, secret);
		FacebookWebappHelper<?> facebook = new FacebookWebappHelper(
				request, response, apiKey, secret, client);
		String authURL = SystemConfig.getInstance().getAuthorizeUrl();
		String loginurl = facebook.getLoginUrl(authURL, true);
		facebook.redirect(loginurl);
		return;
	}

	FacebookClient facebookClient = new DefaultFacebookClient(
			access_token);
	Connection<User> myFriends = facebookClient.fetchConnection(
			"me/friends", User.class);
	out.println("myFriends size=" + myFriends.getData().size());

	class FqlUser extends User {
		@Facebook
		Boolean installed;

	};

	/*Connection<JsonObject> myAPPFriends = facebookClient.fetchConnection(
			"me/friends", JsonObject.class,
			Parameter.with("fields", "installed"));*/
	Connection<FqlUser> myAPPFriends = facebookClient
			.fetchConnection("me/friends", FqlUser.class,
					Parameter.with("fields", "installed"));

	out.println("myAPPFriends size=" + myAPPFriends.getData().size());
	
	
	/*	
	 for( JsonObject obj : myAPPFriends.getData() ){
	 try {
	 Object installobj = obj.get("installed");
	 out.println("istall: " + installobj);
	 } catch( JsonException e ){
	
	 }
	 }*/
%>
</body>