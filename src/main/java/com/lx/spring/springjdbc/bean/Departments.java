package com.lx.spring.springjdbc.bean;

public class Departments {

	private Integer id;
	private String deptCode;
	private String deptName;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "DepartmentsInfo [id=" + id + ", deptCode=" + deptCode
				+ ", deptName=" + deptName + ", description=" + description
				+ "]";
	}
	
}
