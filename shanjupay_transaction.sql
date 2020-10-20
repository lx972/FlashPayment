/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.31-log : Database - shanjupay_transaction
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shanjupay_transaction` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `shanjupay_transaction`;

/*Table structure for table `app_platform_channel` */

DROP TABLE IF EXISTS `app_platform_channel`;

CREATE TABLE `app_platform_channel` (
  `ID` bigint(20) NOT NULL,
  `APP_ID` varchar(50) DEFAULT NULL COMMENT '应用id',
  `PLATFORM_CHANNEL` varchar(50) DEFAULT NULL COMMENT '平台支付渠道',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='说明了应用选择了平台中的哪些支付渠道';

/*Data for the table `app_platform_channel` */

insert  into `app_platform_channel`(`ID`,`APP_ID`,`PLATFORM_CHANNEL`) values (1316943124441870338,'a94f0fd09d5d4dcd980b42dfa667cdd7','shanju_c2b');

/*Table structure for table `pay_channel` */

DROP TABLE IF EXISTS `pay_channel`;

CREATE TABLE `pay_channel` (
  `ID` bigint(20) NOT NULL,
  `CHANNEL_NAME` varchar(50) DEFAULT NULL COMMENT '原始支付渠道名称',
  `CHANNEL_CODE` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `pay_channel` */

insert  into `pay_channel`(`ID`,`CHANNEL_NAME`,`CHANNEL_CODE`) values (1,'微信JSAPI','WX_JSAPI'),(2,'支付宝手机网站支付','ALIPAY_WAP'),(3,'支付宝条码支付','ALIPAY_BAR_CODE'),(4,'微信付款码支付','WX_MICROPAY'),(5,'微信native支付','WX_NATIVE');

/*Table structure for table `pay_channel_param` */

DROP TABLE IF EXISTS `pay_channel_param`;

CREATE TABLE `pay_channel_param` (
  `ID` bigint(20) NOT NULL,
  `CHANNEL_NAME` varchar(50) DEFAULT NULL COMMENT '配置名称',
  `MERCHANT_ID` bigint(20) DEFAULT NULL COMMENT '商户ID',
  `PAY_CHANNEL` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  `PARAM` text COMMENT '支付参数',
  `APP_PLATFORM_CHANNEL_ID` bigint(20) DEFAULT NULL COMMENT '应用与支付渠道绑定ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='某商户针对某一种原始支付渠道的配置参数';

/*Data for the table `pay_channel_param` */

insert  into `pay_channel_param`(`ID`,`CHANNEL_NAME`,`MERCHANT_ID`,`PAY_CHANNEL`,`PARAM`,`APP_PLATFORM_CHANNEL_ID`) values (1316945542764310529,'支付宝手机网站支付',1316925309978800129,'ALIPAY_WAP','{\"appId\":\"2016101700706706\",\"rsaPrivateKey\":\"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHXQPTEYQ4q1M7FWY0rCLSGsmKm51hbbxpFLd+RFrtUlazEc+IZEo6PQcUov7OWU2mQZMZ3HTROUhcRVxQ/TOgbk9a09ZQ2lSzWnJDGnct4yBPkbIh9K+sJr6CZ0gIbyThr2Ak/RIlr7XpG9tTK793/ejutFyzy/0xST8Q4/XrG2o9pbfBajG4FZog0t+0/FYho2t1q6AmSJnwaK4Yn3CZER1PpGDDu6xPU4Zh/5ilL6dUFzzs0xmQnCYMUsjjkd0rlOevzOo3Zy2DMniN+Hf3/FT4KLGs/XtJVVDt1ox73yVm98zozP329YngiXymaZekR4nT9fXlC7eM4Y8N4DOvAgMBAAECggEAHe0hgFN6EPFHqGNVwkVgOWU0s5Et3TFemzi6TI8eLyOqCVLht/y8MF33p8dVYBd8REpxFCGaLftlFQk8nKct98ULhEAbPKrYWQKhClbajGmPZigG4tzuzbePHNNqqHqyA7c7IVJV5cEQDaZb+epNHWEkU0nKyPFLW88ew0QyxTRo4mbfpY+eVrb3G/fBiYXbRem6TcCsbOQ77eTcMPpnOdP0QpwBCZ0lcbMOawpi9LE6RUv0mjRJYi07kUo5zeodenA3GC9RMf9YCCGClvuZVzR4xiOIO6vE0qT9pYa7uOce0dJhUT0bnKXBnZq4sciVVDRkaY5VTvUHdVB0utdScQKBgQDqwVvup7Lw9zVuwteWvlSUx95XbMKFVhX70GTW+qX+1/+duJ46kxqgvm/RwYLCPrNAqyGwQ9FVG9uNg8Vi1KBbQ1eOzbMo6+uiDtUogE8FVZwZWzSw9YKyIvahrWrLoRi5IiAbkN9UzYzuHti3Dy4/N+01lcIJ6RiHnDB2N8zT1wKBgQCTnQPDnZJc27V3slDqaf0iKS5llZ+eNFlnQwmv5Uz+1Wtb9cso+H4mElIUP13vauriHmXw6Ksy29rxtxPNRpVJyDG6wpKVb5ZvkK1634+uaRkC3lbRCxZZTO0NhaPCIhaXFLW2r6U0YHUYb/enI4z9aom0jihZ7juxF2e3+Msj6QKBgB5+aXOxwvO8GOu/UYPaS2BcKgyPKyFo0kg4hLDMND3LTv/s2FjhfOb+dcX4bgTPYjd3Q1QDKzD0Amv6fuxclEvmjnwVSj15j80oQhYVvK4DtdgxWcHW0lhTZFgSD7pNvclmnmcWRXxdiv3vcdUtmqNJn32Da4YgCjirWDwy+V9XAoGAfi11Dj0e4ykbURmnePjoW87/ze275yuwUEhJe4Vx71LW1mCgLIFcs4ZtiskvrnuiE28QjIEV9f9gg8WOs6Vl7w+lEpNHYV1lJjBxWdrHorpLmtwbMc1caTEMYMafWE5zKOmW+nXhrYfWD/GFq+UDm4r58tChRV4SwCnVirisTCECgYEAgpzraHQkr6wodgki6C/8p2WHkR5IRHwDel9dEGXWhlcrQXmNaSoQnZyQmVhnqrMSX9l76j+1RwKx0MN4IqlwxNx9u8LK+kQOlfPHjzTdvDQqPmbiul1uqMErMn1ifbx80WTTTxDd1gpuN2wmfAKHeJ8kR1o/wzypGyPPv1sHDKg=\",\"notifyUrl\":\"http://33833g4w32.wicp.vip:49739/transaction/ali/notify\",\"returnUrl\":\"http://33833g4w32.wicp.vip:49739/transaction/ali/notify\",\"url\":\"https://openapi.alipaydev.com/gateway.do\",\"chareset\":\"UTF-8\",\"format\":\"json\",\"alipayPublicKey\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArmOjVNydnij80SJh9JbPCZyuDjOI5u/ZNUkzYiDus3UEypE++G/pvPHdbMMcnhVV8DvSCI5qLiUvSxPvxML/+OA4vnYzDu+HEpML0h6Hs01ubmd4YVdpMoN2CWKl2n2PBC73+G8dRHGIWpj9tznADRMVkhQsJsQaWvQWA9j0Mlfe+3okhXX75M3TODZuANbsjfyOyZGG0vCFPnOPMLa1was9JjkAG9pKNRUzvQpff8cIHrT1uhFv7NmDl+a3XclRjWAhUyn+ogAtI7Sc3VXcXMKiTU0IKjY7+m7l5GurraPM672Iz6gbLbUg6tNHEoG2xMsJfs6Mwo4cbe7UIY56bQIDAQAB\",\"signtype\":\"RSA2\"}',1316943124441870338);

/*Table structure for table `pay_order` */

DROP TABLE IF EXISTS `pay_order`;

CREATE TABLE `pay_order` (
  `ID` bigint(20) NOT NULL,
  `TRADE_NO` varchar(50) NOT NULL COMMENT '聚合支付订单号',
  `MERCHANT_ID` bigint(20) NOT NULL COMMENT '所属商户',
  `STORE_ID` bigint(20) DEFAULT NULL COMMENT '商户下门店',
  `APP_ID` varchar(50) NOT NULL COMMENT '所属应用',
  `PAY_CHANNEL` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  `PAY_CHANNEL_MCH_ID` varchar(50) DEFAULT NULL COMMENT '原始渠道商户id',
  `PAY_CHANNEL_MCH_APP_ID` varchar(50) DEFAULT NULL COMMENT '原始渠道商户应用id',
  `PAY_CHANNEL_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '原始渠道订单号',
  `CHANNEL` varchar(50) DEFAULT NULL COMMENT '聚合支付的渠道',
  `OUT_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '商户订单号',
  `SUBJECT` varchar(50) DEFAULT NULL COMMENT '商品标题',
  `BODY` varchar(256) DEFAULT NULL COMMENT '订单描述',
  `CURRENCY` varchar(50) DEFAULT NULL COMMENT '币种CNY',
  `TOTAL_AMOUNT` int(11) DEFAULT NULL COMMENT '订单总金额，单位为分',
  `OPTIONAL` varchar(256) DEFAULT NULL COMMENT '用户自定义的参数,商户自定义数据',
  `ANALYSIS` varchar(256) DEFAULT NULL COMMENT '用于统计分析的数据,用户自定义',
  `EXTRA` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `TRADE_STATE` varchar(50) DEFAULT NULL COMMENT '交易状态支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成,4-关闭',
  `ERROR_CODE` varchar(50) DEFAULT NULL COMMENT '渠道支付错误码',
  `ERROR_MSG` varchar(256) DEFAULT NULL COMMENT '渠道支付错误信息',
  `DEVICE` varchar(50) DEFAULT NULL COMMENT '设备',
  `CLIENT_IP` varchar(50) DEFAULT NULL COMMENT '客户端IP',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '订单过期时间',
  `PAY_SUCCESS_TIME` datetime DEFAULT NULL COMMENT '支付成功时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `unique_TRADE_NO` (`TRADE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `pay_order` */

