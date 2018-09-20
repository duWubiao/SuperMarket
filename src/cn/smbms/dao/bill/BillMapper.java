package cn.smbms.dao.bill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Bill;

public interface BillMapper {

	/**
	 * 获取总记录数
	 * @param productName
	 * @param providerId
	 * @param isPayment
	 * @return
	 */
	public int getCount(@Param("productName") String productName,@Param("providerId") Integer providerId,@Param("isPayment") Integer isPayment);

	/**
	 * 获取所有记录
	 */
	public List<Bill> getBillList(@Param("productName") String productName,@Param("providerId") Integer providerId,@Param("isPayment") Integer isPayment,@Param("pageIndex") Integer currentPageNo,@Param("pageSize") Integer pageSize);

	/**
	 * 添加订单
	 */
	public int addBill(Bill bill);
	
	/**
	 * 通过id查询单个订单
	 * @param id
	 * @return
	 */
	public Bill getBillById(@Param("id") String id);
	
	/**
	 * 通过id修改订单信息
	 * @param id
	 * @return
	 */
	public int modify(Bill bill);
	
	/**
	 * 通过id删除订单信息
	 * @param id
	 * @return
	 */
	public int deleteBillById(@Param("id") String id);
}
