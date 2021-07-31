<%@page import="com.franklin.logoutarmycd.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.franklin.logoutarmycd.web.SessionKeyEnum"%>
<%
	SystemConfig sysConfig = SystemConfig.getInstance();
	request.setAttribute("api_key", sysConfig.getApi_key());
	request.setAttribute("app_id", sysConfig.getAp_id());
%>

<center>uid: <%=session.getAttribute(SessionKeyEnum.USER_ID)%></center>

<script src="//connect.facebook.net/zh_TW/all.js"></script>
<script>
      // initialize the library with the API key
      FB.init({ 
    	  appId: '${app_id}',
          apiKey: '${api_key}',
		  status: true,
		  xfbml: true
      });
</script>


