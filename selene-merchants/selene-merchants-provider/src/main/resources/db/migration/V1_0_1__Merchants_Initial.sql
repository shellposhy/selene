drop table if exists merchants_action;

drop table if exists merchants_login_token;

drop table if exists merchants_org;

drop table if exists merchants_org_role;

drop table if exists merchants_role;

drop table if exists merchants_role_action;

drop table if exists merchants_role_data_authority;

drop table if exists merchants_user;

drop table if exists merchants_user_role;

/*==============================================================*/
/* Table: merchants_action                                      */
/*==============================================================*/
create table merchants_action
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   Name                 char(50) not null comment '名称',
   Code                 char(50) not null comment '编码',
   Parent_ID            int(11) not null comment '父节点',
   Type                 int(1) not null comment '类型(0-后台1前台)',
   Uri                  varchar(2000) not null comment '路径',
   Icon_Skin            char(200) comment '图标',
   Order_ID             int(11) not null comment '排序',
   primary key (ID)
);

alter table merchants_action comment '商户用户权限表';

/*==============================================================*/
/* Table: merchants_login_token                                 */
/*==============================================================*/
create table merchants_login_token
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   User_ID              int(11) not null comment '商户用户编号',
   Login_Time           datetime not null comment '登录时间',
   Redis_Key            char(100) not null comment 'RedisKey',
   Token                char(100) not null comment 'Token',
   Refresh_Token        char(200) not null comment 'RefreshToken',
   Secret_Key           char(200) not null comment '秘钥',
   Jwt                  varchar(2000) not null comment 'jwt',
   primary key (ID)
);

alter table merchants_login_token comment '商户登录表';

/*==============================================================*/
/* Table: merchants_org                                         */
/*==============================================================*/
create table merchants_org
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   Super_Status         int(1) not null default 0 comment '超级机构',
   License              char(50) not null comment '许可证',
   Org_Type             int(4) not null comment '机构类型',
   Name                 char(50) not null comment '名称',
   Code                 char(50) not null comment '编码',
   Parent_ID            int(11) not null comment '父节点',
   Order_ID             int(11) comment '排序编号',
   Status               int(1) not null comment '状态',
   Inherit              int(1) comment '继承',
   Create_Time          datetime not null comment '创建时间',
   Creator_ID           int(11) not null comment '创建',
   Update_Time          datetime not null comment '修改时间',
   Updater_ID           int(11) not null comment '修改',
   primary key (ID)
);

alter table merchants_org comment '商户机构表';

/*==============================================================*/
/* Table: merchants_org_role                                    */
/*==============================================================*/
create table merchants_org_role
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   Group_ID             int(11) not null comment '用户组编号',
   Org_ID               int(11) not null comment '机构编号',
   primary key (ID)
);

alter table merchants_org_role comment '商户机构角色表';

/*==============================================================*/
/* Table: merchants_role                                        */
/*==============================================================*/
create table merchants_role
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   License              char(50) not null comment '企业License',
   Name                 char(50) not null comment '名称',
   Code                 char(50) not null comment '编码',
   All_Data_Authority   int(1) not null default 0 comment '是否全部数据权限',
   All_Admin_Authority  int(1) not null default 0 comment '是否全部后台权限',
   All_Front_Authority  int(1) not null default 0 comment '是否全部前台权限',
   Default_Page_Type    int(10) comment '首页地址类型',
   Default_Page_ID      int(10) comment '首页地址编号',
   Default_Page_Url     char(200) comment '首页地址路径',
   Secret_Level         int(1) comment '安全等级',
   Memo                 char(200) comment '备注',
   Create_Time          datetime not null comment '创建时间',
   Creator_ID           int(11) not null comment '创建',
   Update_Time          datetime not null comment '修改时间',
   Updater_ID           int(11) not null comment '修改',
   primary key (ID)
);

alter table merchants_role comment '商户角色表';

/*==============================================================*/
/* Table: merchants_role_action                                 */
/*==============================================================*/
create table merchants_role_action
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   Group_ID             int(11) not null comment '用户组编号',
   Action_ID            int(11) not null comment '权限编号',
   Type                 int(1) not null comment '类型(0后台1前台)',
   primary key (ID)
);

alter table merchants_role_action comment '商户角色权限表';

/*==============================================================*/
/* Table: merchants_role_data_authority                         */
/*==============================================================*/
create table merchants_role_data_authority
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   Group_ID             int(11) not null comment '用户组编号',
   Object_Type          int(3) not null comment '对象类型',
   Object_ID            int(11) not null comment '对象编号',
   Allow_Action_Type    char(200) not null comment '权限类型',
   All_Data_Time        int(1) not null default 0 comment '无限期',
   Start_Data_Time      datetime comment '开始时间',
   End_Data_Time        datetime comment '结束时间',
   Create_Time          datetime not null comment '创建时间',
   Creator_ID           int(11) not null comment '创建',
   Update_Time          datetime not null comment '修改时间',
   Updater_ID           int(11) not null comment '修改',
   primary key (ID)
);

alter table merchants_role_data_authority comment '商户角色数据权限表';

/*==============================================================*/
/* Table: merchants_user                                        */
/*==============================================================*/
create table merchants_user
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   Type                 int(4) not null comment '类型',
   User_Type            int(4) not null comment '账户类型',
   Name                 char(50) not null comment '账户',
   Real_Name            char(50) comment '真实姓名',
   Nick_Name            char(50) not null comment '昵称',
   User_Password        char(50) not null comment '密码',
   Org_ID               int(11) not null comment '机构编号',
   Sex                  int(1) comment '性别',
   Order_ID             int(11) not null comment '排序编号',
   IP_Address           char(100) comment 'IP',
   ID_Card              char(20) comment '身份证号',
   Phone_Number         char(20) comment '手机号码',
   Email                char(200) comment '电子邮件',
   Position             char(100) comment '区域',
   Pic                  char(200) comment '头像',
   Status               int(1) not null comment '状态',
   Create_Time          datetime not null comment '创建时间',
   Creator_ID           int(11) not null comment '创建',
   Update_Time          datetime not null comment '修改时间',
   Updater_ID           int(11) not null comment '修改',
   primary key (ID)
);

alter table merchants_user comment '商户用户表';

/*==============================================================*/
/* Table: merchants_user_role                                   */
/*==============================================================*/
create table merchants_user_role
(
   ID                   int(11) unsigned not null auto_increment comment '编号',
   Group_ID             int(11) not null comment '用户组编号',
   User_ID              int(11) not null comment '用户编号',
   primary key (ID)
);

alter table merchants_user_role comment '商户用户角色表';
