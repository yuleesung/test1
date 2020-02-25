package bbs.action;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

public class EditAction implements MidAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String c_type = request.getContentType();
		// System.out.println(c_type);
		String viewPath = "/edit.jsp";
		
		ServletContext application = request.getServletContext();
		
		String path = application.getRealPath("upload");
		
		String b_idx = request.getParameter("b_idx");
		String cPage = request.getParameter("cPage");
		// System.out.println(cPage);
		// System.out.println("cPage"+cPage);
		
		if(c_type != null && c_type.startsWith("multipart/")) {
			try {
				MultipartRequest mr = new MultipartRequest(request, path, 1024*1024*10, "utf-8", new DefaultFileRenamePolicy());
				String subject = mr.getParameter("subject");
				
				File f = mr.getFile("file");
				String file_name = null;
				String ori_name = null;
				if(f != null) {
					file_name = f.getName();
					ori_name = mr.getOriginalFileName("file");
				}
				
				String pwd = mr.getParameter("pwd");
				String content = mr.getParameter("content");
				String b_idx1 = mr.getParameter("b_idx");
			
				// System.out.println("Edit"+b_idx1);
				String ip = request.getRemoteAddr();
				
				boolean chk = BbsDAO.editPost(b_idx1, pwd, subject, file_name, ori_name, content, ip);
				BbsVO vo = BbsDAO.viewPost(b_idx1);
				
				String cPage1 = mr.getParameter("cPage");
				request.setAttribute("view", vo);
				// request.setAttribute("cPage", cPage1);
				// request.setAttribute("b_idx", b_idx1);
				viewPath = "/Controller?type=view&b_idx="+b_idx1+"&cPage="+cPage1;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else {
			BbsVO vo = BbsDAO.viewPost(b_idx);
			request.setAttribute("forView", vo);
			request.setAttribute("cPage", cPage);
		}
		
		return viewPath;
	}

}
