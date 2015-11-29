package com.lx.spring.springjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.lx.spring.springjdbc.bean.Employee;

/**
 * <pre>实际开发中使用JdbcTemplate操作数据库</pre>
 * 
 * 通过{@link JdbcTemplate}操作employees表
 * @author lixin
 */
@Repository("employeeDao")
public class EmployeeDaoImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 通过id获取employee对象
	 * @param empId		雇员ID
	 * @return Employee	
	 * @author lixin
	 */
	public Employee getEmployee(int empId) {
		
		String sql = "SELECT * FROM employees e WHERE e.id=?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		return employee;
	}
}