insert  into `pay_order`(`ID`,`TRADE_NO`,`MERCHANT_ID`,`STORE_ID`,`APP_ID`,`PAY_CHANNEL`,`PAY_CHANNEL_MCH_ID`,`PAY_CHANNEL_MCH_APP_ID`,`PAY_CHANNEL_TRADE_NO`,`CHANNEL`,`OUT_TRADE_NO`,`SUBJECT`,`BODY`,`CURRENCY`,`TOTAL_AMOUNT`,`OPTIONAL`,`ANALYSIS`,`EXTRA`,`TRADE_STATE`,`ERROR_CODE`,`ERROR_MSG`,`DEVICE`,`CLIENT_IP`,`CREATE_TIME`,`UPDATE_TIME`,`EXPIRE_TIME`,`PAY_SUCCESS_TIME`) values (1317409014165827586,'SJ1317409012386033664',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020101722001456430501139296','易买网商品','向wx付款','￥',56,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-17 18:15:59','2020-10-17 18:16:14','2020-10-17 18:45:59','2020-10-17 18:16:12'),(1317409645689597954,'SJ1317409645679800320',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020101722001456430501138833','易买网商品','向wx付款','￥',74,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-17 18:18:30','2020-10-17 18:18:43','2020-10-17 18:48:30','2020-10-17 18:18:42'),(1317409801344413698,'SJ1317409801343004672',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020101722001456430501139481','易买网商品','向wx付款','￥',45,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-17 18:19:08','2020-10-17 18:23:06','2020-10-17 18:49:08','2020-10-17 18:19:17'),(1317411270982381569,'SJ1317411269515554816',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020101722001456430501139675','易买网商品','向wx付款','￥',40,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-17 18:24:58','2020-10-17 18:25:20','2020-10-17 18:54:58','2020-10-17 18:25:14'),(1318350830281273346,'SJ1318350828163096576',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020102022001456430501140893','易买网商品','向wx付款','￥',40,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-20 08:38:26','2020-10-20 08:38:44','2020-10-20 09:08:26','2020-10-20 08:38:39'),(1318353608756015105,'SJ1318353608701435904',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020102022001456430501140894','易买网商品','向wx付款','￥',60,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-20 08:49:29','2020-10-20 08:49:47','2020-10-20 09:19:29','2020-10-20 08:49:42'),(1318357424389873666,'SJ1318357424373710848',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020102022001456430501140895','易买网商品','向wx付款','￥',56,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-20 09:04:39','2020-10-20 09:04:53','2020-10-20 09:34:39','2020-10-20 09:04:49'),(1318365326957608962,'SJ1318365325497270272',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020102022001456430501140565','易买网商品','向wx付款','￥',56,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-20 09:36:02','2020-10-20 09:36:19','2020-10-20 10:06:02','2020-10-20 09:36:16'),(1318370418742001666,'SJ1318370418682560512',1316925309978800129,1316925310247235585,'a94f0fd09d5d4dcd980b42dfa667cdd7','ALIPAY_WAP',NULL,NULL,NULL,'shanju_c2b','2020102022001456430501140566','易买网商品','向wx付款','￥',56,NULL,NULL,NULL,'2',NULL,NULL,NULL,'192.168.198.1','2020-10-20 09:56:17','2020-10-20 09:56:34','2020-10-20 10:26:17','2020-10-20 09:56:29');

