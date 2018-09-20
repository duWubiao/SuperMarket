package cn.smbms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sun.util.logging.resources.logging;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private UserService userService;

	@RequestMapping(value="/login.html")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode
						,@RequestParam String userPassword
						,HttpServletRequest request
						,HttpSession session){
		logger.info("doLogin===============登录");
		User user = userService.login(userCode, userPassword);
		if(user != null){//登录成功
			session.setAttribute(Constants.USER_SESSION,user);
			return "redirect:/sys/main";
		}else{
			request.setAttribute("error", "用户名或密码错误!");
			return "login";
		}
	}
	@RequestMapping(value="logout")
	public String logout(HttpSession session){
		session.removeAttribute(Constants.USER_SESSION);
		return "login";
	}
	
	@RequestMapping(value="/sys/main")
	public String main(){
		return "frame";
	}
}
