package bbs.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadAction implements MidAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String b_idx = request.getParameter("b_idx");
		String cPage = request.getParameter("cPage");
		String fname = request.getParameter("file");
		
		ServletContext application = request.getServletContext();
		String path = application.getRealPath("/upload/"+fname);

		File f = new File(path);

		if(f.exists()){
			byte[] buf = new byte[2048];
			int len = -1;
			
			BufferedInputStream bis = null;
			FileInputStream fis = null;
			
			BufferedOutputStream bos = null;
			ServletOutputStream sos = null;
			
			try{
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment;filename="+new String(fname.getBytes(), "8859_1"));
				
				fis = new FileInputStream(f);
				bis = new BufferedInputStream(fis);
				
				sos = response.getOutputStream();
				bos = new BufferedOutputStream(sos);
				
				while((len = bis.read(buf)) != -1){
					bos.write(buf, 0, len);
					bos.flush();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(bis != null)
						bis.close();
					if(fis != null)
						fis.close();
					if(bos != null)
						bos.close();
					if(sos != null)
						sos.close();
				}catch(Exception e){}
			}
		}
		
		return "";
	}

}
