package cn.smbms.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件的工具类-单例模式
 * @author Du
 * 
 */
public class ConfigManager {
	private static Properties properties;
	//类加载时，自行进行初始化
	private static ConfigManager configManager = new ConfigManager();

	// 私有构造器-读取数据库配置文件
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
	//全局访问点
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
