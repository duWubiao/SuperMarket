package cn.smbms.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.User;

public interface UserMapper {

	/**
	 * ͨ��userCode��ȡUser����¼��
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User getUser(@Param("userCode") String userCode) throws Exception;
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @return
	 */
	public int getCount(@Param("userName") String userName,@Param("userRole") Integer userRole);
	
	/**
	 * ��ȡ���м�¼
	 * @return
	 */
	public List<User> getUserList(@Param("userName") String userName,@Param("userRole") Integer userRole,@Param("pageIndex") Integer currentPageNo,@Param("pageSize") Integer pageSize);

	/**
	 * ע��
	 */
	public int addUser(User user);
	
	/**
	 *�鿴������Ϣͨ��id
	 */
	public User getUserById(@Param("id") String id);
	
	/**
	 * �޸���Ϣͨ��id
	 * @param id
	 * @return
	 */
	public int updateUserById(User user);
	
	/**
	 * ɾ��ָ���û�
	 * @param id
	 * @return
	 */
	public int deleteUserById(@Param("id") String id);
	
	/**
	 * ͨ���û������ѯ�û��Ա�֤�û���Ψһ��
	 * @param userCode
	 * @return
	 */
	public User getUserByUserCode(@Param("userCode") String userCode);
	
	/**
	 * ͨ�������id��ѯ�û��Ա��޸�����
	 * @param userPassword
	 * @return
	 */
	public int updatePwdById(@Param("userPassword") String userPassword ,@Param("id") int id);
}
