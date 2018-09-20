package cn.smbms.tools;

//饿汉模式时具有延迟加载的特性
public class Singleton {

	private static Singleton singleton;
	
	private Singleton(){
		//放置只执行一次的业务代码操作
	}
	//静态内部类
	public static class SingletonHrlper{
		private static final Singleton INSTANCE = new Singleton();
	}
	
	//只有在掉用这个方法的时候才实现singleton实例，实现懒加载
	public static Singleton getInstance(){
		singleton = SingletonHrlper.INSTANCE;
		return singleton;
	}
	public static Singleton test(){
		return singleton;
	}
}
