/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.31-log : Database - shanjupay_uaa
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shanjupay_uaa` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `shanjupay_uaa`;

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '客户端标识',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` longtext,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `archived` tinyint(4) DEFAULT NULL,
  `trusted` tinyint(4) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='接入客户端信息';

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`create_time`,`archived`,`trusted`,`autoapprove`) values ('507f38136d4443f3a07f07d3ce5f9a05','shanju-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2019-11-13 08:18:32',NULL,0,NULL),('5fe423628cd14143a94d05575323555d','shanju-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2019-11-13 11:00:29',NULL,0,NULL),('8016770e53ab495cb88bacb626930ceb','shanju-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2019-11-13 08:16:27',NULL,0,NULL),('e366cfc9373e440abbdbe5818aa6c91b','shanju-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2019-11-13 10:02:01',NULL,0,NULL),('merchant-platform','shanju-resource','123456','read','client_credentials,password,authorization_code,implicit,refresh_token','http://www.baidu.com','ROLE_MERCHANT,ROLE_USER',31536000,259200,NULL,'2020-10-14 10:55:45',0,0,'false'),('operation-platform','shanju-resource','123456','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_OPERATION',7200,259200,NULL,'2019-09-26 10:10:07',0,0,'false'),('portal-site','shanju-resource','123456','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_PORTAL',7200,259200,NULL,'2019-09-26 10:10:18',0,0,'false'),('shanju-resource','shanju-resource','suiyixie','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2019-10-24 02:29:38',NULL,0,NULL);

/*Table structure for table `oauth_code` */

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `code` varchar(255) NOT NULL COMMENT '授权码',
  `authentication` longblob COMMENT '认证信息',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='授权码';

/*Data for the table `oauth_code` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
