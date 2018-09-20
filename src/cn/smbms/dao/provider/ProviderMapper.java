package cn.smbms.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Provider;

public interface ProviderMapper {

	/**
	 * 查询所有供应商
	 * @param proCode
	 * @param proName
	 * @return
	 */
	public List<Provider> getProList(@Param("proCode") String proCode,@Param("proName") String proName);

	/**
	 * 查询所有供应商总记录
	 * @param proCode
	 * @param proName
	 * @return
	 */
	public int getCount(@Param("proCode") String proCode,@Param("proName") String proName);
	
	/**
	 * 添加供应商
	 * @param pro
	 * @return
	 */
	public int proAdd(Provider pro);
	
	/**
	 * 通过id查询供应商信息
	 * @param id
	 * @return
	 */
	public Provider getProById(@Param("id") String id);
	
	/**
	 * 通过id修改供应商
	 * @param pro
	 * @return
	 */
	public int modify(Provider pro);
	
	/**
	 * 通过id删除供应商
	 * @param id
	 * @return
	 */
	public int delPro(@Param("id") String id);
}
