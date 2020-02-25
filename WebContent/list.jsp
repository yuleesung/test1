<%@page import="bbs.util.Paging"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/jquery-ui.css"/>
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
	
	#bbs table th,#bbs table td {
	    text-align:center;
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
	
	/* paging */
	
	table tfoot ol.paging {
	    list-style:none;
	}
	
	table tfoot ol.paging li {
	    float:left;
	    margin-right:8px;
	}
	
	table tfoot ol.paging li a {
	    display:block;
	    padding:3px 7px;
	    border:1px solid #00B3DC;
	    color:#2f313e;
	    font-weight:bold;
	}
	
	table tfoot ol.paging li a:hover {
	    background:#00B3DC;
	    color:white;
	    font-weight:bold;
	}
	
	.disable {
	    padding:3px 7px;
	    border:1px solid silver;
	    color:silver;
	}
	
	.now {
	   padding:3px 7px;
	    border:1px solid #ff4aa5;
	    background:#ff4aa5;
	    color:white;
	    font-weight:bold;
	}
	#popup{display: none;}
	#popup table caption{
		text-indent: -9999px;
		height: 0;
	}
	#popup table tbody tr:last-child td{
		text-align: right;
	}
	#t2 tbody tr:last-child td:last-child{
		text-align: left;
	}
</style>
</head>

