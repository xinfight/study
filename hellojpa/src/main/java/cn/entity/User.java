package cn.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity //该类是实体化类
@Table	//该类对应的数据库中的表名
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //主键生成策略
	@Column(name="userId")
	private Long userID;
	@Column
	private String username;
	@Column
	private String sex;
	@Column
	private int age;
	
	@OneToMany(targetEntity=Role.class,mappedBy="user",cascade=CascadeType.ALL)
	private Set<Role> roles=new HashSet(0);
	
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", sex=" + sex + ", age=" + age + ", roles="
				+ roles + "]";
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
