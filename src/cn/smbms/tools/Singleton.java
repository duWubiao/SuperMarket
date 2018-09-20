package cn.smbms.tools;

//����ģʽʱ�����ӳټ��ص�����
public class Singleton {

	private static Singleton singleton;
	
	private Singleton(){
		//����ִֻ��һ�ε�ҵ��������
	}
	//��̬�ڲ���
	public static class SingletonHrlper{
		private static final Singleton INSTANCE = new Singleton();
	}
	
	//ֻ���ڵ������������ʱ���ʵ��singletonʵ����ʵ��������
	public static Singleton getInstance(){
		singleton = SingletonHrlper.INSTANCE;
		return singleton;
	}
	public static Singleton test(){
		return singleton;
	}
}
