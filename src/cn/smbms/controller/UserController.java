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
	
	//��ѯ��������
	@RequestMapping(value="userList")
	public String getUserList(Model model,@RequestParam(value="userName",required=false) String userName
							  ,@RequestParam(value="userRole",required=false) String userRole
							  ,@RequestParam(value="pageIndex",required=false) String pageIndex){
		logger.info("getUserList==============��ѯ�û��б�");
		//���ڴ��������û���ɫIDΪString�����Ҫǿת
		int _userRole = 0;
		//����ҳ������
		int pageSize = Constants.pageSize;
		//��ǰҳ��
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
				//���pageIndex����һ�����������ֱ��쳣
				return "redirect:sys/user/syserror";
			}
		}
		//�ܼ�¼��
		int totalCount = userService.getCount(userName, _userRole);
		System.out.println(totalCount+"=====================");
		//��ҳ��
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		
		//������ҳ��βҳ
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
		logger.info("useradd==============��������û�ҳ��");
		return "useradd";
	}
	//ע���û�
	@RequestMapping(value="saveUser",method=RequestMethod.POST)
	public String saveUser(User user,HttpSession session,HttpServletRequest request,@RequestParam(value="attachs",required=false) MultipartFile[] attachs){
		int id = ((User)session.getAttribute(Constants.USER_SESSION)).getId();
		String idPicPath = null;
		String workPicPath = null;
		String errorFile = null;
		boolean flags = true;
		//�����ϴ�Ŀ��·��
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
				//��ȡԭ�ļ���
				String oldFileName = attach.getOriginalFilename();
				//��ȡԭ�ļ���׺
				String prefix = FilenameUtils.getExtension(oldFileName);
				//����ԭ�ļ���С
				int fileSize = 500000;
				if(attach.getSize() > fileSize){
					request.setAttribute(errorFile, " * �ϴ���С���ó���500K");
					flags = false;
				}else if(prefix.equalsIgnoreCase("jpg")
						|| prefix.equalsIgnoreCase("jpeg")
						|| prefix.equalsIgnoreCase("png")
						|| prefix.equalsIgnoreCase("pneg")){
					//�µ��ļ�����ֹ�ظ�
					//��ǰϵͳʱ��+�����+"_Personal.jpg"
					String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+id+"_Personal.jpg";
					System.out.println(fileName+"===================");
					System.out.println(id+"=======================");
					//�����µ�File����
					File targetFile = new File(path,fileName);
					//�ж��Ƿ����
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
					try {
						//�ļ��ϴ�
						attach.transferTo(targetFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute(errorFile, " * �ϴ�ʧ��");
						flags = false;
					}
					if(i == 0){
						idPicPath = path+File.separator+fileName;
					}else if(i == 1){
						workPicPath = path+File.separator+fileName;
					}	
				}else{
					request.setAttribute(errorFile, " * �ϴ�ͼƬ��ʽ����ȷ");
					flags = false;
				}
			}
		}
		if(flags){
			logger.info("saveUser==============����û�");
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
	//ʹ��AJAX��ɵ������ݲ鿴
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
	//�����޸��û�ҳ��
	@RequestMapping(value="usermodify",method=RequestMethod.GET)
	public String userModify(@RequestParam String id,Model model){
		logger.info("userModify=============�û��޸�ҳ��");
		User user = userService.getUserById(id);
		model.addAttribute(user);
		return "usermodify";
	}
	//�޸��û���Ϣ
	@RequestMapping(value="usermodifysave",method=RequestMethod.POST)
	public String userModifySave(User user,HttpSession session){
		logger.info("userModifySave===========�޸��û�");
		user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setModifyDate(new Date());
		int k = userService.updateUserById(user);
		if(k > 0){
			return "redirect:/sys/user/userList";
		}else{
			return "usermodify";
		}
	}
	//ɾ��ָ���û�
	@RequestMapping(value="userdel")
	@ResponseBody
	public Object userDelete(@RequestParam String id){
		logger.info("userDelete================ɾ��ָ���û�");
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
	//�ж��û���Ψһ
	@RequestMapping(value="ucexist")
	@ResponseBody
	public Object viewJson(@RequestParam String userCode){
		HashMap<String, String> map = new HashMap<String, String>();
		//�ж��Ƿ�Ϊ��
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
		//��mapת��ΪJSON��ʽ����
		return JSON.toJSONString(map);
	}
	//���������޸�ҳ��
	@RequestMapping(value="pwdmodify",method=RequestMethod.GET)
	public String pwdModify(){
		logger.info("pwdModify==============���������޸�ҳ��");
		return "pwdmodify";
	}
	//�ж������Ƿ���ȷ�Ա����޸�����
	@RequestMapping(value="getpwd")//ʹ��ajaxʱ�������÷���(GET,POST)
	@ResponseBody
	public Object getPwd(@RequestParam("oldpassword") String oldpassword,HttpSession session){
		logger.info("getPwd=======================�ж������Ƿ���ȷ�Ա��޸�����");
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
	//�����޸��������
	@RequestMapping(value="modifypwd",method=RequestMethod.POST)
	public String modifyPwd(@RequestParam("newpassword") String newpassword
						   ,@RequestParam("rnewpassword") String rnewpassword
						   ,HttpSession session,HttpServletRequest request){
		logger.info("modifyPwd===================�����޸��������");
		if(!newpassword.equals(rnewpassword)){
			request.setAttribute(Constants.SYS_MESSAGE, "������������벻һ��");
			return "redirect:/sys/user/pwdmodify";
		}else if(StringUtils.isNullOrEmpty(rnewpassword) || StringUtils.isNullOrEmpty(newpassword)){
			request.setAttribute(Constants.SYS_MESSAGE, "�޸�����ʧ��");
			return "redirect:/sys/user/pwdmodify";
		}else{
			User user = (User) session.getAttribute(Constants.USER_SESSION);
			int k = userService.updatePwdById(newpassword, user.getId());
			if(k > 0){
				request.setAttribute(Constants.USER_SESSION, "�޸�����ɹ�,���˳���ʹ�����������µ�¼!");
				request.getSession().removeAttribute(Constants.USER_SESSION);
			}else{
				request.setAttribute(Constants.SYS_MESSAGE, "�޸�����ʧ��");
			}
			return "pwdmodify";
		}
	}
}
