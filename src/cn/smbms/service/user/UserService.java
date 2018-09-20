package cn.smbms.service.user;

import java.util.List;

import cn.smbms.pojo.User;

public interface UserService {

	public User login(String userCode,String userPassword);
	
	public int getCount(String userName,int userRole);
	
	public List<User> getUserList(String userName,int userRole,int currentPageNo,int pageSize);
	
	public int register(User user);
	
	public User getUserById(String id);
	
	public int updateUserById(User user);
	
	public int deleteUserById(String id);
	
	public User getUserByUserCode(String userCode);
	
	public int updatePwdById(String userPassword,int id);
}
