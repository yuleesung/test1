package bbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.dao.BbsDAO;

public class DelAction implements MidAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// String cPage = request.getParameter("cPage");
		// 기본키와 비밀번호를 파라미터로 받는다.
		String b_idx = request.getParameter("b_idx");
		String pwd = request.getParameter("pwd");
		
		boolean chk = BbsDAO.delPost(b_idx, pwd);
		
		request.setAttribute("chk", chk);
		// return "Controller?type=list&cPage="+cPage;
		return "/del.jsp";
	}

}
