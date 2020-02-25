package bbs.action;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bbs.util.Paging;
import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

public class WriteAction implements MidAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		/*
		Paging page = new Paging();
		page.setTotalRecord(BbsDAO.getCount());
		
		page.setNowPage(page.getNowPage());
		
		BbsVO[] ar = BbsDAO.getList(page.getBegin(), page.getEnd());
		// System.out.println(ar);
		
		request.setAttribute("write_ok", ar);
		// request.setAttribute("nowpage", page.getNowPage());
		 
		*/
		// 선생님이 하시는 방법(form으로 오거나 a href로 올 때 구분하는 방법
		// 현재 메서드는 list.jsp에 있는 [글쓰기]버튼을 클릭했을 때와 write.jsp에서 [보내기]버튼을 클릭했을 때 호출된다.
		String c_type = request.getContentType(); // 요청한 곳으로부터 MIME타입을 얻는다. Get방식은 MIME타입이 없다.
		// - Get방식 : Null
		// - Post방식 : application/... <- 이건 기본
		// - Post방식에 encType="multipart/form-data" : multipart/...
		// System.out.println(c_type); // 을 찍어보면 나온다.
		String viewPath = "/write.jsp";
		
		if(c_type != null && c_type.startsWith("multipart/")) {
			try {
				// 첨부파일을 저장할 위치를 절대경로화 시킨다.
				ServletContext application = request.getServletContext();
				
				String path = application.getRealPath("upload");
				
				MultipartRequest mr = new MultipartRequest(request, path, 1024*1024*10, "utf-8", new DefaultFileRenamePolicy());
				// 이때 첨부파일 업로드 됨.
				File f = mr.getFile("file");
				String file_name = "";
				String ori_name = "";
				if(f != null) {
					file_name = mr.getFile("file").getName();
					ori_name = mr.getOriginalFileName("file");
				}
				
				BbsVO vo = new BbsVO();
				vo.setSubject(mr.getParameter("subject"));
				vo.setWriter(mr.getParameter("writer"));
				vo.setContent(mr.getParameter("content"));
				vo.setFile_name(file_name);
				vo.setOri_name(ori_name);
				vo.setPwd(mr.getParameter("pwd"));
				vo.setIp(request.getRemoteAddr());
				vo.setHit("0");
				vo.setStatus("0");
				
				// DB저장
				boolean chk = BbsDAO.addPost(vo);
				
				request.setAttribute("write_ok", chk);
				// System.out.println(request.getAttribute("write_ok"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// JSP경로를 반환하면 forward되도록 되어 있어서 F5 즉, 화면 갱신을 하면 저장된 정보가 계속 저장된다.
			// viewPath = "Controller?type=list";
			viewPath = null;
		}
		
		
		
		//return "/write_ok.jsp";
		boolean chk = false;
		String type = request.getParameter("type");
		if(type.equals("write")) {
			chk = true;
		}
		request.setAttribute("write", chk);
		
		// return "/write.jsp";
		return viewPath;
	}

}
