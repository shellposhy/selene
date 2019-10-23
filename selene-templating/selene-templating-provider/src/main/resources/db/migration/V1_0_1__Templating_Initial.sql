drop table if exists templating_content;

drop table if exists templating_item;

drop table if exists templating_model;

drop table if exists templating_model_bill;

drop table if exists templating_page;

/*==============================================================*/
/* Table: templating_content                                    */
/*==============================================================*/
create table templating_content
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   Page_ID              int(11) unsigned not null comment '页面编号',
   Item_ID              int(11) unsigned not null comment '配置区编码',
   Content_Name         char(200) not null comment '内容标签名称',
   Content_Type         tinyint(2) not null comment '内容类型',
   Base_ID              int(11) unsigned not null comment '内容来源库ID',
   Filter_Condition     varchar(1000) comment '过滤条件',
   List_Format          varchar(500) comment '列表格式',
   primary key (ID)
);

alter table templating_content comment '模板配置区内容表';

/*==============================================================*/
/* Table: templating_item                                       */
/*==============================================================*/
create table templating_item
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   Model_ID             int(11) unsigned not null comment '模板编号',
   Item_Code            char(200) not null comment '配置区编码',
   Item_Name            char(200) comment '配置区名称',
   Item_Type            tinyint(3) not null comment '配置区类型',
   Item_Macro           varchar(500) not null comment '配置区宏',
   Item_Content         varchar(2000) comment '配置区内容',
   Add_Symbol           tinyint(1) comment '是否添加符号',
   Symbol_Position      tinyint(1) comment '符号位置',
   Symbol_Type          tinyint(2) comment '符号类型',
   Line_Size            int unsigned default 5 comment '数据行数',
   Length_Size          int default 20 comment '数据字数',
   Pic_Width            int comment '图片宽度',
   Pic_Height           int comment '图片高度',
   primary key (ID)
);

alter table templating_item comment '模板配置区表';

/*==============================================================*/
/* Table: templating_model                                      */
/*==============================================================*/
create table templating_model
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   License              char(50) not null comment '许可证',
   Bill_ID              int(11) unsigned not null comment '目录编号',
   Model_Name           char(200) not null comment '模板名称',
   Model_Code           char(200) not null comment '模板编码',
   Model_Type           tinyint(3) not null comment '模板类型',
   Model_File           varchar(500) comment '模板文件',
   Order_ID             int(11) unsigned comment '顺序编号',
   Creator_ID           int(11) unsigned not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) unsigned not null comment '更新',
   Update_Time          datetime not null comment '更新时间',
   primary key (ID),
   unique key AK_UQ_Model_Code (Model_Code)
);

alter table templating_model comment '模板表';

/*==============================================================*/
/* Table: templating_model_bill                                 */
/*==============================================================*/
create table templating_model_bill
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   License              char(50) not null comment '许可证',
   Parent_ID            int(11) unsigned not null comment '上一级编号',
   Bill_Type            tinyint(2) not null comment '目录类型',
   Bill_Name            char(200) not null comment '目录名称',
   Bill_Code            char(100) not null comment '目录编码',
   Path_Code            char(250) not null comment '目录路径',
   Order_ID             int(11) unsigned comment '顺序编号',
   Creator_ID           int(11) unsigned not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) unsigned not null comment '更新',
   Update_Time          datetime not null comment '更新时间',
   primary key (ID),
   unique key AK_UQ_Path_Code (Path_Code)
);

alter table templating_model_bill comment '模板目录表';

/*==============================================================*/
/* Table: templating_page                                       */
/*==============================================================*/
create table templating_page
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   License              char(50) not null comment '许可证',
   Parent_ID            int(11) unsigned not null comment '上一级编号',
   Page_Model_ID        int unsigned not null comment '页面模板编号',
   Page_Name            char(200) not null comment '页面名称',
   Page_Code            char(200) not null comment '页面编码',
   Page_Type            tinyint(2) not null comment '页面类型',
   Status               tinyint(1) not null comment '上架状态',
   Page_Status          tinyint(2) not null comment '页面发布状态',
   Page_Html_Path       varchar(500) comment '发布页面保存路径',
   Publish_Time         datetime comment '发布时间',
   Creator_ID           int(11) unsigned not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) unsigned not null comment '更新',
   Update_Time          datetime not null comment '更新时间',
   primary key (ID),
   unique key AK_UQ_Page_Code (Page_Code)
);

alter table templating_page comment '页面模板表';
