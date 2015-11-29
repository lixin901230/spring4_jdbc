package com.lx.spring.springjdbc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lx.spring.springjdbc.bean.Departments;
import com.lx.spring.springjdbc.bean.Employee;

/**
 * <p><b>SpringJdbc 具名参数jdbc模板对象{@link NamedParameterJdbcTemplate}操作数据库测试；</b></p>
 * 
 * <p>特点：sql占位符以冒号加参数名称代替，且执行sql时参数传入常用的主要可分为以下两中：</p>
 * <p>1）参数使用SqlParameterSource的实现类BeanPropertySqlParameterSource传入传入，
 * 		但前提是 sql 语句中的参数占位符的名名称必须  与  创建BeanPropertySqlParameterSource(bean)对象时传入的实体bean的属性名要一致；见示例：testNamedParamTemBySqlParameterSourceParam(),</p>
 * <p>2）参数使用Map形式传入，map的key为sql参数占位符的参数名，value为对应的参数值；见下示例：testNamedParamJdbcTemOfUpdateByMapParam()</p>
 * 
 * @author lixin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	//"file:src/main/webapp/WEB-INF/config/spring/applicationContext.xml"	//配置文件放到WEB-INF下时需要指定路径使用绝对路径去加载配置文件
	"classpath*:applicationContext.xml"
})
public class NamedParameterJdbcTemplateTest {
	
	//非注解方式：通过ClassPathXmlApplicationContext加载配置文件
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	{
		namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) ctx.getBean("namedParameterJdbcTemplate");
	}
	
	/**
	 * <p><b>使用具名参数jdbc模板对象执行插入操作，且参数以SqlParameterSource的实现类BeanPropertySqlParameterSource作为统一参数</b></p>
	 * 
	 * <p>使用具名参数jdbc模板对象时，可以使用 update(String sql, SqlParameterSource paramSource) 方法进行更新操作</p>
	 * <p>1. 使用SqlParameterSource做统一参数时，sql 语句中的占位符参数名必须 与 实体类的属性名要一致，见下实例sql参数占位符</p>
	 * <p>2. 使用 SqlParameterSource 的 BeanPropertySqlParameterSource 实现类作为统一参数</p>
	 * 
	 * @author lixin
	 */
	@Test
	public void testNamedParamTemBySqlParameterSourceParam() {
		
		//1.获取bean参数对象
		Employee employee = new Employee();
		employee.setTrueName("lixin");
		employee.setEmail("1234@sina.cn");
		employee.setDeptId(2);
		
		//2.将参数model bean转换为SqlParameterSource对象
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);

		//3.将实体bean中参数统一转移到 SqlParameterSource 中作为参数载体，sql中的具名参数占位符的名称必须与实体bean中的属性名称一致
		String sql = "INSERT INTO employees(trueName, email, dept_id) VALUES(:trueName, :email, :deptId)";
		int update = namedParameterJdbcTemplate.update(sql, paramSource);
		System.out.println(update);
	}
	
	/**
	 * <b></p>使用具名参数jdbc模板对象插入记录，且参数以map形式传入，key为sql占位符对应的参数名称，val即为参数值</b></p>
	 * 
	 * <p>测试具名参数模板对象的update(String sql, Map<String, ?> paramMap)操作数据库，参数以map形式传入，key为sql占位符对应的参数名称，value即为参数值</li>
	 * <p>1、好处：可以为参数起名字，若有多个参数，则不用再去与?占位符一一对应位置，而是直接对应参数名，便于维护</p>
	 * <p>2、缺点：稍微麻烦一点，设置参数时需要指定参数名</p>
	 * 
	 * @author lixin
	 */
	@Test
	public void testNamedParamJdbcTemOfUpdateByMapParam() {
		String sql = "INSERT INTO departments(deptCode, deptName, description) VALUES(:code, :deptName, :desc)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", "NP01");
		paramMap.put("deptName", "NamedParameterJdbcTemplate_01");
		paramMap.put("desc", "（具名参数模板操作类NamedParameterJdbcTemplate）This is insert By org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate.update(String sql, Map<String, ?> paramMap) ");
		int update = namedParameterJdbcTemplate.update(sql, paramMap);
		System.out.println(update);
	}
	
	/**
	 * 通过具名参数jdbc模板对象执行条件查询；本例中的sql条件问好（?）占位符由具名参数占位符（:deptId）
	 * @author lixin
	 */
	@Test
	public void testNamedParamQuery() {
		
		String sql = "SELECT * FROM departments d WHERE d.id=:deptId";
		RowMapper<Departments> rowMapper = new BeanPropertyRowMapper<Departments>(Departments.class);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deptId", 1);
		Departments departments = namedParameterJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
		System.out.println(departments);
	}
}
