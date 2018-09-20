package cn.smbms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;

@Controller
@RequestMapping(value="/sys/bill")
public class BillController extends BaseController{

	private Logger logger = Logger.getLogger(BillController.class);
	
	@Resource
	private BillService billService;
	
	@Resource
	private ProviderService providerService;
	
	@RequestMapping("billList")
	public String getBillList(Model model,@RequestParam(value="queryProductName",required=false) String productName
							   			 ,@RequestParam(value="queryProviderId",required=false) String providerId
							   			 ,@RequestParam(value="queryIsPayment",required=false) String isPayment
							   			 ,@RequestParam(value="pageIndex",required=false) String pageIndex){
		logger.info("getUserList==============��ѯ�����б�");
				//���ڴ������Ĺ�Ӧ�̽�ɫIDΪString�����Ҫǿת
				int _providerId = 0;
				//�Ƿ񸶿�,���ڴ�����string�����Ҫǿת
				int _isPayment = 0;
				//����ҳ������
				int pageSize = 20;
				//��ǰҳ��
				int currentPageNo = 1;
				if(productName == null){
					productName = "";
				}
				if(providerId != null && !providerId.equals("")){
					_providerId = Integer.parseInt(providerId);
				}
				if(isPayment != null && !isPayment.equals("")){
					_isPayment = Integer.parseInt(isPayment);
				}
				if(pageIndex != null){
					try {
						currentPageNo = Integer.valueOf(pageIndex);
					} catch (Exception e) {
						//���pageIndex����һ�����������ֱ��쳣
						return "redirect:sys/user/syserror";
					}
				}
				//�ܼ�¼��
				int totalCount = billService.getCount(productName, _providerId,_isPayment);
				System.out.println(totalCount+"=====================");
				//��ҳ��
				PageSupport pages = new PageSupport();
				pages.setCurrentPageNo(currentPageNo);
				pages.setPageSize(pageSize);
				pages.setTotalCount(totalCount);
				int totalPageCount = pages.getTotalPageCount();
				
				//������ҳ��βҳ
		    	if(currentPageNo < 1){
		    		currentPageNo = 1;
		    	}else if(currentPageNo > totalPageCount){
		    		currentPageNo = totalPageCount;
		    	}
		    	List<Bill> billList = billService.getBillList(productName,_providerId,_isPayment,currentPageNo, pageSize);
		    	for (Bill bill : billList) {
					System.out.println(bill.getProductName());
				}
		    	model.addAttribute("billList", billList);
		    	List<Provider> proList = providerService.getProList("","");
		    	model.addAttribute("queryIsPayment", _isPayment);
		    	model.addAttribute("providerList", proList);
		    	model.addAttribute("queryProductName", productName);
		    	model.addAttribute("queryProviderId", providerId);
		    	model.addAttribute("totalPageCount", totalPageCount);
		    	model.addAttribute("totalCount", totalCount);
		    	model.addAttribute("currentPageNo", currentPageNo);
		    	return "billlist";
	}
	@RequestMapping(value="billadd",method=RequestMethod.GET)
	public String billAdd(){
		logger.info("������Ӷ���ҳ��==============");
		return "billadd";
	}
	@RequestMapping(value="addBill",method=RequestMethod.POST)
	public String addBill(Bill bill,HttpSession session,HttpServletRequest request){
		logger.info("������Ӷ�������===============");
		bill.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		bill.setCreationDate(new Date());
		int k = billService.addBill(bill);
		if(k > 0){
			return "redirect:/sys/bill/billList";
		}else{
			return "billadd";
		}
	}
	//��ѯ���й�Ӧ��
	@RequestMapping(value="getproviderlist",method=RequestMethod.GET)
	@ResponseBody
	public List<Provider> getProviderList(){
		logger.info("��ѯ���й�Ӧ��==============");
		List<Provider> proList = null;
		try {
			proList = providerService.getProList("","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proList;
	}
	//�����ѯ����������Ϣ��REST���
	@RequestMapping(value="billview/{id}")
	public String billView(@PathVariable String id,Model model){
		logger.info("�����ѯ��������ҳ��==============");
		Bill bill = billService.getBillById(id);
		model.addAttribute("bills",bill);
		System.out.println(bill.getProductName()+"======================");
		return "billview";
	}
	//�����޸Ķ�����Ϣҳ��
	@RequestMapping(value="billmodify/{id}")
	public String billModify(@PathVariable String id,Model model){
		logger.info("���붩���޸�ҳ��==============");
		Bill bill = billService.getBillById(id);
		model.addAttribute("bills",bill);
		System.out.println(bill.getProductName()+"======================");
		return "billmodify";
	}
	//�޸Ķ�����Ϣ
	@RequestMapping(value="modifyBill",method=RequestMethod.POST)
	public String modifyBill(Bill bill,HttpSession session){
		logger.info("�����޸Ķ�������==============");	
		bill.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		bill.setModifyDate(new Date());
		int k = billService.modify(bill);
		if(k > 0){
			return "redirect:/sys/bill/billList";
		}else{
			return "billmodify";
		}
	}
	@RequestMapping(value="delBill",method=RequestMethod.GET)
	@ResponseBody
	public Object delBill(@RequestParam String id){
		logger.info("delBill=================ɾ��ָ������");
		HashMap<String, String> map = new HashMap<String, String>();
		if(id == null || id.equals("")){
			map.put("delResult", "notexist");
		}
		int k = billService.deleteBillById(id);
		if(k > 0){
			map.put("delResult", "true");
		}else{
			map.put("delResult", "false");
		}
		return JSON.toJSONString(map);
	}
}
