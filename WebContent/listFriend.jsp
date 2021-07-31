<%@page import="com.google.code.facebookapi.schema.FriendsGetResponse"%>
<%@page import="com.google.code.facebookapi.FacebookJaxbRestClient"%>
<%
	FacebookJaxbRestClient client = (FacebookJaxbRestClient)session.getAttribute("client");
	FriendsGetResponse fg_response = (FriendsGetResponse)client.friends_get();
	for( Long l : fg_response.getUid()){
		out.print(l+",");
	}
%>