package bbs.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

public class ViewAction implements MidAction {
	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String cPage = request.getParameter("cPage");
		String b_idx = request.getParameter("b_idx");
		// System.out.println(b_idx1);
		
		
		BbsVO vo = BbsDAO.viewPost(b_idx);
		
		HttpSession session = request.getSession();
		
		Object obj = session.getAttribute("read_bbs");

		if(vo != null){
			boolean chk = false;
			List<BbsVO> a_list = null;
			
			if(obj == null){
				a_list = new ArrayList<BbsVO>();
				session.setAttribute("read_bbs", a_list);
			}else{
				a_list = (List<BbsVO>) obj;
				
				// vo의 b_idx와 list에 있는 각 BbsVO의 b_idx를 비교
				for(BbsVO r_vo : a_list){
					if(b_idx.equals(r_vo.getB_idx())){
						chk = true;
						break;
					}
				}
			}
			
			if(!chk){
				// 일단 현 게시물의 조회수 값을 가져온다.
				int hit = Integer.parseInt(vo.getHit());
				++hit;
				
				vo.setHit(String.valueOf(hit));
				
				// 여기까지는 vo가 가지고 있는 hit값을 변경했지만 DB에는 변경되지 않았다.
				BbsDAO.hit(b_idx);
				
				// 읽은 게시물로 처리하기 위해 list에 vo를 추가
				a_list.add(vo);
				
				
			}
			request.setAttribute("view", vo); // jsp에서 바로 EL로 사용가능하다. 하지만 파라미터를 쓰려면 ${param.b_idx} 방식으로 해야 한다.
		}
		
		
		request.setAttribute("cPage", cPage);
		
		return "/view.jsp";
		
	}

}
