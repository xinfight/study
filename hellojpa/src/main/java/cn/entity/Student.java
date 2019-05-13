package cn.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class Student implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long studentId;
	@Column
	private String studentName;
	@Column
	private String studentAge;
	
	@ManyToMany(targetEntity=Teacher.class,mappedBy="stuSet",fetch=FetchType.LAZY)
	private Set<Teacher> teaSet=new HashSet<Teacher>(0);

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(String studentAge) {
		this.studentAge = studentAge;
	}

	
	public Set<Teacher> getTeaSet() {
		return teaSet;
	}

	public void setTeaSet(Set<Teacher> teaSet) {
		this.teaSet = teaSet;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", studentAge=" + studentAge
				+ ", teaSet=" + teaSet + "]";
	}

	

}
