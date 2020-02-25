<%@page import="org.json.JSONObject"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
Object obj = request.getAttribute("write");

if(obj != null){
	boolean chk = (boolean) obj;
%>
	{"chk":"<%=chk%>"}
<%	
}
%>