<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/summernote-lite.min.css">
<style type="text/css">
	#bbs table {
	    width:580px;
	    margin-left:10px;
	    border:1px solid black;
	    border-collapse:collapse;
	    font-size:14px;
	    
	}
	
	#bbs table caption {
	    font-size:20px;
	    font-weight:bold;
	    margin-bottom:10px;
	}
	
	#bbs table th {
	    text-align:center;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	#bbs table td {
	    text-align:left;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	
	.odd {background:silver}
	
	#t1 tbody tr:nth-child(2) td input[type=text]{
		background-color: #dedede;
	}
		
</style>

</head>
<body>
	<%
	Object obj = request.getAttribute("forView");
	String cPage = (String) request.getAttribute("cPage");
	if(obj != null){
		BbsVO vo = (BbsVO) obj;
	%>
	<div id="bbs">
		<form name="frm1" action="Controller?type=edit" method="post" encType="multipart/form-data">
		<input type="hidden" name="b_idx" value="<%=vo.getB_idx() %>"/>
		<input type="hidden" name="cPage" value="<%=cPage %>"/>
			<table id="t1" summary="게시판 수정">
				<caption>게시판 수정</caption>
				<tbody>
					<tr>
						<th>제목:</th>
						<td><input type="text" name="subject" id="subject"size="45" value="<%=vo.getSubject() %>"/></td>
					</tr>
					<tr>
						<th>이름:</th>
						<td><input type="text" name="writer" id="writer" size="12" value="<%=vo.getWriter() %>" readonly="readonly"/></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<td><input type="file" name="file" id="file"/><%if(vo.getFile_name() != null){ %>(<%=vo.getOri_name() %>)<%} %></td>
					</tr>
					<tr>
						<th>비밀번호:</th>
						<td><input type="password" name="pwd" id="pwd" size="12"/></td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" id="content" name="content"/>
		</form>
		<table>
			<tr>
				<th style="width: 70px;">내용:</th>
				<td><textarea name="cont" id="cont" cols="50" rows="8"><%=vo.getContent() %></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="수정" id="edit_btn"/>
					<input type="button" value="목록" onclick="goList('<%=cPage %>')"/>
				</td>
			</tr>
		</table>
	</div>
	<%
	}
	%>
	
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="js/summernote-lite.min.js"></script>
	<script type="text/javascript" src="js/lang/summernote-ko-KR.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			
			$("#edit_btn").on("click", function(){
				var subject = $("#subject").val().trim();
				var writer = $("#writer").val().trim();
				var cont = $("#cont").val().trim();
				var pwd = $("#pwd").val().trim();
				
				if(subject.length < 1){
					alert("제목을 입력하세요!");
					$("#subject").focus();
					return;
				}
				
				if(writer.length < 1){
					alert("이름을 입력하세요!");
					$("#writer").focus();
					return;
				}
				
				if(cont.length < 1){
					alert("내용을 입력하세요!");
					$("#content").focus();
					return;
				}
				
				if(pwd.length < 1){
					alert("비밀번호를 입력하세요!");
					$("#pwd").focus();
					return;
				}
				
				$("#content").val(cont);
				
				document.frm1.submit();
			});
			
			$("#cont").summernote({
				dialogsInBody: true,
				width: 400,
				height: 400,
				lang: "ko-KR",
				callbacks:{
					onImageUpload: function(files, editor){
						for(var i=0; i<files.length; i++){
							sendFile(files[i], editor);
						}
					}
				}
			});
				
		});
		
		function goList(cPage) {
			location.href = "Controller?type=list&cPage="+cPage;
		}
		
		function sendFile(file, editor) {
			// console.log(file);
			var frm = new FormData(); // <form encType="multipart/form-data"></form>
			frm.append("upload", file);
			
			$.ajax({
				url: "Controller?type=saveImage",
				type: "post",
				data: frm,
				dataType: "json",
				contentType: false,
				processData: false
			}).done(function(res){
				// console.log(res.img_url);
				$("#cont").summernote("editor.insertImage", res.img_url);
			}).fail(function(err){
				console.log(err);
			});
		}
	
	</script>
	
</body>
</html>