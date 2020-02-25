package bbs.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class SaveImageAction implements MidAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//String type = request.getParameter("type");
		//System.out.println(type);
		ServletContext application = request.getServletContext(); // request만 있으면 웬만한 서블릿 기능은 사용할 수 있다.
		String path = application.getRealPath("/editor_img");
		MultipartRequest mr = null;
		try {
			mr = new MultipartRequest(request, path, 1024*1024*10, "utf-8", new DefaultFileRenamePolicy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f = mr.getFile("upload");
		String f_name = null;
		if(f != null) {
			f_name = f.getName();
		}
		
		request.setAttribute("saveImage", f_name);
		
		return "/saveImage.jsp";
	}

}
