package cn.smbms.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User login(String userCode, String userPassword) {
		User user = null;
		try {
			user = userMapper.getUser(userCode);
			if(user != null){
				if(!user.getUserPassword().equals(userPassword)){
					user = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int getCount(String userName,int userRole) {
		return userMapper.getCount(userName,userRole);
	}

	@Override
	public List<User> getUserList(String userName, int userRole,
			int currentPageNo, int pageSize) {	
		return userMapper.getUserList(userName, userRole, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public int register(User user) {
		return userMapper.addUser(user);
	}

	@Override
	public User getUserById(String id) {
		return userMapper.getUserById(id);
	}

	@Override
	public int updateUserById(User user) {
		return userMapper.updateUserById(user);
	}

	@Override
	public int deleteUserById(String id) {
		return userMapper.deleteUserById(id);
	}

	@Override
	public User getUserByUserCode(String userCode) {
		return userMapper.getUserByUserCode(userCode);
	}

	@Override
	public int updatePwdById(String userPassword, int id) {
		return userMapper.updatePwdById(userPassword, id);
	}

}
