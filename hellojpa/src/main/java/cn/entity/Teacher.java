package cn.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class Teacher implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long teacherId;
	@Column
	private String teacherName;
	@Column
	private String teacherAge;
	
	@ManyToMany
	@JoinTable(name="stu_tea",
		joinColumns= {
				@JoinColumn(name="sys_teaId",referencedColumnName="teacherId")},
		inverseJoinColumns= {
				@JoinColumn(name="sys_stuId",referencedColumnName="studentId")})
	private Set<Student> stuSet=new HashSet<Student>(0);
	
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherAge() {
		return teacherAge;
	}
	public void setTeacherAge(String teacherAge) {
		this.teacherAge = teacherAge;
	}
	public Set<Student> getStuSet() {
		return stuSet;
	}
	public void setStuSet(Set<Student> stuSet) {
		this.stuSet = stuSet;
	}
	@Override
	public String toString() {
		return "Teacher [teacherId=" + teacherId + ", teacherName=" + teacherName + ", teacherAge=" + teacherAge + "]";
	}
	
	
	

}
