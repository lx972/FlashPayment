/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.31-log : Database - shanjupay_merchant_service
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shanjupay_merchant_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `shanjupay_merchant_service`;

/*Table structure for table `app` */

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `ID` bigint(20) NOT NULL,
  `APP_ID` varchar(50) DEFAULT NULL,
  `APP_NAME` varchar(50) DEFAULT NULL COMMENT '商店名称',
  `MERCHANT_ID` bigint(20) DEFAULT NULL COMMENT '所属商户',
  `PUBLIC_KEY` longblob COMMENT '应用公钥(RSAWithSHA256)',
  `NOTIFY_URL` varchar(50) DEFAULT NULL COMMENT '授权回调地址',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `APP_ID` (`APP_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `app` */

insert  into `app`(`ID`,`APP_ID`,`APP_NAME`,`MERCHANT_ID`,`PUBLIC_KEY`,`NOTIFY_URL`) values (1316943078799568897,'a94f0fd09d5d4dcd980b42dfa667cdd7','闪聚宝',1316925309978800129,'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArmOjVNydnij80SJh9JbPCZyuDjOI5u/ZNUkzYiDus3UEypE++G/pvPHdbMMcnhVV8DvSCI5qLiUvSxPvxML/+OA4vnYzDu+HEpML0h6Hs01ubmd4YVdpMoN2CWKl2n2PBC73+G8dRHGIWpj9tznADRMVkhQsJsQaWvQWA9j0Mlfe+3okhXX75M3TODZuANbsjfyOyZGG0vCFPnOPMLa1was9JjkAG9pKNRUzvQpff8cIHrT1uhFv7NmDl+a3XclRjWAhUyn+ogAtI7Sc3VXcXMKiTU0IKjY7+m7l5GurraPM672Iz6gbLbUg6tNHEoG2xMsJfs6Mwo4cbe7UIY56bQIDAQAB','');

/*Table structure for table `merchant` */

DROP TABLE IF EXISTS `merchant`;

CREATE TABLE `merchant` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `MERCHANT_NAME` varchar(50) DEFAULT NULL COMMENT '商户名称',
  `MERCHANT_NO` varchar(32) DEFAULT NULL COMMENT '企业编号',
  `MERCHANT_ADDRESS` varchar(255) DEFAULT NULL COMMENT '企业地址',
  `MERCHANT_TYPE` varchar(50) DEFAULT NULL COMMENT '商户类型',
  `BUSINESS_LICENSES_IMG` varchar(100) DEFAULT NULL COMMENT '营业执照（企业证明）',
  `ID_CARD_FRONT_IMG` varchar(100) DEFAULT NULL COMMENT '法人身份证正面照片',
  `ID_CARD_AFTER_IMG` varchar(100) DEFAULT NULL COMMENT '法人身份证反面照片',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '联系人姓名',
  `MOBILE` varchar(50) DEFAULT NULL COMMENT '联系人手机号(关联统一账号)',
  `CONTACTS_ADDRESS` varchar(255) DEFAULT NULL COMMENT '联系人地址',
  `AUDIT_STATUS` varchar(20) DEFAULT NULL COMMENT '审核状态 0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝',
  `TENANT_ID` bigint(20) DEFAULT NULL COMMENT '租户ID,关联统一用户',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `merchant` */

insert  into `merchant`(`ID`,`MERCHANT_NAME`,`MERCHANT_NO`,`MERCHANT_ADDRESS`,`MERCHANT_TYPE`,`BUSINESS_LICENSES_IMG`,`ID_CARD_FRONT_IMG`,`ID_CARD_AFTER_IMG`,`USERNAME`,`MOBILE`,`CONTACTS_ADDRESS`,`AUDIT_STATUS`,`TENANT_ID`) values (1316925309978800129,'易买网','123456789','湖北省黄冈市营山县','教育','http://qhewxzg37.hn-bkt.clouddn.com/2ba97e5e-e0e8-40fc-93ae-33cf90f911e9.png','http://qhewxzg37.hn-bkt.clouddn.com/01ae468c-43d2-4f0f-93d4-6ff347a0e67a.png','http://qhewxzg37.hn-bkt.clouddn.com/24788a46-fcca-4985-aac6-14d4ad0fd1d3.png','wx','17607139545','废话废话多发的吧','2',17);

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `MERCHANT_ID` bigint(20) DEFAULT NULL COMMENT '商户ID',
  `FULL_NAME` varchar(50) DEFAULT NULL COMMENT '姓名',
  `POSITION` varchar(50) DEFAULT NULL COMMENT '职位',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户名(关联统一用户)',
  `MOBILE` varchar(50) DEFAULT NULL COMMENT '手机号(关联统一用户)',
  `STORE_ID` bigint(20) DEFAULT NULL COMMENT '员工所属门店',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `STAFF_STATUS` int(11) DEFAULT NULL COMMENT '0表示禁用，1表示启用',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `staff` */

insert  into `staff`(`ID`,`MERCHANT_ID`,`FULL_NAME`,`POSITION`,`USERNAME`,`MOBILE`,`STORE_ID`,`LAST_LOGIN_TIME`,`STAFF_STATUS`) values (1316925310679248898,1316925309978800129,NULL,NULL,'wx','17607139545',1316925310247235585,NULL,1);

/*Table structure for table `store` */

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `ID` bigint(20) NOT NULL,
  `STORE_NAME` varchar(50) DEFAULT NULL COMMENT '门店名称',
  `STORE_NUMBER` bigint(20) DEFAULT NULL COMMENT '门店编号',
  `MERCHANT_ID` bigint(20) DEFAULT NULL COMMENT '所属商户',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父门店',
  `STORE_STATUS` int(11) DEFAULT NULL COMMENT '0表示禁用，1表示启用',
  `STORE_ADDRESS` varchar(50) DEFAULT NULL COMMENT '门店地址',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `store` */

insert  into `store`(`ID`,`STORE_NAME`,`STORE_NUMBER`,`MERCHANT_ID`,`PARENT_ID`,`STORE_STATUS`,`STORE_ADDRESS`) values (1316925310247235585,'根门店',NULL,1316925309978800129,NULL,1,NULL);

/*Table structure for table `store_staff` */

DROP TABLE IF EXISTS `store_staff`;

CREATE TABLE `store_staff` (
  `ID` bigint(20) NOT NULL,
  `STORE_ID` bigint(20) DEFAULT NULL COMMENT '门店标识',
  `STAFF_ID` bigint(20) DEFAULT NULL COMMENT '员工标识',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `store_staff` */

insert  into `store_staff`(`ID`,`STORE_ID`,`STAFF_ID`) values (1316925310826049538,1316925310679248898,1316925310247235585);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
