package com.lx.spring.springjdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lx.spring.springjdbc.bean.Departments;
import com.lx.spring.springjdbc.mapper.DepartmentsMapper;

/**
 * <ul><b>Spring jdbc 操作数据库，支持两种方式，如下:</b>
 * 	<li>1、通过在自定义的Dao中注入 {@link JdbcTemplate}类来操作数据库</li>
 * 	<li>2、（不推荐使用）通过自定义的Dao继承 {@link JdbcDaoSupport}类来操作数据库</li>
 * 	<li>3、通过具名参数模板对象{@link NamedParameterJdbcTemplate}操作数据库</li>
 * </ul>
 * 
 * 	<pre>在该类中我们使用Spring jdbc 提供的{@link JdbcTemplate} 进行增删改查操作</pre>
 * @author lixin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	//"file:src/main/webapp/WEB-INF/config/spring/applicationContext.xml"	//配置文件放到WEB-INF下时需要指定路径使用绝对路径去加载配置文件
	"classpath*:applicationContext.xml"
})
public class JdbcTemplateTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 测试下数据源是否配置正确
	 * @throws SQLException
	 */
	@Test
	public void testDatasource() throws SQLException {
		
		System.out.println(dataSource);
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		
		System.out.println(jdbcTemplate);
	}
	
	/**
	 * 增加 
	 * {@link JdbcTemplate}.execute(String sql)
	 */
	@Test
	public void testInsert() {
		
		String sql = "INSERT INTO departments(deptCode, deptName, description) values('dept02', '部门02', '测试部门02')";
		jdbcTemplate.execute(sql);
	}
	
	@Test
	public void testQueryForType() {
		String sql = "SELECT * FROM departments d";
		ResultSetExtractor<List<List<Departments>>> resultSetExtractor 
			= new RowMapperResultSetExtractor<List<Departments>>(new DepartmentsMapper(), 2);
		List<List<Departments>> list = jdbcTemplate.query(sql, resultSetExtractor);
		System.out.println(list.get(0).size()+"\n"+list.toString());
	}
	
	/**
	 * 获取单个列的值
	 */
	@Test
	public void testQueryForObjectToSimpleType() {
		String sql = "SELECT d.deptName FROM departments d WHERE d.id=?";
		String res = jdbcTemplate.queryForObject(sql, String.class, 1);
		System.out.println(res);
	}
	
	/**
	 * （常用）查询一条记录，并返回一个对应的bean对象
	 * 
	 * 通用映射结果集到bean对象
	 * 
	 * 1. 其中的RowMapper 指定如何去映射结果集中的行，常用的实现类为BeanPropertyRowMapper。
	 * 2. 使用 SQL 中的列的别名完成列名与bean的属性名的映射。
	 */
	@Test
	public void testQueryForObject() {
		
		String sql = "SELECT * FROM departments d WHERE d.id=?";
		RowMapper<Departments> rowMapper = new BeanPropertyRowMapper<Departments>(Departments.class);
		Departments dept = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		System.out.println(dept);
	}
	
	/**
	 * 集合对象查询
	 * 
	 * 查询多行记录返回实体集合
	 * 注意：不是用QueryForList()方法
	 */
	@Test
	public void testQueryForList() {
		String sql = "SELECT * FROM departments d WHERE d.id > ?";
		RowMapper<Departments> rowMapper = new BeanPropertyRowMapper<Departments>(Departments.class);
		List<Departments> list = jdbcTemplate.query(sql, rowMapper, 5);
		System.out.println(list.toString());
	}
	
	/**
	 * 查询单行结果，并映射结果集到map中
	 * 
	 * 注意：queryForMap()方法：
	 * 			文档中说明，该方法被用来做单行记录查询的，并将查询结果集映射到map中，用列明作为map的key；
	 * 			文档：The query is expected to be a single row query; the result row will be mapped to a Map (one entry for each column, using the column name as the key).
	 * @author lixin
	 */
	@Test
	public void testQueryForMap() {
		String sql = "SELECT * FROM departments d where d.id=?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, 1);
		System.out.println(map);
	}
	
	/**
	 * 查询一条或多条记录，返回RowSet对象，自行处理查询结果
	 * 
	 * queryForRowSet()
	 * @author lixin
	 */
	@Test
	public void testQueryForRowSet() {
		String sql = "SELECT * FROM departments d where d.id=?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, 1);
		while(rowSet.next()) {
			
			int id = rowSet.getInt("id");
			String deptName = rowSet.getString("deptName");
			String description = rowSet.getString("description");
			
			System.out.println(id+"===="+deptName+"====="+description);
		}
	}
	
	/**
	 * 执行单条记录更新
	 */
	@Test
	public void testUpdate() {
		String sql = "UPDATE departments SET description='测试部门01修改' WHERE id='1'";
		int updateRow = jdbcTemplate.update(sql);
		System.out.println(updateRow);
	}
	
	
	//##########################	jdbcTemplate  批处理	###################################
	
	
	/**
	 * 执行批量更新：批量的 INSERT，UPDATE，DELETE
	 * 最后一个参数是 Object[] 的 List 类型（即：List<Object[]>），因为更新一条记录需要一个Object[]作为参数，那么批量更新多条，那么就需要多个Object的数组，即Object数组的集合
	 */
	@Test
	public void testBatchUpdate() {
		String sql = "INSERT INTO departments(deptCode, deptName, description) Values(?,?,?)";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		batchArgs.add(new Object[]{"batch01", "批量测试部门01", "批量测试部门01"});
		batchArgs.add(new Object[]{"batch02", "批量测试部门02", "批量测试部门02"});
		batchArgs.add(new Object[]{"batch03", "批量测试部门03", "批量测试部门03"});
		batchArgs.add(new Object[]{"batch04", "批量测试部门04", "批量测试部门04"});
		int[] batchUpdate = jdbcTemplate.batchUpdate(sql, batchArgs);
		System.out.println(batchUpdate.toString());
	}
	
	
	//##########################	jdbcTemplate  调用执行存储过程	###################################
	
	
	/**
	 * 执行存储过程(有输出参数)
	 * 返回单个统计值
	 */
	@Test
	public void testHasOutParamsProcedure() {
		
		/* 本示例的存储过程
		CREATE DEFINER=`root`@`localhost` PROCEDURE `count_emp_procedure`(IN in_deptId varchar(50),OUT out_empNum bigint)
		BEGIN
			select count(*) from employees e where e.dept_id=in_deptId into out_empNum;
		END
		*/
		
		//创建调用存储过程的预定义SQL语句 
		String sql = "{call count_emp_procedure(?, ?)}";
		Long totalRows = jdbcTemplate.execute(sql, new CallableStatementCallback<Long>() {
			
			@Override
			public Long doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				
//				cs.setInt("in_deptId", 14);	//通过存储过程参数名称  设置入参
//				cs.registerOutParameter("out_deptNum", Types.BIGINT);	//通过存储过程出参名称 注册出参
				cs.setInt(1, 14);	//通过参数下标设置入参
				cs.registerOutParameter(2, Types.BIGINT);
				int result = cs.executeUpdate();
				System.out.println(result);
				
				//获取输出参数值（两种方式都行）
				long count = cs.getLong(2);
				//long count = cs.getLong("out_deptNum");
				return count;
			}
		});
		System.out.println("totalRows="+totalRows);
	}
	
	/**
	 * 执行存储过程(无输出参数)
	 * 返回列表
	 */
	@Test
	public void testNoOutParamsProcedure() {
		
		/* 本示例的存储过程
		CREATE DEFINER=`root`@`localhost` PROCEDURE `find_all_dept_proc`(IN in_id int)
		BEGIN
			SELECT d.id, d.deptCode, d.deptName, d.description FROM departments d WHERE d.id<in_id;
		END
		*/
		
		//创建调用存储过程的预定义SQL语句 
        String sql = "{call find_all_dept_proc(?)}";
		List<Departments> deptList = jdbcTemplate.execute(sql, new CallableStatementCallback<List<Departments>>() {

			@Override
			public List<Departments> doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {

				List<Departments> depts = new ArrayList<Departments>();
				cs.setInt(1, 5);
				ResultSet resultSet = cs.executeQuery();
				while(resultSet.next()) {
					
					Departments dept = new Departments();
					dept.setId(resultSet.getInt("id"));
					dept.setDeptCode(resultSet.getString("deptCode"));
					dept.setDeptName(resultSet.getString("deptName"));
					dept.setDescription(resultSet.getString("description"));
					depts.add(dept);
				}
				return depts;
			}
		});
		System.out.println("deptList="+deptList);
	}
	
}
