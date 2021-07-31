<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邀請朋友</title>
<link href="css/highlighter-form/styles.css" type="text/css"
	rel="stylesheet" />
</head>
 <body>
<div align="center"><jsp:include page="header.jsp"></jsp:include>
</div>
<br><br><br><br>
<fb:serverFbml width="755">  
  <script type="text/fbml">  
  <fb:fbml>
  <fb:request-form action="${ap_url}"
    method="POST" invite="true" type="國軍登出倒數計時器 "
   content="歡迎一起使用登出國軍倒數計時器 <fb:req-choice url='${fb_apurl}' label='開始使用' />  " >
   <fb:multi-friend-selector showborder="true" exclude_ids="${exclude_friend_string}" actiontext="邀請你的朋友加入"/>  
  </fb:request-form>  
  </fb:fbml>  
  </script>  
</fb:serverFbml>

 <jsp:include page="footer.jsp"></jsp:include>
 <script>
 	FB.Canvas.setAutoGrow();
 </script>
  </body>
</html>