package cn.smbms.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	//查询所有数据
	@RequestMapping(value="userList")
	public String getUserList(Model model,@RequestParam(value="userName",required=false) String userName
							  ,@RequestParam(value="userRole",required=false) String userRole
							  ,@RequestParam(value="pageIndex",required=false) String pageIndex){
		logger.info("getUserList==============查询用户列表");
		//由于传过来的用户角色ID为String因此需要强转
		int _userRole = 0;
		//设置页面容量
		int pageSize = Constants.pageSize;
		//当前页码
		int currentPageNo = 1;
		if(userName == null){
			userName = "";
		}
		if(userRole != null && !userRole.equals("")){
			_userRole = Integer.parseInt(userRole);
		}
		if(pageIndex != null){
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (Exception e) {
				//如果pageIndex不是一个正常的数字报异常
				return "redirect:sys/user/syserror";
			}
		}
		//总记录数
		int totalCount = userService.getCount(userName, _userRole);
		System.out.println(totalCount+"=====================");
		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		
		//控制首页和尾页
    	if(currentPageNo < 1){
    		currentPageNo = 1;
    	}else if(currentPageNo > totalPageCount){
    		currentPageNo = totalPageCount;
    	}
    	List<User> userList = userService.getUserList(userName,_userRole,currentPageNo, pageSize);
    	for (User user : userList) {
			System.out.println(user.getUserName());
		}
    	model.addAttribute("userList", userList);
    	List<Role> roleList = roleService.getRoleList();
    	model.addAttribute("roleList", roleList);
    	model.addAttribute("queryUserName", userName);
    	model.addAttribute("queryUserRole", userRole);
    	model.addAttribute("totalPageCount", totalPageCount);
    	model.addAttribute("totalCount", totalCount);
    	model.addAttribute("currentPageNo", currentPageNo);
    	return "userlist";
	}
	@RequestMapping(value="useradd",method=RequestMethod.GET)
	public String userAdd(){
		logger.info("useradd==============进入添加用户页面");
		return "useradd";
	}
	//注册用户
	@RequestMapping(value="saveUser",method=RequestMethod.POST)
	public String saveUser(User user,HttpSession session,HttpServletRequest request,@RequestParam(value="attachs",required=false) MultipartFile[] attachs){
		int id = ((User)session.getAttribute(Constants.USER_SESSION)).getId();
		String idPicPath = null;
		String workPicPath = null;
		String errorFile = null;
		boolean flags = true;
		//定义上传目标路径
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
	//	System.out.println(user.getId()+"147258369============");
		for(int i=0;i<attachs.length;i++){
			MultipartFile attach = attachs[i];
			if(!attach.isEmpty()){
				if(i == 0){
					errorFile = "uploadFileError";
				}else if(i == 1){
					errorFile = "uploadWpError";
				}
				//获取原文件名
				String oldFileName = attach.getOriginalFilename();
				//获取原文件后缀
				String prefix = FilenameUtils.getExtension(oldFileName);
				//定义原文件大小
				int fileSize = 500000;
				if(attach.getSize() > fileSize){
					request.setAttribute(errorFile, " * 上传大小不得超过500K");
					flags = false;
				}else if(prefix.equalsIgnoreCase("jpg")
						|| prefix.equalsIgnoreCase("jpeg")
						|| prefix.equalsIgnoreCase("png")
						|| prefix.equalsIgnoreCase("pneg")){
					//新的文件名防止重复
					//当前系统时间+随机数+"_Personal.jpg"
					String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+id+"_Personal.jpg";
					System.out.println(fileName+"===================");
					System.out.println(id+"=======================");
					//定义新的File对象
					File targetFile = new File(path,fileName);
					//判断是否存在
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
					try {
						//文件上传
						attach.transferTo(targetFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute(errorFile, " * 上传失败");
						flags = false;
					}
					if(i == 0){
						idPicPath = path+File.separator+fileName;
					}else if(i == 1){
						workPicPath = path+File.separator+fileName;
					}	
				}else{
					request.setAttribute(errorFile, " * 上传图片格式不正确");
					flags = false;
				}
			}
		}
		if(flags){
			logger.info("saveUser==============添加用户");
			user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
			user.setCreationDate(new Date());
			user.setIdPicPath(idPicPath);
			user.setWorkPicPath(workPicPath);
			int k = userService.register(user);
			if(k >0){
				return "redirect:/sys/user/userList";
			}
		}
			return "useradd";
	}
	//使用AJAX完成单条数据查看
	@RequestMapping(value="view",method=RequestMethod.GET)
	@ResponseBody
	public User view(@RequestParam String id){
		logger.info("viewJson=================");
		User user = new User();
		try {
			user = userService.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	//进入修改用户页面
	@RequestMapping(value="usermodify",method=RequestMethod.GET)
	public String userModify(@RequestParam String id,Model model){
		logger.info("userModify=============用户修改页面");
		User user = userService.getUserById(id);
		model.addAttribute(user);
		return "usermodify";
	}
	//修改用户信息
	@RequestMapping(value="usermodifysave",method=RequestMethod.POST)
	public String userModifySave(User user,HttpSession session){
		logger.info("userModifySave===========修改用户");
		user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setModifyDate(new Date());
		int k = userService.updateUserById(user);
		if(k > 0){
			return "redirect:/sys/user/userList";
		}else{
			return "usermodify";
		}
	}
	//删除指定用户
	@RequestMapping(value="userdel")
	@ResponseBody
	public Object userDelete(@RequestParam String id){
		logger.info("userDelete================删除指定用户");
		HashMap<String, String> map = new HashMap<String, String>();
		if(id == null || id.equals("")){
			map.put("delResult", "notexist");
		}else{
			int k = userService.deleteUserById(id);
			if(k > 0){
				map.put("delResult", "true");
			}else{
				map.put("delResult", "false");
			}
	}
		return JSON.toJSONString(map);
	}
	//判断用户名唯一
	@RequestMapping(value="ucexist")
	@ResponseBody
	public Object viewJson(@RequestParam String userCode){
		HashMap<String, String> map = new HashMap<String, String>();
		//判断是否为空
		if(StringUtils.isNullOrEmpty(userCode)){
			map.put("userCode", "exist");
		}else{
			User user = userService.getUserByUserCode(userCode);
			if(user != null){
				map.put("userCode", "exist");
			}else{
				map.put("userCode", "noexist");
			}
		}
		//把map转换为JSON格式返回
		return JSON.toJSONString(map);
	}
	//进入密码修改页面
	@RequestMapping(value="pwdmodify",method=RequestMethod.GET)
	public String pwdModify(){
		logger.info("pwdModify==============进入密码修改页面");
		return "pwdmodify";
	}
	//判断密码是否正确以便修修改密码
	@RequestMapping(value="getpwd")//使用ajax时不能设置方法(GET,POST)
	@ResponseBody
	public Object getPwd(@RequestParam("oldpassword") String oldpassword,HttpSession session){
		logger.info("getPwd=======================判断密码是否正确以便修改密码");
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		Map<String, Object> map = new HashMap<String, Object>();
		if(user == null){
			map.put("result", "sessionerror");
		}else if(StringUtils.isNullOrEmpty(oldpassword)){
			map.put("result", "error");
		}else{
			String password = user.getUserPassword();
			if(oldpassword.equals(password)){
				map.put("result", "true");
			}else{
				map.put("result", "false");
			}
		}
		return JSON.toJSONString(map);
	}
	//进行修改密码操作
	@RequestMapping(value="modifypwd",method=RequestMethod.POST)
	public String modifyPwd(@RequestParam("newpassword") String newpassword
						   ,@RequestParam("rnewpassword") String rnewpassword
						   ,HttpSession session,HttpServletRequest request){
		logger.info("modifyPwd===================进行修改密码操作");
		if(!newpassword.equals(rnewpassword)){
			request.setAttribute(Constants.SYS_MESSAGE, "两次输入的密码不一致");
			return "redirect:/sys/user/pwdmodify";
		}else if(StringUtils.isNullOrEmpty(rnewpassword) || StringUtils.isNullOrEmpty(newpassword)){
			request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败");
			return "redirect:/sys/user/pwdmodify";
		}else{
			User user = (User) session.getAttribute(Constants.USER_SESSION);
			int k = userService.updatePwdById(newpassword, user.getId());
			if(k > 0){
				request.setAttribute(Constants.USER_SESSION, "修改密码成功,请退出并使用新密码重新登录!");
				request.getSession().removeAttribute(Constants.USER_SESSION);
			}else{
				request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败");
			}
			return "pwdmodify";
		}
	}
}
