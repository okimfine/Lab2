<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head></head>  
<body>
<body bgcolor = "#EEF4E1">
	<center><h1>图书查询</h1>
	<form action = "Gotoadd">
	<br><input type="submit" value="添加一本图书"><br><br><br>
	</form>
    <form action = "Search">
             作者姓名:<br>
    <input type="text" name = "username" onfocus="this.value=''">
    <br><br>
    <input type="submit" value="查询"/>
    </form>
    </center> 
</body>
</html>
</html>