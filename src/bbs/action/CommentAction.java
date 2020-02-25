package bbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.dao.BbsDAO;
import mybatis.vo.CommentVO;

public class CommentAction implements MidAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String cPage = request.getParameter("cPage");
		String b_idx = request.getParameter("b_idx");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		String ip = request.getRemoteAddr();
		// System.out.println(b_idx+"/"+writer+"/"+content+"/"+pwd);
		
		boolean chk = BbsDAO.addComm(b_idx, writer, content, pwd, ip);
		
		request.setAttribute("b_idx", b_idx);
		request.setAttribute("cPage", cPage);
		
		// return "Controller?type=view&cPage="+cPage+"&b_idx="+b_idx;
		return "comment";
	}

}
