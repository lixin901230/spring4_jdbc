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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='部门信息表';

/*Data for the table `departments` */

insert  into `departments`(`id`,`deptCode`,`deptName`,`description`) values (1,'dept01','部门01','测试部门01'),(2,'dept02','部门02','测试部门02'),(3,'dept03','财务部','财务部'),(4,'dept04','行政部','行政部'),(5,'dept05','市场部','市场部'),(6,'dept06','人事部','人事部'),(7,'dept07','项目开发部','项目开发部'),(8,'dept08','产品研发部','产品研发部'),(9,'dept09','产品组','产品组'),(10,'dept10','运维部','运维部'),(11,'dept11','运营部','运营部'),(12,'dept12','销售部','销售部'),(13,'dept13','测试部','测试部'),(14,'dept14','总裁部','总裁部');

/*Table structure for table `employees` */

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trueName` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) DEFAULT NULL COMMENT 'email',
  `dept_id` int(50) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='员工信息表';

/*Data for the table `employees` */

insert  into `employees`(`id`,`trueName`,`email`,`dept_id`) values (1,'邓总','1234@sina.com',14),(2,'张总','2345@sina.com',14),(3,'吴总','2343@sian.com',14),(4,'卢总','3432@sina.com',14),(5,'kevinli','kevinli@sina.com',8);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
