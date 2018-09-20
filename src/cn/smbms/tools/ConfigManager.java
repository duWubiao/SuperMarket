package cn.smbms.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ��ȡ�����ļ��Ĺ�����-����ģʽ
 * @author Du
 * 
 */
public class ConfigManager {
	private static Properties properties;
	//�����ʱ�����н��г�ʼ��
	private static ConfigManager configManager = new ConfigManager();

	// ˽�й�����-��ȡ���ݿ������ļ�
	private ConfigManager() {
		properties = new Properties();
		String configFile = "database.properties";
		InputStream is = ConfigManager.class.getClassLoader()
				.getResourceAsStream(configFile);
		try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ȫ�ַ��ʵ�
//	public static ConfigManager getInstance(){
//		if(configManager == null){
//			configManager = new ConfigManager();
//		}
//		return configManager;
//	}
	public static ConfigManager getInstance(){
		return configManager;
	}
	
	public String getValue(String key){
		return properties.getProperty(key);
	}
}
