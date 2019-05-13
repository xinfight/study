package cn.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory factory;
	static {
		factory = Persistence.createEntityManagerFactory("myJPAUnit");
	}
	//使用管理器对象生成一个管理器
	public static EntityManager getEntityManager() {
		return  factory.createEntityManager();
	}

}
