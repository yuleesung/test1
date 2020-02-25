package bbs.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bbs.util.Paging;
import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

public class WriteOKAction implements MidAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		ServletContext application = request.getServletContext();
		
		String path = application.getRealPath("/upload");
		
		MultipartRequest mr = null;
		try {
			mr = new MultipartRequest(request, path, 1024*1024*10, "utf-8", new DefaultFileRenamePolicy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		boolean chk = BbsDAO.addPost(vo);
		
		Paging page = new Paging();
		
		page.setTotalRecord(BbsDAO.getCount());
		String cPage = request.getParameter("cPage");
		// System.out.println(cPage);
		if(cPage != null) {
			int p = Integer.parseInt(cPage);
			page.setNowPage(p);
		}else { 
			page.setNowPage(page.getNowPage());
		}
		
		BbsVO[] ar = BbsDAO.getList(page.getBegin(), page.getEnd());
	
		request.setAttribute("ar", ar);
		request.setAttribute("page", page);
		
		request.setAttribute("write_ok", chk);
		
		// return "/write.jsp";
		return null;
	}

}
