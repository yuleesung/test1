package bbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MidAction {
	
	String execute(HttpServletRequest request, HttpServletResponse response);

}
