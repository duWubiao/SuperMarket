package cn.smbms.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Provider;

public interface ProviderMapper {

	/**
	 * ��ѯ���й�Ӧ��
	 * @param proCode
	 * @param proName
	 * @return
	 */
	public List<Provider> getProList(@Param("proCode") String proCode,@Param("proName") String proName);

	/**
	 * ��ѯ���й�Ӧ���ܼ�¼
	 * @param proCode
	 * @param proName
	 * @return
	 */
	public int getCount(@Param("proCode") String proCode,@Param("proName") String proName);
	
	/**
	 * ��ӹ�Ӧ��
	 * @param pro
	 * @return
	 */
	public int proAdd(Provider pro);
	
	/**
	 * ͨ��id��ѯ��Ӧ����Ϣ
	 * @param id
	 * @return
	 */
	public Provider getProById(@Param("id") String id);
	
	/**
	 * ͨ��id�޸Ĺ�Ӧ��
	 * @param pro
	 * @return
	 */
	public int modify(Provider pro);
	
	/**
	 * ͨ��idɾ����Ӧ��
	 * @param id
	 * @return
	 */
	public int delPro(@Param("id") String id);
}
