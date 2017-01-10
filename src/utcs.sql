-- 区域参数表
CREATE TABLE `utcs_region_param`(
`region_id` int NOT NULL AUTO_INCREMENT COMMENT '区域ID',
`REGION_NUM` varchar(24) COMMENT '区域编号 全局唯一',
`region_name` VARCHAR(32) NOT NULL COMMENT '区域名称',
`DEPT_ID` INT NOT NULL COMMENT '所属部门ID',
`REGION_TYPE` INT NOT NULL COMMENT '区域类型 0-区域 1-子区',
`FATHER_REGION_ID` INT COMMENT '父级区域ID，当REGION_TYPE=1时有效',
`REGION_STATE` INT NOT NULL DEFAULT 0 COMMENT '区域状态(0-正常在线，2 脱机/断线 3-异常故障)',
`REGION_DESC` VARCHAR(255) COMMENT '区域描述',
 PRIMARY KEY (`region_id`),
 UNIQUE KEY `IDX_REGION_NUM` (`REGION_NUM`),
 KEY `IDX_DEPT_ID` (`DEPT_ID`),
 CONSTRAINT `IDX_DEPT_ID` FOREIGN KEY (`DEPT_ID`) REFERENCES `utcs_dept` (`DEPT_ID`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
-- 表初始化
INSERT INTO `utcs_region_param` (REGION_NUM,region_name,DEPT_ID,REGION_TYPE) VALUES('123456','南昌',26,0) ;
INSERT INTO `utcs_region_param` (REGION_NUM,region_name,DEPT_ID,REGION_TYPE) VALUES('123457','新建',26,0) ;
INSERT INTO `utcs_region_param` (REGION_NUM,region_name,DEPT_ID,REGION_TYPE) VALUES('123458','红谷滩',26,0) ;


-- 路口参数表
CREATE TABLE `UTCS_CROSS_PARAM` (
`CROSS_ID` INT NOT NULL AUTO_INCREMENT COMMENT '路口ID，自增字段',
`CROSS_NUM` VARCHAR(24) COMMENT '路口编号，全局唯一，取值区域编号+5位数字',
`CROSS_NAME` VARCHAR(32) NOT NULL COMMENT '路口名称',
`REGION_ID` INT NOT NULL COMMENT '所属区域ID',
`FEATURE` INT NOT NULL DEFAULT 41 COMMENT '路口特征（00-环形交叉路口 11-匝道/出入口 21-路段<只有两个方向的路口>，1-T形路口，32-Y形路口 33-错位T形路口 34-错位Y形路口 41-十字形路口 42-斜交路口 51-多路路口 99-其他）',
`ISKEY` INT NOT NULL COMMENT '是否关键路口（0-非关键路口 1-关键路口）',
`CROSS_TYPE` INT COMMENT '路口类型（0-一般路口 1-特殊路口）',
`SIGNAL_CONTROLER_ID` INT COMMENT '信号机设备ID',
`CROSS_STATE` INT NOT NULL DEFAULT 0 COMMENT '路口状态（0-正常在线，2脱机/断线 4-异常故障）',
`CROSS_DESC` VARCHAR(255) COMMENT '路口描述',
 PRIMARY KEY (CROSS_ID),
 UNIQUE KEY `IDX_CROSS_NUM` (`CROSS_NUM`),
 KEY `IDX_REGION_ID` (`REGION_ID`),
 CONSTRAINT `IDX_REGION_ID` FOREIGN KEY (`REGION_ID`) REFERENCES `utcs_region_param` (`REGION_ID`) ON DELETE CASCADE,
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='路口参数表';

-- 表初始化
INSERT INTO `UTCS_CROSS_PARAM` (CROSS_NUM,CROSS_NAME,REGION_ID,ISKEY) VALUES('123456000','工业四路',10,0);
INSERT INTO `UTCS_CROSS_PARAM` (CROSS_NUM,CROSS_NAME,REGION_ID,ISKEY) VALUES('123456001','工业三路',10,0);
INSERT INTO `UTCS_CROSS_PARAM` (CROSS_NUM,CROSS_NAME,REGION_ID,ISKEY) VALUES('123457000','工业二路',12,0);
INSERT INTO `UTCS_CROSS_PARAM` (CROSS_NUM,CROSS_NAME,REGION_ID,ISKEY) VALUES('123458000','工业一路',13,0);

-- 信号机参数表
DROP TABLE IF EXISTS `utcs_signal_controler`;
CREATE TABLE `utcs_signal_controler` (
  `SIGNAL_CONTROLER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '信号机ID,自增字段',
  `SIGNAL_CONTROLER_NUM` varchar(24) NOT NULL COMMENT '信号机编号 取值12位交通部门管理机构代码+5为数字',
  `SIGNAL_CONTROLER_NAME` varchar(128) NOT NULL COMMENT '信号机名称',
  `CROSS_ID` INT NOT NULL COMMENT '路口ID',
  `SERVER_ID` int(11) NOT NULL COMMENT '所属服务器ID',
  `SUPPLIER` int(128) DEFAULT NULL COMMENT '供应商(即生产商)(0-南昌金科)',
  `TYPE` int(11) DEFAULT NULL COMMENT '规格型号,与SUPPLIER有关,当SUPPLIER=0时,可选值0-JKC3,1-JKD3,2-JKE3.3-XT100',
  `PROTOCOL_NUM` varchar(24) DEFAULT NULL COMMENT '协议号,与SUPPLIER和TYPE有关,JKC3(1.0/2.0) JKD3(1.0/2.0/3/0) JKE3(1.0/2.0) XT100(1.0)',
  `DEVICE_IP` varchar(24) DEFAULT NULL COMMENT '设备IP地址',
  `DEVICE_PORT` int(11) DEFAULT NULL COMMENT '设备通信端口（预留）',
  `SIGNAL_STATE` int(11) DEFAULT NULL COMMENT '信号机状态（0-正常在线 2-脱机/断线 4-异常故障）',
  `ERROR_ID` int(11) NOT NULL DEFAULT '0' COMMENT 'SIGNAL_STATE取值为4时有效,最新最无知,0-无错误,取值于信号机故障表SIGNALCONTROLERERROR中的ERROR_ID',
  `LONGITUDE` varchar(24) DEFAULT NULL COMMENT '信号机经度',
  `LATITUDE` varchar(24) DEFAULT NULL COMMENT '信号机纬度',
  `MAP_SIGN` int(11) DEFAULT '0' COMMENT '地图标注 0-未标注 1-已标注',
  PRIMARY KEY (`SIGNAL_CONTROLER_ID`),
  UNIQUE KEY `IDX_SIGNAL_CONTROLER_NUM` (`SIGNAL_CONTROLER_NUM`),
  KEY `IDX_SERVER_ID` (`SERVER_ID`),
  CONSTRAINT `IDX_SERVER_ID` FOREIGN KEY (`SERVER_ID`) REFERENCES `utcs_server_param` (`SERVER_ID`) ON DELETE CASCADE,
  KEY `IDX_CROSS_ID` (`CROSS_ID`),
  CONSTRAINT `IDX_CROSS_ID` FOREIGN KEY (`CROSS_ID`) REFERENCES `utcs_cross_param` (`CROSS_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='信号机参数表';

-- 信号机参数表初始化
INSERT INTO `UTCS_SIGNAL_CONTROLER` (CROSS_ID,SIGNAL_CONTROLER_NUM,SIGNAL_CONTROLER_NAME)
    VALUE
   (10,11111111111111111,'工业四路信号机'),
   (13,22222222222222222,'工业三路信号机'),
   (14,33333333333333333,'工业二路信号机'),
   (15,55555555555555555,'工业一路信号机'),
   (10,66666666666666666,'长富大道信号机'),
   (10,77777777777777777,'长堎大道信号机'),
   (10,88888888888888888,'文化大道信号机');

--网络参数表
DROP TABLE IF EXISTS `UTCS_NETWORK_PARAM`;
CREATE TABLE `UTCS_NETWORK_PARAM`(
  `NET_ID` INT NOT NULL AUTO_INCREMENT COMMENT '网络参数ID,自增字段',
  `INSIDE_PORT` INT NOT NULL COMMENT '内部协议监听端口',
  `INSIDE_CONNUM` INT NOT NULL COMMENT '内部协议连接数 最大100',
  `INSIDE_HJNUM` INT NOT NULL COMMENT '内部协议有效心跳次数',
  `INSIDE_HJSTEP` INT NOT NULL COMMENT '内部协议心跳步长',
  `GAT1049_PORT` INT NOT NULL COMMENT 'GAT1049协议监听端口',
  `GAT1049_CONNUM` INT NOT NULL COMMENT 'GAT1049协议连接数 最大100',
  `GAT1049_HJNUM` INT NOT NULL COMMENT 'GAT1049协议有效心跳次数',
  `GAT1049_HJSTEP` INT NOT NULL COMMENT 'GAT1049协议心跳步长',
   `GAT1049_USER` VARCHAR(16) NOT NULL COMMENT 'GAT1049协议登录用户名',
  `GAT1049_PWD` VARCHAR(16) NOT NULL COMMENT 'GAT1049协议登录密码',
  PRIMARY KEY (NET_ID)
)ENGINE =InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT ='网络参数表';

-- 网络参数表初始化
INSERT INTO `UTCS_NETWORK_PARAM` (NET_ID,INSIDE_PORT,INSIDE_CONNUM,INSIDE_HJNUM,INSIDE_HJSTEP,GAT1049_PORT,GAT1049_CONNUM,GAT1049_HJNUM,GAT1049_HJSTEP,GAT1049_USER,GAT1049_PWD)
    VALUE
   (10,6000,30,3,10,7000,30,3,10,'admin','123456')

--服务器参数表
DROP TABLE IF EXISTS `UTCS_SERVER_PARAM`;
CREATE TABLE `UTCS_SERVER_PARAM`(
  `SERVER_ID` INT NOT NULL AUTO_INCREMENT COMMENT '服务器参数ID,自增字段',
  `SERVER_NO` VARCHAR(16) NOT NULL COMMENT '服务器编号',
  `SERVER_IP` VARCHAR(32) NOT NULL COMMENT '服务器IP地址',
  `ISCENTER` INT COMMIT '中心/区域服务器标志 0-中心服务器 1-区域服务器',
  PRIMARY KEY (`SERVER_ID`)
  UNIQUE KEY IDX_SERVER_NO (SERVER_NO),
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '服务器参数表';

--服务器参数表初始化
INSERT INTO `UTCS_SERVER_PARAM` (SERVER_ID, SERVER_NO, SERVER_IP, ISCENTER)
   VALUE
   (10,'S001','192.168.0.1',0);


--控件通信参数表
DROP TABLE IF EXISTS `UTCS_PLUGIN_PARAM`;
CREATE TABLE `UTCS_PLUGIN_PARAM`(
  `PLUGIN_ID` INT NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `SERVER_IP` VARCHAR(32) NOT NULL COMMENT '服务器IP地址',
  `FTP_USER` VARCHAR(16) NOT NULL COMMENT 'FTP用户名',
  `FTP_PWD` VARCHAR(16) NOT NULL COMMENT 'FTP密码',
  `FTP_PORT` INT NOT NULL COMMENT 'FTP端口号',
  `TCP_PORT` INT NOT NULL COMMENT 'TCP端口号',
  `FTP_DIR` VARCHAR(16) NOT NULL COMMENT 'FTP根路径',
   PRIMARY KEY (`PLUGIN_ID`)
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '控件通信参数表';

--控件通信参数初始化
INSERT INTO `UTCS_PLUGIN_PARAM` (PLUGIN_ID, SERVER_IP, FTP_USER, FTP_PWD, FTP_PORT, TCP_PORT) VALUE (10,'192.168.200.244','admin','123456','8401','5000');

--非国标信号机数据表 START
DROP TABLE IF EXISTS `UTCS_SIGNAL`;
CREATE TABLE `UTCS_SIGNAL`(
  `SIGNAL_ID`    INT not null AUTO_INCREMENT COMMENT '信号机ID',
  `CROSS_ID`     INT NOT NULL COMMENT '路口ID',
  `SIGNAL_NAME`       VARCHAR(255) not null COMMENT '信号机名称',
  `ASSET_CODE`  INT  COMMENT '资产编码',
  `SIGNAL_TYPE`    INT not null COMMENT '信号机类型',
  `ROAD_TYPE`    INT COMMENT '路口类型',
  `DEVICE_IPADDR`    VARCHAR(50) COMMENT '设备IP地址',
  `COMM_PORT`             INT COMMENT '通信端口号',
  `COMM_PROTOCOL_NUM`       VARCHAR(24) COMMENT '协议号',
  `SERVER_ID` INT not null COMMENT '服务器ID',
  `ROAD_BACKGROUND_MAP`     blob COMMENT '路口背景图' ,
  `LIGHT_SET`               VARCHAR(120) COMMENT '16个灯驱板配置(F型方可配)',
  `BACKGROUND_MAP_WIDTH`    int COMMENT '背景图宽度',
  `BACKGROUND_MAP_HEIGHT`   int COMMENT '背景图高度',
  `USE_STATUS`              int default 0 not null COMMENT '使用状态(0-启用,2-维护)',
  `UPDATE_TIME`             DATE COMMENT '更新时间',
  `UPDATE_ACCOUNT`          VARCHAR(20) COMMENT '更新账号',
  `LONGITUDE`               VARCHAR(24) COMMENT '经度',
  `LATITUDE`                VARCHAR(24) COMMENT '纬度',
  `MAP_SIGN`                INT COMMENT '地图标注(0-未标注,1-已标注)',
  `TRAFFICPIC_SET`          VARCHAR(300) COMMENT '车流量通道配置',
  `SPECIALLIGHTNAME`        VARCHAR(700) COMMENT '每个特殊灯色表的名称，共60个特殊灯色表(JK-F)',
   PRIMARY KEY (`SIGNAL_ID`),
    KEY `IDX_SIGNALE_CROSS_ID` (`CROSS_ID`),
   CONSTRAINT `IDX_SIGNALE_CROSS_ID` FOREIGN KEY (`CROSS_ID`) REFERENCES `utcs_cross_param` (`CROSS_ID`) ON DELETE CASCADE,
    KEY `IDX_SIGNAL_SERVER_ID` (`SERVER_ID`),
  CONSTRAINT `IDX_SIGNAL_SERVER_ID` FOREIGN KEY (`SERVER_ID`) REFERENCES `utcs_server_param` (`SERVER_ID`) ON DELETE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '非国标信号机参数';

--信号机车道
DROP TABLE IF EXISTS `UTCS_SIGNAL_DRIVERWAY`;
CREATE TABLE `UTCS_SIGNAL_DRIVERWAY`(
  `DRIVERWAY_ID`   int not null AUTO_INCREMENT COMMENT '车道ID',
  `SIGNAL_ID`      int  not null COMMENT '信号机ID',
  `DRIVERWAY_NAME` VARCHAR (32) COMMENT '车道名称',
  `DRIVERWAY_X`    INT COMMENT '车道在图片中的横坐标' ,
  `DRIVERWAY_Y`    INT COMMENT '车道在图片中的纵坐标',
  `EDDY_ANGLE`     INT COMMENT '旋转角度',
  `FONT_COLOR`     VARCHAR (12) COMMENT '字体颜色',
  `FONT_SIZE`      INT COMMENT '字体大小',
  PRIMARY KEY (`DRIVERWAY_ID`),
    KEY `IDX_SIGNAL_ID` (`SIGNAL_ID`),
   CONSTRAINT `IDX_SIGNAL_ID` FOREIGN KEY (`SIGNAL_ID`) REFERENCES `utcs_signal` (`SIGNAL_ID`) ON DELETE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '非国标信号机车道表';

--灯组表
DROP TABLE IF EXISTS `UTCS_SIGNAL_LIGHT`;
CREATE TABLE `UTCS_SIGNAL_LIGHT`(
  `LIGHT_ID`               int not null AUTO_INCREMENT COMMENT '灯组ID',
  `SIGNAL_ID`                int not null COMMENT '信号机ID',
  `LIGHT_WIDTH`              int COMMENT '灯组宽度',
  `LIGHT_HEIGHT`             int COMMENT '灯组高度',
  `LIGHT_NUM`                int COMMENT '所属灯驱板板号(1~16)',
  `LIGHT_OUTPUT`             int COMMENT '所在灯驱板输出号(1~4)',
  `LIGHT_X`                  int COMMENT '在路口图片中的横坐标',
  `LIGHT_Y`                  int COMMENT '在路口图片中的纵坐标',
  `DRIVERWAY_TYPE`           int COMMENT '车道类型(0-机动车,1-人行)',
  `DRIVERWAY_DIRECTION`      int COMMENT '车道方向(0-东,1-南,2-西,3-北,4-东南,5-西南,6-西北,7-东北)',
  `DRIVERWAY_DIRECTION_TYPE` int COMMENT '车道方向类型(0-左,1-直,2-右,3-人行)',
  `EDDY_ANGLE`               int COMMENT '旋转角度(单位:度,不包括)',
   PRIMARY KEY (`LIGHT_ID`),
    KEY `IDX_LIGHT_SIGNAL_ID` (`SIGNAL_ID`),
   CONSTRAINT `IDX_LIGHT_SIGNAL_ID` FOREIGN KEY (`SIGNAL_ID`) REFERENCES `utcs_signal` (`SIGNAL_ID`) ON DELETE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '非国标信号机灯组表';

--信号机交通图片表
DROP TABLE IF EXISTS `UTCS_SIGNAL_TRAFFICPIC`;
CREATE TABLE `UTCS_SIGNAL_TRAFFICPIC`(
  TRAFFICPIC_ID        int not null AUTO_INCREMENT COMMENT 'ID',
  SIGNAL_ID            int not null COMMENT '信号机设备ID，外键',
  TRAFFICPIC_DIRECTION int not null COMMENT '方向(0-东左，1-东直，2-东右...........9-北左，10-北直，11-北右)',
  TRAFFICPIC_X         int not null COMMENT '横坐标',
  TRAFFICPIC_Y         int not null COMMENT '纵坐标',
  TRAFFICPIC_WIDTH     int not null COMMENT '宽度',
  TRAFFICPIC_HEIGHT    int not null COMMENT '高度',
  TRAFFICPIC_ANGLE     DOUBLE not null COMMENT '角度',
  TRAFFICPIC_FONTCOLOR VARCHAR (12) not null COMMENT '字体颜色',
  TRAFFICPIC_FONTSIZE  int not null COMMENT '字体大小',
  TRAFFICPIC_YELLOW    int not null COMMENT '交通状态阀值(黄色状态)',
  TRAFFICPIC_RED       int not null COMMENT '交通状态阀值(红色状态)',
  PRIMARY KEY (`TRAFFICPIC_ID`),
   KEY `IDX_TRAFFICPICT_SIGNAL_ID` (`SIGNAL_ID`),
   CONSTRAINT `IDX_TRAFFICPICT_SIGNAL_ID` FOREIGN KEY (`SIGNAL_ID`) REFERENCES `utcs_signal` (`SIGNAL_ID`) ON DELETE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '非国标信号机交通图片表';
--END

--警卫任务方案表
DROP TABLE IF EXISTS `UTCS_GUARD`;
CREATE TABLE `UTCS_GUARD`(
   `GUARD_ID`   int not null AUTO_INCREMENT COMMENT '方案ID',
   `GUARD_NAME` VARCHAR(32) NOT  NULL COMMENT '方案名称',
   `GUARD_STATUS`     int default 0 not null COMMENT '方案状态 0-未审核 1-已审核',
   `POINTS`     VARCHAR(1024) COMMENT '地图秒点集合',
   `RESERVE`    VARCHAR(32) COMMENT '预留字段',
   `RESERVE1`   INT  COMMENT '预留字段',
   PRIMARY KEY (`GUARD_ID`)
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '警卫任务方案表';

--警卫任务信号机表
DROP TABLE IF EXISTS `UTCS_GUARD_SIGNAL`;
CREATE TABLE `UTCS_GUARD_SIGNAL`(
   `GUARD_SIGNAL_ID`   int not null AUTO_INCREMENT COMMENT 'ID',
   `GUARD_ID` int NOT  NULL COMMENT '所属方案ID',
   `GUARD_INDEX` INT NOT NULL COMMENT '所在方案的序号',
   `LAST_TO_TIME`     int default 0 not null COMMENT '上一个路口到达该路口所需时间秒数',
   `PASS_TIME`     int default 0 not null COMMENT '通过该路口所需秒数',
   `DIRECTION`    int default 0 not null COMMENT '方向',
   `SIGNAL_CONTROLER_ID` INT NOT NULL COMMENT '所属信号机ID',
   `SIGNAL_SIGN` INT DEFAULT 0 NOT NULL COMMENT '预留字段 信号机标识 0-国标信号机 1-非国标信号机',
   `RESERVE`    VARCHAR(32) COMMENT '预留字段',
   `RESERVE1`   INT  COMMENT '预留字段',
   PRIMARY KEY (`GUARD_SIGNAL_ID`)
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '警卫任务信号机表';

DROP TABLE IF EXISTS `UTCS_TRAFFIC_DATA`;
CREATE TABLE `UTCS_TRAFFIC_DATA`(
  `TRAFFIC_DATA_ID` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `CROSS_ID` INT NOT NULL COMMENT '路口ID',
  `CROSS_DATE` DATE COMMENT '统计日期',
  `CROSS_TIME` TIME COMMENT '统计时间',
  `EAST_LEFT` INT  COMMENT '东左方向',
  `EAST_STRAIGHT` INT  COMMENT '东直方向',
  `EAST_RIGHT` INT  COMMENT '东右方向',
  `SOUTH_LEFT` INT  COMMENT '南左方向',
  `SOUTH_STRAIGHT` INT  COMMENT '南直方向',
  `SOUTH_RIGHT` INT  COMMENT '南右方向',
  `WEST_LEFT` INT  COMMENT '西左方向',
  `WEST_STRAIGHT` INT  COMMENT '西直方向',
  `WEST_RIGHT` INT  COMMENT '西右方向',
  `NORTH_LEFT` INT  COMMENT '北左方向',
  `NORTH_STRAIGHT` INT  COMMENT '北直方向',
  `NORTH_RIGHT` INT  COMMENT '北右方向',
  PRIMARY KEY (`TRAFFIC_DATA_ID`),
  KEY `IDX_CROSS_ID` (`CROSS_ID`),
   CONSTRAINT `IDX_CROSS_ID` FOREIGN KEY (`CROSS_ID`) REFERENCES `utcs_cross_param` (`CROSS_ID`) ON DELETE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET=UTF8 COMMENT = '交通流量信息表';