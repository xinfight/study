package cn.test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import cn.entity.Role;
import cn.entity.Student;
import cn.entity.Teacher;
import cn.entity.User;
import cn.utils.JPAUtil;

public class TestManyTable {
	@Test
	public void test1() {
		//获取管理器
		EntityManager em = JPAUtil.getEntityManager();
		//获取操作事务对象
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		User user = new User();
		user.setUsername("张三");
		user.setAge(18);
		user.setSex("男");
		Role role = new Role();
		role.setRoleName("老师");
		//告知用户有哪些角色
		user.getRoles().add(role);
		//告知角色属于哪个用户
		role.setUser(user);
		
		//先保存主表的内容，在保存从表的内容
		em.persist(user);
		em.persist(role);
		tx.commit();
		em.close();
		
	}
	
	
	@Test
	public void test2() {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User user = em.find(User.class, 1L);
		em.remove(user);
		tx.commit();
		em.close();
	}
	
	@Test
	public void test3() {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Student stu1 = new Student();
		stu1.setStudentName("stu1");
		
		Student stu2 = new Student();
		stu2.setStudentName("stu2");
		
		Student stu3 = new Student();
		stu3.setStudentName("stu3");
		
		Teacher tea1 = new Teacher();
		tea1.setTeacherName("tea1");
		
		Teacher tea2 = new Teacher();
		tea2.setTeacherName("tea2");
		
		stu1.getTeaSet().add(tea1);
		stu2.getTeaSet().add(tea1);
		stu2.getTeaSet().add(tea2);
		stu3.getTeaSet().add(tea2);
		
		tea1.getStuSet().add(stu1);
		tea1.getStuSet().add(stu2);
		tea2.getStuSet().add(stu2);	
		tea2.getStuSet().add(stu3);	
		
		
		em.persist(stu1);
		em.persist(stu2);
		em.persist(stu3);
		
		em.persist(tea1);
		em.persist(tea2);
		
		
		tx.commit();
		em.close();
	}
	
	@Test
	public void test4() {
		//获取管理器对象
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		Student stu = em.find(Student.class, 2L);
		Set<Teacher> teaSet = stu.getTeaSet();
		for(Object t:teaSet) {
			System.out.println(t);
		}
		tx.commit();
		em.close();
	}
	
	@Test  //根据find方法进行id进行查询
	public void test5() {
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		Student stu = em.find(Student.class,1L);
		System.out.println(stu);
		
		Set<Teacher> teaSet = stu.getTeaSet();
		for(Teacher t:teaSet) {
			System.out.println(t);
		}
		
		tx.commit();
		em.close();
	}
	
	@Test   //getReference方法进行查询
	public void test6() {
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		Student stu = em.getReference(Student.class, 1L);
		
		System.out.println(stu.getStudentAge());
		System.out.println(stu.getTeaSet());
		tx.commit();
		em.close();
	}
	
	@Test		//JPQL查询
	public void test7() {
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		
//		Query query = em.createQuery("from Student where studentId=?");
//		query.setParameter(1, 1L);
		
		Query query = em.createQuery("from Student where studentId=:id");
		query.setParameter("id", 1L);
		
		query.setFirstResult(0);
		query.setMaxResults(2);
		
		List<Student> resultList = query.getResultList();
		for(Student stu:resultList) {
			System.out.println(stu);
		}
		tx.commit();
		em.close();
	}
	
	@Test		//sql查询
	public void test8() {
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
//		
//		Query query = em.createNativeQuery("select * from student");
//		List<Object[]> resultList = query.getResultList();
//		for(Object[] o:resultList) {
//			System.out.println(Arrays.toString(o));
//		}
		
		Query query = em.createNativeQuery("select * from student",Student.class);
		
		List<Student> list = query.getResultList();
		for(Student stu:list) {
			System.out.println(stu);
		}
		
		tx.commit();
		em.close();
	}
	
	@Test  //QBC查询
	public void test9() {
		EntityManager em = JPAUtil.getEntityManager();
		//获取事务
		EntityTransaction tx = em.getTransaction();
		//开启事务
		tx.begin();
		//创建CriteriaBuilder对象，该对象中封装着查询对象的方法
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//创建查询对象，用于生成sql语句
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		//获取实体类对象的封装对象，有此对象之后，所有实体类都可以看成此类型
		Root<Student> root = cq.from(Student.class);
		Predicate p1 = cb.equal(root.get("studentId").as(Long.class), 1L);
		Predicate p2 = cb.like(root.get("studentName").as(String.class), "%stu%");
		
		Predicate p3 = cb.and(p1,p2);
		//进行条件的绑定
		cq.where(p3);
		List<Student> list = em.createQuery(cq).getResultList();
		
		for(Student stu:list) {
			System.out.println(stu);
		}
		tx.commit();
		em.close();
		
	
	}
	
	

}
