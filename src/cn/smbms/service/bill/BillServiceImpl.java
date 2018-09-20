package cn.smbms.service.bill;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.pojo.Bill;

@Service
public class BillServiceImpl implements BillService{

	@Resource
	private BillMapper billMapper;
	
	@Override
	public int getCount(String productName, Integer providerId,
			Integer isPayment) {
		return billMapper.getCount(productName, providerId, isPayment);
	}

	@Override
	public List<Bill> getBillList(String productName, Integer providerId,
			Integer isPayment, int currentPageNo, int pageSize) {
		return billMapper.getBillList(productName, providerId, isPayment, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public int addBill(Bill bill) {
		return billMapper.addBill(bill);
	}

	@Override
	public Bill getBillById(String id) {
		return billMapper.getBillById(id);
	}

	@Override
	public int modify(Bill bill) {
		return billMapper.modify(bill);
	}

	@Override
	public int deleteBillById(String id) {
		return billMapper.deleteBillById(id);
	}

}
