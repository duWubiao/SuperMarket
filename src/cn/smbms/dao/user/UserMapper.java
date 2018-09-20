package cn.smbms.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.User;

public interface UserMapper {

	/**
	 * 通过userCode获取User（登录）
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User getUser(@Param("userCode") String userCode) throws Exception;
	
	/**
	 * 获取总记录数
	 * @return
	 */
	public int getCount(@Param("userName") String userName,@Param("userRole") Integer userRole);
	
	/**
	 * 获取所有记录
	 * @return
	 */
	public List<User> getUserList(@Param("userName") String userName,@Param("userRole") Integer userRole,@Param("pageIndex") Integer currentPageNo,@Param("pageSize") Integer pageSize);

	/**
	 * 注册
	 */
	public int addUser(User user);
	
	/**
	 *查看单人信息通过id
	 */
	public User getUserById(@Param("id") String id);
	
	/**
	 * 修改信息通过id
	 * @param id
	 * @return
	 */
	public int updateUserById(User user);
	
	/**
	 * 删除指定用户
	 * @param id
	 * @return
	 */
	public int deleteUserById(@Param("id") String id);
	
	/**
	 * 通过用户编码查询用户以保证用户名唯一性
	 * @param userCode
	 * @return
	 */
	public User getUserByUserCode(@Param("userCode") String userCode);
	
	/**
	 * 通过密码和id查询用户以便修改密码
	 * @param userPassword
	 * @return
	 */
	public int updatePwdById(@Param("userPassword") String userPassword ,@Param("id") int id);
}
