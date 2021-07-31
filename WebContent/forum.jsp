<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Forum direction page</title>
<script type="text/javascript">
	var forumUrl = "${forum_url}";
	function init(){
		window.top.location = forumUrl;
	}
</script>
</head>
<body onload="init();">

</body>
</html>