/*Table structure for table `payment_bill` */

DROP TABLE IF EXISTS `payment_bill`;

CREATE TABLE `payment_bill` (
  `id` bigint(20) NOT NULL,
  `merchant_id` bigint(20) NOT NULL COMMENT '商户id',
  `merchant_name` varchar(60) NOT NULL COMMENT '商户名称',
  `merchant_app_id` bigint(20) NOT NULL COMMENT '商户应用Id',
  `merchant_order_no` varchar(60) NOT NULL COMMENT '商户订单号',
  `channel_order_no` varchar(60) NOT NULL COMMENT '渠道订单号',
  `product_name` varchar(255) NOT NULL COMMENT '商品名称',
  `create_time` varchar(60) DEFAULT NULL COMMENT '创建时间',
  `pos_time` varchar(60) NOT NULL COMMENT '交易时间',
  `equipment_no` varchar(60) DEFAULT NULL COMMENT '终端号',
  `user_account` varchar(60) DEFAULT NULL COMMENT '用户账号/标识信息',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `trade_amount` decimal(10,2) NOT NULL COMMENT '实际交易金额',
  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '折扣金额',
  `service_fee` decimal(10,4) DEFAULT NULL COMMENT '手续费',
  `refund_order_no` varchar(60) DEFAULT NULL COMMENT '退款单号',
  `refund_money` decimal(10,2) DEFAULT NULL,
  `platform_channel` varchar(50) NOT NULL COMMENT '平台支付渠道',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `payment_bill` */

/*Table structure for table `platform_channel` */

DROP TABLE IF EXISTS `platform_channel`;

CREATE TABLE `platform_channel` (
  `ID` bigint(20) NOT NULL,
  `CHANNEL_NAME` varchar(50) DEFAULT NULL COMMENT '平台支付渠道名称',
  `CHANNEL_CODE` varchar(50) DEFAULT NULL COMMENT '平台支付渠道编码',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `platform_channel` */

insert  into `platform_channel`(`ID`,`CHANNEL_NAME`,`CHANNEL_CODE`) values (1,'闪聚B扫C','shanju_b2c'),(2,'闪聚C扫B','shanju_c2b'),(3,'微信Native支付','wx_native'),(4,'支付宝手机网站支付','alipay_wap');

/*Table structure for table `platform_pay_channel` */

DROP TABLE IF EXISTS `platform_pay_channel`;

CREATE TABLE `platform_pay_channel` (
  `ID` bigint(20) NOT NULL,
  `PLATFORM_CHANNEL` varchar(20) DEFAULT NULL COMMENT '平台支付渠道编码',
  `PAY_CHANNEL` varchar(20) DEFAULT NULL COMMENT '原始支付渠道名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `platform_pay_channel` */

insert  into `platform_pay_channel`(`ID`,`PLATFORM_CHANNEL`,`PAY_CHANNEL`) values (1,'shanju_b2c','WX_MICROPAY'),(2,'shanju_b2c','ALIPAY_BAR_CODE'),(3,'wx_native','WX_NATIVE'),(4,'alipay_wap','ALIPAY_WAP'),(5,'shanju_c2b','WX_JSAPI'),(6,'shanju_c2b','ALIPAY_WAP');

/*Table structure for table `refund_order` */

DROP TABLE IF EXISTS `refund_order`;

CREATE TABLE `refund_order` (
  `ID` bigint(20) NOT NULL,
  `REFUND_NO` varchar(50) DEFAULT NULL COMMENT '聚合支付退款订单号',
  `TRADE_NO` varchar(50) DEFAULT NULL COMMENT '聚合支付订单号',
  `MERCHANT_ID` bigint(20) DEFAULT NULL COMMENT '所属商户',
  `APP_ID` varchar(50) DEFAULT NULL COMMENT '所属应用',
  `PAY_CHANNEL` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  `PAY_CHANNEL_MCH_ID` varchar(50) DEFAULT NULL COMMENT '原始渠道商户id',
  `PAY_CHANNEL_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '原始渠道订单号',
  `PAY_CHANNEL_REFUND_NO` varchar(50) DEFAULT NULL COMMENT '原始渠道退款订单号',
  `CHANNEL` varchar(50) DEFAULT NULL COMMENT '聚合支付的渠道',
  `OUT_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '商户订单号',
  `OUT_REFUND_NO` varchar(50) DEFAULT NULL COMMENT '商户退款订单号',
  `PAY_CHANNEL_USER` varchar(50) DEFAULT NULL COMMENT '原始渠道用户标识,如微信openId,支付宝账号',
  `PAY_CHANNEL_USERNAME` varchar(50) DEFAULT NULL COMMENT '原始渠道用户姓名',
  `CURRENCY` varchar(50) DEFAULT NULL COMMENT '币种CNY',
  `TOTAL_AMOUNT` int(11) DEFAULT NULL COMMENT '订单总金额，单位为分',
  `REFUND_AMOUNT` int(11) DEFAULT NULL COMMENT '退款金额,单位分',
  `OPTIONAL` varchar(256) DEFAULT NULL COMMENT '用户自定义的参数,商户自定义数据',
  `ANALYSIS` varchar(256) DEFAULT NULL COMMENT '用于统计分析的数据,用户自定义',
  `EXTRA` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `REFUND_STATE` varchar(50) DEFAULT NULL COMMENT '退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成',
  `REFUND_RESULT` varchar(50) DEFAULT NULL COMMENT '退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
  `ERROR_CODE` varchar(50) DEFAULT NULL COMMENT '渠道支付错误码',
  `ERROR_MSG` varchar(256) DEFAULT NULL COMMENT '渠道支付错误信息',
  `DEVICE` varchar(50) DEFAULT NULL COMMENT '设备',
  `CLIENT_IP` varchar(50) DEFAULT NULL COMMENT '客户端IP',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '订单过期时间',
  `REFUND_SUCCESS_TIME` datetime DEFAULT NULL COMMENT '退款成功时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `refund_order` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
