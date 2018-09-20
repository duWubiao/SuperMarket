package cn.smbms.service.provider;

import java.util.List;

import cn.smbms.pojo.Provider;

public interface ProviderService {

	public List<Provider> getProList(String proCode,String proName);
	
	public int getCount(String proCode,String proName);
	
	public int proAdd(Provider pro);
	
	public Provider getProById(String id);
	
	public int modify(Provider pro);
	
	public int delPro(String id);
}
