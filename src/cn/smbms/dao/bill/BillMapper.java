package cn.smbms.dao.bill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Bill;

public interface BillMapper {

	/**
	 * ��ȡ�ܼ�¼��
	 * @param productName
	 * @param providerId
	 * @param isPayment
	 * @return
	 */
	public int getCount(@Param("productName") String productName,@Param("providerId") Integer providerId,@Param("isPayment") Integer isPayment);

	/**
	 * ��ȡ���м�¼
	 */
	public List<Bill> getBillList(@Param("productName") String productName,@Param("providerId") Integer providerId,@Param("isPayment") Integer isPayment,@Param("pageIndex") Integer currentPageNo,@Param("pageSize") Integer pageSize);

	/**
	 * ��Ӷ���
	 */
	public int addBill(Bill bill);
	
	/**
	 * ͨ��id��ѯ��������
	 * @param id
	 * @return
	 */
	public Bill getBillById(@Param("id") String id);
	
	/**
	 * ͨ��id�޸Ķ�����Ϣ
	 * @param id
	 * @return
	 */
	public int modify(Bill bill);
	
	/**
	 * ͨ��idɾ��������Ϣ
	 * @param id
	 * @return
	 */
	public int deleteBillById(@Param("id") String id);
}
