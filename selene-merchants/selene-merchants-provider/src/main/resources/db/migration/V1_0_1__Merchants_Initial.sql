/*Table structure for table merchants_org */
DROP TABLE IF EXISTS merchants_org;
CREATE TABLE merchants_org (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Name varchar(50) NOT NULL COMMENT '名称',
  Code varchar(50) NOT NULL COMMENT '编码',
  Parent_ID int(10) NOT NULL COMMENT '父节点',
  Order_ID int(10) DEFAULT NULL COMMENT '排序编号',
  Status int(1) NOT NULL COMMENT '状态',
  Inherit int(1) DEFAULT NULL COMMENT '继承',
  Create_Time datetime NOT NULL COMMENT '创建时间',
  Creator_ID int(10) NOT NULL COMMENT '创建',
  Update_Time datetime NOT NULL COMMENT '修改时间',
  Updater_ID int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_org COMMENT '商户机构表';

/*Table structure for table merchants_org_role */
DROP TABLE IF EXISTS merchants_org_role;
CREATE TABLE merchants_org_role (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Group_ID int(10) NOT NULL COMMENT '用户组编号',
  Org_ID int(10) DEFAULT NULL COMMENT '机构编号',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_org_role COMMENT '商户机构角色表';

/*Table structure for table merchants_user */
DROP TABLE IF EXISTS merchants_user;
CREATE TABLE merchants_user (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Type int(4) NOT NULL COMMENT '类型',
  User_Type int(4) NOT NULL COMMENT '账户类型',
  Name varchar(32) NOT NULL COMMENT '账户',
  Real_Name varchar(20) DEFAULT NULL COMMENT '真实姓名',
  Nick_Name varchar(32) DEFAULT NULL COMMENT '昵称',
  User_Password varchar(50) DEFAULT NULL COMMENT '密码',
  Org_ID int(10) DEFAULT NULL COMMENT '机构编号',
  Sex int(1) DEFAULT NULL COMMENT '性别',
  Order_ID int(11) NOT NULL COMMENT '排序编号',
  IP_Address varchar(500) DEFAULT NULL COMMENT 'IP',
  ID_Card varchar(20) DEFAULT NULL COMMENT '身份证号',
  Phone_Number varchar(20) DEFAULT NULL COMMENT '手机号码',
  Email varchar(100) DEFAULT NULL COMMENT '电子邮件',
  Position varchar(100) DEFAULT NULL COMMENT '区域',
  Pic varchar(100) DEFAULT NULL COMMENT '头像',
  Status int(3) NOT NULL COMMENT '状态',
  Create_Time datetime NOT NULL COMMENT '创建时间',
  Creator_ID int(11) NOT NULL COMMENT '创建',
  Update_Time datetime NOT NULL COMMENT '修改时间',
  Updater_ID int(11) NOT NULL COMMENT '修改',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_user COMMENT '商户用户表';

/*Table structure for table merchants_user_action */
DROP TABLE IF EXISTS merchants_user_action;
CREATE TABLE merchants_user_action (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Name varchar(50) NOT NULL COMMENT '名称',
  Code varchar(50) NOT NULL COMMENT '编码',
  Parent_ID int(11) NOT NULL COMMENT '父节点',
  Type int(11) NOT NULL COMMENT '权限类型(0-后台权限 1-前台权限)',
  Uri varchar(2000) DEFAULT NULL COMMENT '路径',
  Icon_Skin varchar(256) DEFAULT NULL COMMENT '图标',
  Order_ID int(10) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_user_action COMMENT '商户用户权限表';

/*Table structure for table merchants_role_action */
DROP TABLE IF EXISTS merchants_role_action;
CREATE TABLE merchants_role_action (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Group_ID int(10) NOT NULL COMMENT '用户组编号',
  Action_ID int(10) NOT NULL COMMENT '权限编号',
  Type int(10) NOT NULL COMMENT '权限类型(0-后台权限 1-前台权限)',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_role_action COMMENT '商户角色权限表';

/*Table structure for table merchants_role_data_authority */
DROP TABLE IF EXISTS merchants_role_data_authority;
CREATE TABLE merchants_role_data_authority (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Group_ID int(10) NOT NULL COMMENT '用户组编号',
  Object_Type int(3) NOT NULL COMMENT '对象类型',
  Object_ID int(10) DEFAULT NULL COMMENT '对象编号',
  Allow_Action_Type varchar(200) DEFAULT NULL COMMENT '权限类型',
  All_Data_Time int(1) DEFAULT NULL COMMENT '无限期',
  Start_Data_Time datetime DEFAULT NULL COMMENT '开始时间',
  End_Data_Time datetime DEFAULT NULL COMMENT '结束时间',
  Create_Time datetime NOT NULL COMMENT '创建时间',
  Creator_ID int(10) NOT NULL COMMENT '创建',
  Update_Time datetime NOT NULL COMMENT '修改时间',
  Updater_ID int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_role_data_authority COMMENT '商户角色数据权限表';

/*Table structure for table merchants_role */
DROP TABLE IF EXISTS merchants_role;
CREATE TABLE merchants_role (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Name varchar(50) NOT NULL COMMENT '名称',
  Code varchar(50) NOT NULL COMMENT '编码',
  All_Data_Authority int(1) DEFAULT NULL COMMENT '是否全部数据权限',
  All_Admin_Authority int(1) DEFAULT NULL COMMENT '是否全部后台权限',
  All_Front_Authority int(1) DEFAULT NULL COMMENT '是否全部前台权限',
  Default_Page_Type int(10) DEFAULT NULL COMMENT '首页地址类型',
  Default_Page_ID int(10) DEFAULT NULL COMMENT '首页地址编号',
  Default_Page_Url varchar(200) DEFAULT NULL COMMENT '首页地址路径',
  Secret_Level int(1) DEFAULT NULL COMMENT '安全等级',
  Memo varchar(100) DEFAULT NULL COMMENT '备注',
  Create_Time datetime NOT NULL COMMENT '创建时间',
  Creator_ID int(10) NOT NULL COMMENT '创建',
  Update_Time datetime NOT NULL COMMENT '修改时间',
  Updater_ID int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_role COMMENT '商户角色表';

/*Table structure for table merchants_user_role */
DROP TABLE IF EXISTS merchants_user_role;
CREATE TABLE merchants_user_role (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Group_ID int(10) NOT NULL COMMENT '用户组编号',
  User_ID int(10) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (ID)
);
ALTER TABLE merchants_user_role COMMENT '商户用户角色表';