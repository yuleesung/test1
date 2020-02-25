<%@page import="java.io.File"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

Object obj = request.getAttribute("saveImage");
String f_name = null;
if(obj != null){
	f_name = (String) obj;
}

%>
{"img_url":"<%=request.getContextPath() %>/editor_img/${saveImage}"}
<%-- {"img_url":"<%=request.getContextPath()/editor_img/<%=f_name %>"} --%>