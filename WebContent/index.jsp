<%@page import="org.slf4j.bridge.SLF4JBridgeHandler"%>
<%@page import="com.google.code.facebookapi.IFacebookRestClient"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="com.restfb.DefaultFacebookClient"%>
<%@page import="com.restfb.FacebookClient"%>
<%@page import="com.restfb.types.User"%>
<%@page import="com.restfb.DefaultLegacyFacebookClient"%>
<%@page import="com.restfb.LegacyFacebookClient"%>
<%@page import="com.franklin.logoutarmycd.web.SessionKeyEnum"%>
<%@page import="com.franklin.logoutarmycd.SystemConfigEnum"%>
<%@page import="com.franklin.logoutarmycd.SystemConfig"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="com.google.code.facebookapi.FacebookWebappHelper"%>
<%@page import="com.google.code.facebookapi.FacebookJaxbRestClient"%>
<%
	SLF4JBridgeHandler.install();

	String webRootPath = application.getRealPath("/");
	System.setProperty(SystemConfigEnum.WEB_PATH, webRootPath);
	System.setProperty(SystemConfigEnum.SYS_METADATA_PATH, webRootPath
			+ "/_metadata");
	
	response.sendRedirect("counter.action");
%>