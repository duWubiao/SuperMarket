package cn.smbms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;

@Controller
@RequestMapping(value="/sys/pro")
public class ProviderController extends BaseController{

	private Logger logger = Logger.getLogger(ProviderController.class);
	
	@Resource
	private ProviderService proService;
	
	@RequestMapping("proList")
	public String getProList(Model model,@RequestParam(value="queryProCode",required=false) String proCode
										,@RequestParam(value="queryProName",required=false) String proName){
		logger.info("getProList===================查询所有供应商");
		int count = proService.getCount(proCode, proName);
		List<Provider> proList = proService.getProList(proCode, proName);
		model.addAttribute("queryProCode", proCode);
		model.addAttribute("queryProName", proName);
		model.addAttribute("providerList", proList);
		return "providerlist";
	}
	//进入添加供应商页面
	@RequestMapping(value="proadd",method=RequestMethod.GET)
	public String proAdd(){
		logger.info("proAdd================添加供应商页面");
		return "provideradd";
	}
	//进行添加供应商操作
	@RequestMapping(value="prosave",method=RequestMethod.POST)
	public String proSave(Provider provider,HttpSession session){
		logger.info("proSave===============添加供应商");
		provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		provider.setCreationDate(new Date());
		int k = proService.proAdd(provider);
		if(k > 0){
			return "redirect:/sys/pro/proList";
		}else{
			return "provideradd";
		}
	}
	//查看供应商信息
	@RequestMapping(value="proview/{id}",method=RequestMethod.GET)
	public String proView(@PathVariable String id,Model model){
		logger.info("proView==================查看供应商信息");
		Provider pro = proService.getProById(id);
		model.addAttribute("provider", pro);
		return "providerview";
	}
	//进入供应商修改页面
	@RequestMapping(value="promodify/{id}")
	public String proModify(@PathVariable String id,Model model){
		logger.info("proModify==================供应商修改页面");
		Provider pro = proService.getProById(id);
		System.out.println(id);
		model.addAttribute("provider", pro);
		return "providermodify";
	}
	//进行修改供应商操作
	@RequestMapping(value="modifypro",method=RequestMethod.POST)
	public String modifyPro(Provider pro,HttpSession session){
		logger.info("modifyPro==================进行修改供应商操作");
		pro.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		pro.setModifyDate(new Date());
		logger.info(pro.getId()+"==================");
		int k = proService.modify(pro);
		if(k > 0){
			return "redirect:/sys/pro/proList";
		}else{
			return "providermodify";
		}
	}
	//删除供应商
	@RequestMapping(value="delpro",method=RequestMethod.GET)
	@ResponseBody
	public Object delPro(@RequestParam("id") String id){
		logger.info("delPro================删除供应商");
		Map<String, Object> map = new HashMap<String, Object>();
		if(id == null || id.equals("")){
			map.put("delResult", "notexist");
		}else{
			int k = proService.delPro(id);
			if(k > 0){
				map.put("delResult", "true");
			}else{
				map.put("delResult", "true");
			}
		}
		return JSON.toJSONString(map);
	}
}
