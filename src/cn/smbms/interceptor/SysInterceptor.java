package cn.smbms.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.smbms.pojo.User;
import cn.smbms.tools.Constants;

public class SysInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler){
		System.out.println("preHandle====================À¹½ØÆ÷");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		if(user == null){
			try {
				response.sendRedirect(request.getContextPath()+"/401.jsp");
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return true;
	}
}
