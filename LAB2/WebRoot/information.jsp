<%@ page contentType="text/html; charset=UTF-8" import="java.util.LinkedList,java.util.List"
pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
<body bgcolor = "#EEF4E1">
    <h1>
        <s:property value="username" />详细信息
    </h1>
<table border="1" align="center">
<tr>
    <td>编号</td>
    <td>姓名</td>
    <td>年龄</td>
    <td>国家</td>
<tr>
    <%List<String> inilist = (List<String>)session.getAttribute("inilist");
    for (int i = 0; i < inilist.size(); i++) {
  out.print("<td align=\"center\">"+inilist.get(i)+"</td>");
} %>
</tr>
</table>
<h1>书籍详细信息</h1>
<table border="1" align="center">
<tr>
    <td>ISBN</td>
    <td>书名</td>
    <td>作者编号</td>
    <td>出版社</td>
    <td>出版日期</td>
    <td>价格</td>
    <td>修改</td>
    <td>删除</td>
<tr>
    <%List<String> list = (List<String>)session.getAttribute("list");
    for (int i = 0; i < list.size(); i+=6) {
  out.print("<td align=\"center\">"+list.get(i)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+1)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+2)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+3)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+4)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+5)+"</td>");
  out.print("<td align=\"center\"><a href=Gotoedit?isbn="+list.get(i)+">修改</td>");
  out.print("<td align=\"center\"><a href=Delete.action?isbn="+list.get(i)+">删除</td>");
  
} %>
</tr>
</table>
<a href=Search?username=<%out.print(inilist.get(1)); %>>返回</a>
    <form action = "Login">
    <input type="submit" value="返回主页"/>
    </form>  
</body>
</html>