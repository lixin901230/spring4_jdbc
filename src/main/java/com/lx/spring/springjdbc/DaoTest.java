package com.lx.spring.springjdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lx.spring.springjdbc.bean.Departments;
import com.lx.spring.springjdbc.bean.Employee;
import com.lx.spring.springjdbc.dao.DepartmentsDaoImpl;
import com.lx.spring.springjdbc.dao.EmployeeDaoImpl;
import com.lx.spring.util.ApplicationContextHelper;

/**
 * <ul><b>Spring jdbc 操作数据库，两种方式，如下:</b>
 * 	<li>1、通过在自定义的Dao中注入 {@link JdbcTemplate}类来操作数据库</li>
 * 	<li>2、（不推荐使用）通过自定义的Dao继承 {@link JdbcDaoSupport}类来操作数据库</li>
 * 	<li>3、通过具名参数模板对象{@link NamedParameterJdbcTemplate}操作数据库</li>
 * </ul>
 * 
 * 	<pre>在该类中我们使用Spring jdbc 提供的{@link JdbcDaoSupport} 进行增删改查操作</pre>
 * @author lx
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	//"file:src/main/webapp/WEB-INF/config/spring/applicationContext.xml"	//配置文件放到WEB-INF下时需要指定路径使用绝对路径去加载配置文件
	"classpath*:applicationContext.xml"
})
public class DaoTest {
	
	//非注解方式：通过ClassPathXmlApplicationContext加载配置文件
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	
	private DepartmentsDaoImpl departmentsDao;
	private EmployeeDaoImpl employeeDao;
	
	{
		employeeDao = (EmployeeDaoImpl) ctx.getBean("employeeDao");
		departmentsDao = (DepartmentsDaoImpl) ctx.getBean("departmentsDao");
		
		//注意：通过接口ApplicationContextAware的实现类ApplicationContextHelper在方法块中无法获取到bean实例
		//departmentsDao = (DepartmentsDaoImpl)ApplicationContextHelper.getBean("departmentsDao");
	}
	
	/**
	 * <pre>方式一测试：通过在自定义的Dao（EmployeeDaoImpl）中注入 {@link JdbcTemplate}类来操作数据库</pre>
	 * 
	 * 测试EmployeeDaoImpl中的方法：getEmployee(int id)
	 * @throws InterruptedException
	 * @author lixin
	 */
	@Test
	public void testGetEmployee() {
		
		//注意：通过接口ApplicationContextAware的实现类ApplicationContextHelper只能在使用的地方才能获取到需要的bean实例，在方法块中取不到
//		employeeDao = (EmployeeDaoImpl)ApplicationContextHelper.getBean("employeeDao");
		
		Employee employee = employeeDao.getEmployee(1);
		System.out.println(employee);
	}
	
	/**
	 * <pre>方式二测试：通过自定义的Dao（DepartmentsDaoImpl）继承 {@link JdbcDaoSupport}类来操作数据库</pre>
	 * 
	 * 测试DepartmentsDaoImpl中的方法：getDepartments(int id)
	 * @throws InterruptedException
	 * @author lixin
	 */
	@Test
	public void testGetDepartments() throws InterruptedException {
		
		//注意：通过接口ApplicationContextAware的实现类ApplicationContextHelper只能在使用的地方才能获取到需要的bean实例，在方法块中取不到
		departmentsDao = (DepartmentsDaoImpl)ApplicationContextHelper.getBean("departmentsDao");
		
		Departments departments = departmentsDao.getDepartments(1);
		System.out.println(departments);
	}
}
