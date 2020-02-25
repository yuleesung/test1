<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
Object obj = request.getAttribute("write_ok");
// System.out.println(obj);
if(obj != null){
	boolean chk = (boolean) obj;
%>
	{"chk":"<%=chk %>"}
<%
}
%>