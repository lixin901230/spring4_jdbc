package com.lx.spring.springjdbc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.lx.spring.springjdbc.bean.Departments;

/**
 * <ul>Spring jdbc 操作数据库，支持两种方式，如下:
 * 	<li>1、通过 {@link JdbcTemplate}类操作数据库</li>
 * 	<li>2、通过 {@link JdbcDaoSupport}类操作数据库</li>
 * </ul>
 * 
 * 	<pre>在该类中我们使用Spring jdbc 提供的{@link JdbcDaoSupport} 进行增删改查操作</pre>
 * @author lx
 */
@Repository("departmentsDao")
public class DepartmentsDaoImpl extends JdbcDaoSupport {
	
	/**
	 * <b>必须步骤</b><br/>
	 * 
	 * 曲线救国：使用JdbcDaoSupport操作数据库，需要提供DataSource给JdbcDaoSupport，
	 * 并通过{@link JdbcDaoSupport}提供你的final方法setDataSource(DataSource dataSource)设置进去
	 * 
	 * @param dataSource	数据源对象
	 */
	@Autowired
	public void setDataSourceToJdbcDaoSupport(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	/**
	 * 通过id获取Departments对象
	 * @return
	 */
	public Departments getDepartments(int id) {
		String sql = "SELECT * FROM departments d WHERE d.id=?";
		RowMapper<Departments> rowMapper = new BeanPropertyRowMapper<Departments>(Departments.class);
		Departments departments = getJdbcTemplate().queryForObject(sql, rowMapper, id);
		return departments;
	}
}
