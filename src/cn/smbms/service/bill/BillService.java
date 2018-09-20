package cn.smbms.service.bill;

import java.util.List;

import cn.smbms.pojo.Bill;

public interface BillService {

	public int getCount(String productName,Integer providerId,Integer isPayment);
	
	public List<Bill> getBillList(String productName,Integer providerId,Integer isPayment,int currentPageNo,int pageSize);

	public int addBill(Bill bill); 
	
	public Bill getBillById(String id);
	
	public int modify(Bill bill);
	
	public int deleteBillById(String id);
}
