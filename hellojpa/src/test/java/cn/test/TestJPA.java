package cn.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;

import cn.entity.Customer;
import cn.utils.JPAUtil;

public class TestJPA {
	@Test
	public void test1() {
		//JPA的实体类管理工厂
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJPAUnit");
		//实体类的管理
		EntityManager entityManager = factory.createEntityManager();
		//获取事务处理的对象
		EntityTransaction tx = entityManager.getTransaction();
		//开启事务
		tx.begin();
		Customer cus = new Customer();
		cus.setCustName("阿拉斯加");
		//保存操作
		entityManager.persist(cus);
		//提交事务
		tx.commit();
		//释放资源
		entityManager.close();
		factory.close();
	}
	
	//保存操作
	@Test
	public void saveTest() {
		//获取管理器对象
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务对象
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		Customer customer = new Customer();
		customer.setCustName("大宝");
		customer.setCustAddress("北京昌平");
		em.persist(customer);
		//提交事务
		tx.commit();
		//释放资源
		em.close();
	}
	//查询操作
	@Test
	public void findTest() {
		//获取管理器对象
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务管理对象
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		//find方法是立即加载，返回的对象使实现对象
		Customer cus = em.find(Customer.class,5L);
		System.out.println(cus);
		//提交事务
		tx.commit();
		//释放资源
		em.close();
	}
	
	@Test
	public void updateTest() {
		//获取管理器对象
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务管理
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		//进行数据的修改需要先进行查询，在进行修改
		Customer cus = em.find(Customer.class, 4L);
		System.out.println(cus);
		cus.setCustLevel("6VIP");
		cus.setCustName("红星");
		//对数据库中数据进行修改
		em.merge(cus);
		tx.commit();
		//释放资源
		em.close();
	}
	//删除操作   先要进行查询操作，才能进行删除操作
	@Test
	public void deleTest() {
		//获取管理器
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务对象
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Customer cus = em.find(Customer.class, 3L);
		em.remove(cus);
		//提交事务
		tx.commit();
		em.close();
		
	}
	//根据id进行查询的getReference方法
	@Test	
	public void getReferenceTest() {
		//获取管理器对象
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务对象
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		Customer cus = em.getReference(Customer.class, 5L);
		System.out.println(cus.getCustLevel());
		tx.commit();
		em.close();
	}
	
	//根据条件进行查询
	@Test
	public void testFindAll() {
		//获取管理器
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		//注意：from后面跟的是类名，不是表名
		Query query = em.createQuery("from Customer");
		List<Customer> list = query.getResultList();
		for(Customer c:list) {
			System.out.println(c);
		}
	}
	
	//根据条件进行查询
	@Test
	public void testFindByManyCondition() {
		//获取管理器
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		//获取条件查询的对象  from后面是表名，where后面跟的是属性名
		Query query = em.createQuery("from Customer where custId=? and custName like ?");
		query.setParameter(1,1L);
		query.setParameter(2, "%斯加%");
		
		List<Customer> list = query.getResultList();
		for(Customer c:list) {
			System.out.println(c);
		}
		tx.commit();
		em.close();
	}
	
	//排序查询
	@Test
	public void testOrderBy() {
		//获取管理器
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		Query query = em.createQuery("from Customer order by custId");
		List<Customer> list = query.getResultList();
		for(Customer c:list) {
			System.out.println(c);
		}
		tx.commit();
		em.close();
	}
	//分页查询
	@Test
	public void testByPage() {
		//获取管理器
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		//获取查询对象
		Query query = em.createQuery("from Customer");
		//设置分页查询的开始的记录
		query.setFirstResult(2);
		//设置的是每页查询的条数
		query.setMaxResults(2);
		List<Customer> list = query.getResultList();
		
		for(Customer c:list) {
			System.out.println(c);
		}
		//提交事务
		tx.commit();
		//释放资源
		em.close();
	}
}
