package com.lx.spring.springjdbc.bean;

public class Employee {

	private Integer id;
	private String trueName;
	private String email;
	private Integer deptId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	
	@Override
	public String toString() {
		return "Emplyees [id=" + id + ", trueName=" + trueName + ", email="
				+ email + ", deptId=" + deptId + "]";
	}
	
}
