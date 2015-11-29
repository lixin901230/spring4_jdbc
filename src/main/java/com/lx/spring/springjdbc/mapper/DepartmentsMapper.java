package com.lx.spring.springjdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.lx.spring.springjdbc.bean.Departments;

/**
 * Departments 映射器
 * 
 * @author lx
 *
 */
public class DepartmentsMapper implements RowMapper<List<Departments>> {

	@Override
	public List<Departments> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		if(rs == null) {
			return null;
		}

		List<Departments> depts = new ArrayList<Departments>();
		while(rs.next()) {
			Departments departments = new Departments();
			departments.setId(rs.getInt("id"));
			departments.setDeptCode(rs.getString("deptCode"));
			departments.setDeptName(rs.getString("deptName"));
			departments.setDescription(rs.getString("description"));
			depts.add(departments);
		}
		return depts;
	}
	
}