<%
request.setCharacterEncoding("utf-8");
%>
<body>
	<div id="bbs">
		<table id="t1" summary="게시판 목록">
			<caption>게시판 목록</caption>
			<thead>
				<tr class="title">
					<th class="no">번호</th>
					<th class="subject">제목</th>
					<th class="writer">글쓴이</th>
					<th class="reg">날짜</th>
					<th class="hit">조회수</th>
				</tr>
			</thead>
			
			<tfoot>
            	<tr>
            		<td colspan="4">
            			<ol class="paging">
            			<%
            			// 페이징을 위해 request에 저장된 page객체를 얻어낸다.
            			Object obj = request.getAttribute("page");
            			Paging pvo = null;
            			if(obj != null){
            				pvo = (Paging) obj;
            			%>
            				<%if(pvo.getStartPage() < pvo.getPagePerBlock()){ %>
            				<li class="disable">&lt;</li>
            				<%}else{ %>
            				<li>
            					<a href="Controller?type=list&cPage=<%=pvo.getNowPage()-pvo.getPagePerBlock() %>">&lt;</a>
            				</li>
            				<%} %>
            				
            				<%for(int i=pvo.getStartPage(); i<=pvo.getEndPage(); i++){ 
            					if(pvo.getNowPage() == i){
            				%>
							<li class="now"><%=i %></li>
							<%	}else{ %>
         					<li>
         						<a href="Controller?type=list&cPage=<%=i %>"><%=i %></a>
         					</li>
         					<%	}
            				}	
            				%>
            				
            				<%if(pvo.getEndPage() < pvo.getTotalPage()){ %>
            				<li>
         						<a href="Controller?type=list&cPage=<%=pvo.getNowPage()+pvo.getPagePerBlock() %>">&gt;</a>
         					</li>
            				<%}else{ %>
         					<li class="disable">&gt;</li>
         					<%}
            			}		
            			%>
						</ol>
                 	</td>
					<td>
						<input type="button" value="글쓰기" id="write_btn"/>
					</td>
           		</tr>
           	</tfoot>
			<tbody>
			<%
			Object ar_obj = request.getAttribute("ar");
			BbsVO[] ar = null;
			if(ar_obj != null){
				ar = (BbsVO[]) ar_obj;
				
				for(int i=0; i<ar.length; i++){
					BbsVO vo = ar[i];
					int num = pvo.getTotalRecord()-((pvo.getNowPage()-1)*pvo.getNumPerPage()+i); // 페이지가 순서대로 나오게 하게끔 하기 위한 연산식
			%>
				<tr>
					<td><%=num %></td>
					<td style="text-align: left">
						<a href="Controller?type=view&cPage=<%=pvo.getNowPage() %>&b_idx=<%=vo.getB_idx() %>"><%=vo.getSubject() %></a>
						<%if(!vo.getC_list().isEmpty()){ %>
							(<%=vo.getC_list().size() %>)
						<%} %>
					</td>
					<td><%=vo.getWriter() %></td>
					<td><%=vo.getWrite_date().substring(0, 10) %></td>
					<td><%=vo.getHit() %></td>
				</tr>
			<%
				}
			}else{ %>
				<tr>
					<td colspan="5">게시물이 없습니다.</td>
				</tr>
			<%} %>
			</tbody>
		</table>
		
	</div>
	
	<div id="popup" title="게시판 글쓰기">
		<form name="frm1" action="Controller?type=write" method="post" enctype="multipart/form-data">
			<table id="t2" summary="게시판 글쓰기">
				<caption>게시판 글쓰기</caption>
				<tbody>
					<tr>
						<th>제목:</th>
						<td><input type="text" name="subject" id="subject"size="45"/></td>
					</tr>
					<tr>
						<th>이름:</th>
						<td><input type="text" name="writer" id="writer" size="12"/></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<td><input type="file" name="file" id="file"/></td>
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
				<td><textarea name="cont" id="cont" cols="50" rows="8"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="보내기" id="send_btn"/>
					<input type="button" value="닫기" id="close_btn"/>
				</td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/summernote-lite.min.js"></script>
	<script type="text/javascript" src="js/lang/summernote-ko-KR.min.js"></script>
	<script type="text/javascript">
		
		$(function(){
			$("#write_btn").bind("click", function(){
				
				$.ajax({
					url: "Controller",
					type: "get",
					data: "type="+encodeURIComponent("write"),
					dataType: "json"
				}).done(function(res){
					// console.log(res.chk);
					if(res.chk == "true"){
						$("#popup").dialog();
						$("#popup").dialog({width: 550});
					}
				}).fail(function(err){
					console.log(err);
				});
			});
			
			$("#close_btn").on("click", function(){
				$("#popup").dialog("close");
			});
			
			$("#popup").on("dialogclose", function(event, ui){
				clearWrite();
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
			
			$("#send_btn").on("click", function(){
				var subject = $("#subject").val().trim();
				var writer = $("#writer").val().trim();
				var cont = $("#cont").val().trim();
				// var file = $("input[name=file]")[0].files[0];
				var pwd = $("#pwd").val().trim();
				
				// console.log(subject+"/"+writer+"/"+content+"/"+file+"/"+pwd);
				// console.log(file);
				
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
				
				/*
				var frm1 = new FormData();
				frm1.append("subject", subject);
				frm1.append("writer", writer);
				frm1.append("content", content);
				frm1.append("file", file);
				frm1.append("pwd", pwd);
				
				$.ajax({
					url: "Controller?type=write",
					type: "post",
					data: frm1,
					dataType: "json",
					contentType: false,
					processData: false
				}).done(function(res){
					// console.log(res.chk);
					if(res.chk == "true"){
						list();
					}else{
						alert("저장을 실패했습니다!");
					}
					$("#popup").dialog("close");
				}).fail(function(err){
					console.log(err);
				});
				*/
			});
		});
		/*
		function list() {
			$.ajax({
				url: "Controller",
				type: "post",
				data: "type=write_ok",
				dataType: "json"
			}).done(function(res){
				//console.log(res.length);
				//console.log(res[0].nowpage);
				//console.log(res[1]);
				
				var msg = "";
				for(var i=0; i<res.length; i++){
					msg += "<tr style='text-align: left'>";
					msg += "<td>"+res[i].b_idx+"</td>";
					msg += "<td><a href='Controller?type=view&cPage=1&b_idx="+res[i].b_idx+"'>"+res[i].subject+"</a></td>";
					msg += "<td>"+res[i].writer+"</td>";
					msg += "<td>"+res[i].write_date.substr(0, 10)+"</td>";
					msg += "<td>"+res[i].hit+"</td>";
					msg += "</tr>";
				}
				$("#t1>tbody").html(msg);
				
			}).fail(function(err){
				console.log(err);
			});
		}
		*/
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
		
		function clearWrite() {
			$("#subject").val("");
			$("#writer").val("");
			$("#cont").summernote('reset');
			$("#file").val("");
			$("#pwd").val("");
		}
	
	</script>
</body>
</html>