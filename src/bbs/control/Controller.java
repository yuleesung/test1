package bbs.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.action.MidAction;

/**
 * Servlet implementation class Controller
 */
@WebServlet(
		urlPatterns = { "/Controller" }, 
		initParams = { 
				@WebInitParam(name = "myParam", value = "/WEB-INF/action.properties")
		})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, MidAction> map;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        map = new HashMap<String, MidAction>();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		String props_path = getInitParameter("myParam");
		ServletContext application = getServletContext();
		String path = application.getRealPath(props_path);
		
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			props.load(fis);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null)
					fis.close();
			} catch (Exception e2) {}
		}
		
		Iterator<Object> it = props.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = props.getProperty(key);
			try {
				Object obj = Class.forName(value).newInstance();
				map.put(key, (MidAction) obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String type = request.getParameter("type");
		if(type == null)
			type = "list";
		
		//System.out.println(type);
		
		MidAction action = map.get(type);
		
		String viewPath = action.execute(request, response);
		
		// System.out.println(viewPath);
		
		if(viewPath != null) {
			if(viewPath.equals("comment")) {
				response.sendRedirect("Controller?type=view&cPage="+request.getAttribute("cPage")+"&b_idx="+request.getAttribute("b_idx"));
			}else if(viewPath.trim().length() == 0){
				
			}else {
				RequestDispatcher disp = request.getRequestDispatcher(viewPath);
				disp.forward(request, response);
			}
			
		}else{
			response.sendRedirect("Controller");
			// response.sendRedirect();를 하게 되면 Action으로부터 받은 request들은 여기까지 즉, Controller까지는 유지된다. 하지만, 이후 경로로 전달되어질 때는 request는
			// 빈 깡통이 된다.
		}
	}

}
