<%@page import="mybatis.vo.CommentVO"%>
<%@page import="java.util.List"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
	
		
</style>

</head>
<body>
	<div id="bbs">
	<form method="post" >
		<table summary="게시판 글쓰기">
			<caption>게시판 글쓰기</caption>
			<tbody>
			<%
			Object obj = request.getAttribute("view");
			String cPage = (String) request.getAttribute("cPage");
			BbsVO vo = null;
			if(obj != null){
				vo = (BbsVO) obj;
			%>
				<tr>
					<th>제목:</th>
					<td><%=vo.getSubject() %></td>
				</tr>

				<tr>
					<th>첨부파일:</th>
					<td><a href="javascript: location.href= 'Controller?type=download&cPage=<%=cPage %>&b_idx=<%=vo.getB_idx() %>&file=<%=vo.getFile_name() %>'">
					<%if(vo.getFile_name() != null) {%>
						<%=vo.getOri_name() %>
					<%} %>
					</a></td>
				</tr>
				
				<tr>
					<th>이름:</th>
					<td><%=vo.getWriter() %></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><%=vo.getContent() %></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="button" value="수정" onclick="javascript: location.href='Controller?type=edit&cPage=<%=cPage %>&b_idx=<%=vo.getB_idx() %>'"/>
						<input type="button" value="삭제" onclick="del('<%=cPage %>', '<%=vo.getB_idx() %>')" />
						<input type="button" value="목록" onclick="javascript: location.href='Controller?type=list&cPage=<%=cPage %>'"/>
					</td>
				</tr>
			<%} %>
			</tbody>
		</table>
	</form>
	<form name="frm" method="post" action="Controller?type=comment">
		이름:<input type="text" name="writer" id="writer"/><br/>
		내용:<textarea rows="4" cols="55" name="content" id="content"></textarea><br/>
		비밀번호:<input type="password" name="pwd" id="pwd"/><br/>
		
		
		<%-- <input type="hidden" name="b_idx" value="<%=vo.getB_idx() %>"/> --%>
		<input type="hidden" name="b_idx" value="${param.b_idx }"/>
		<%-- <input type="hidden" name="cPage" value="<%=cPage %>"/> --%>
		<input type="hidden" name="cPage" value="${param.cPage }"/>
		<input type="button" value="저장하기" onclick="saveComm()"/> 
	</form>
	
	댓글들<hr/>
	<%
	List<CommentVO> c_list = vo.getC_list();
	if(!c_list.isEmpty()){
		for(int i=0; i<c_list.size(); i++){
			CommentVO cvo = c_list.get(i);
	%>
		<div>
			이름: <%=cvo.getWriter() %> &nbsp;&nbsp;
			날짜: <%=cvo.getWrite_date().substring(0, 10) %><br/>
			내용: <%=cvo.getContent() %>
		</div>
		<hr/>
	<%
		}
	}else{ %>
		<div>댓글 없음</div>
	<%} %>
	</div>
	
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript">
		function del(cPage, b_idx) {
			var pwd = prompt("비밀번호를 입력하세요!");
			// console.log(pwd);
			// location.href = "Controller?type=del&cPage="+cPage+"&b_idx="+b_idx+"&pwd="+pwd;
			var param = "type=del&b_idx="+b_idx+"&pwd="+pwd;
			$.ajax({
				url: "Controller",
				type: "post",
				data: param,
				dataType: "json"
			}).done(function(res){
				// console.log(res.chk);
				if(res.chk == "true"){
					alert("삭제되었습니다!");
					location.href="Controller?type=list&cPage="+cPage;
				}else{
					alert("비밀번호가 다릅니다!");
				}
				
			}).fail(function(){
				console.log(err);
			});
		}
		
		function saveComm() {
			var writer = document.getElementById("writer");
			var content = document.getElementById("content");
			var pwd = document.getElementById("pwd");
			
			if(writer.value.trim().length < 1){
				alert("이름을 입력하세요!");
				writer.focus();
				return;
			}
			
			if(content.value.trim().length < 1){
				alert("내용을 입력하세요!");
				content.focus();
				return;
			}
			
			if(pwd.value.trim().length < 1){
				alert("비밀번호를 입력하세요!");
				pwd.focus();
				return;
			}
			document.frm.submit();
			
		}
	</script>
</body>
</html>