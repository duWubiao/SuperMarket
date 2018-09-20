package cn.smbms.service.provider;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Provider;

@Service
public class ProviderServiceImpl implements ProviderService{

	@Resource
	private ProviderMapper providerMapper;
	
	@Override
	public List<Provider> getProList(String proCode,String proName) {
		return providerMapper.getProList(proCode, proName);
	}

	@Override
	public int getCount(String proCode, String proName) {
		return providerMapper.getCount(proCode, proName);
	}

	@Override
	public int proAdd(Provider pro) {
		return providerMapper.proAdd(pro);
	}

	@Override
	public Provider getProById(String id) {
		return providerMapper.getProById(id);
	}

	@Override
	public int modify(Provider pro) {
		return providerMapper.modify(pro);
	}

	@Override
	public int delPro(String id) {
		return providerMapper.delPro(id);
	}

}
