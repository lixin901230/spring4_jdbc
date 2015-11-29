/*
SQLyog Community v11.11 (64 bit)
MySQL - 5.1.73-community : Database - spring4_jdbc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spring4_jdbc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spring4_jdbc`;

/*Table structure for table `departments` */

DROP TABLE IF EXISTS `departments`;

CREATE TABLE `departments` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
  `deptCode` varchar(50) DEFAULT NULL COMMENT '部门编码',
  `deptName` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='部门信息表';

/*Data for the table `departments` */

insert  into `departments`(`id`,`deptCode`,`deptName`,`description`) values (1,'dept01','部门01','测试部门01修改'),(2,'dept02','部门02','测试部门02'),(3,'dept03','财务部','财务部'),(4,'dept04','行政部','行政部'),(5,'dept05','市场部','市场部'),(6,'dept06','人事部','人事部'),(7,'dept07','项目开发部','项目开发部'),(8,'dept08','产品研发部','产品研发部'),(9,'dept09','产品组','产品组'),(10,'dept10','运维部','运维部'),(11,'dept11','运营部','运营部'),(12,'dept12','销售部','销售部'),(13,'dept13','测试部','测试部'),(14,'dept14','总裁部','总裁部'),(15,'batch01','批量测试部门01','批量测试部门01'),(16,'batch02','批量测试部门02','批量测试部门02'),(17,'batch03','批量测试部门03','批量测试部门03'),(18,'batch04','批量测试部门04','批量测试部门04'),(19,'batch01','批量测试部门01','批量测试部门01'),(20,'batch02','批量测试部门02','批量测试部门02'),(21,'batch03','批量测试部门03','批量测试部门03'),(22,'batch04','批量测试部门04','批量测试部门04'),(23,'dept02','部门02','测试部门02'),(24,'batch01','批量测试部门01','批量测试部门01'),(25,'batch02','批量测试部门02','批量测试部门02'),(26,'batch03','批量测试部门03','批量测试部门03'),(27,'batch04','批量测试部门04','批量测试部门04'),(28,'dept02','部门02','测试部门02'),(29,'NP01','NamedParameterJdbcTemplate_01','（具名参数模板操作类NamedParameterJdbcTemplate）This is insert By org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate.update(String sql, Map<String, ?> paramMap) '),(30,'procedure01','test_procedure01','test_procedure01'),(31,'proc02','proc02','proc02');

/*Table structure for table `employees` */

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trueName` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) DEFAULT NULL COMMENT 'email',
  `dept_id` int(50) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='员工信息表';

/*Data for the table `employees` */

insert  into `employees`(`id`,`trueName`,`email`,`dept_id`) values (1,'邓总','1234@sina.com',14),(2,'张总','2345@sina.com',14),(3,'吴总','2343@sian.com',14),(4,'卢总','3432@sina.com',14),(5,'kevinli','kevinli@sina.com',8),(6,'lixin','1234@sina.cn',2);

/* Procedure structure for procedure `count_emp_procedure` */

/*!50003 DROP PROCEDURE IF EXISTS  `count_emp_procedure` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `count_emp_procedure`(IN in_deptId varchar(50),OUT out_empNum bigint)
BEGIN
	select count(*) from employees e where e.dept_id=in_deptId into out_empNum;
END */$$
DELIMITER ;

/* Procedure structure for procedure `find_all_dept_proc` */

/*!50003 DROP PROCEDURE IF EXISTS  `find_all_dept_proc` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `find_all_dept_proc`(IN in_id int)
BEGIN
	#Routine body goes here...
	SELECT d.id, d.deptCode, d.deptName, d.description FROM departments d WHERE d.id<in_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `find_dept_list_cursor` */

/*!50003 DROP PROCEDURE IF EXISTS  `find_dept_list_cursor` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `find_dept_list_cursor`()
BEGIN
	#Routine body goes here...
	DECLARE done INT DEFAULT 0;
	DECLARE _deptId VARCHAR(50) DEFAULT '';
	DECLARE _deptCode VARCHAR(50) DEFAULT '';
	DECLARE _deptName VARCHAR(50) DEFAULT '';
	DECLARE _desc VARCHAR(500) DEFAULT '';

	DECLARE query_dept_cursor CURSOR FOR
		SELECT d.id, d.deptCode, d.deptName, d.description FROM departments d;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;

	OPEN query_dept_cursor;
	dept_loop: LOOP
		FETCH query_dept_cursor INTO _deptId, _deptCode, _deptName, _desc;
		IF done=1 THEN
			LEAVE dept_loop;
		END IF;
	END LOOP dept_loop;
	CLOSE query_dept_cursor;
END */$$
DELIMITER ;

/* Procedure structure for procedure `insert_count_row_proc` */

/*!50003 DROP PROCEDURE IF EXISTS  `insert_count_row_proc` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_count_row_proc`(IN `in_deptCode` varchar(200),IN `in_deptName` varchar(200),IN `in_desc` varchar(500),OUT out_deptNum bigint)
BEGIN
	#Routine body goes here...
	insert into departments(deptCode,deptName, description) values(in_deptCode, in_deptName, in_desc); 
	select count(*) from departments into out_deptNum;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
