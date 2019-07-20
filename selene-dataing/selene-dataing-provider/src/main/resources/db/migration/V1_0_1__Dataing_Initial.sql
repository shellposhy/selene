drop table if exists data_base;

drop table if exists data_base_field_map;

drop table if exists data_field;

drop table if exists data_model;

drop table if exists data_model_field_map;

drop table if exists data_table;

drop table if exists data_tag;

drop table if exists data_task;

drop table if exists data_task_sub;

/*==============================================================*/
/* Table: data_base                                             */
/*==============================================================*/
create table data_base
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   License              char(50) not null comment '许可证',
   Name                 char(100) not null comment '名称',
   Code                 char(200) not null comment '编码',
   Path_Code            varchar(200) comment '路径',
   Type                 tinyint(1) not null comment '类型',
   Node_Type            tinyint(2) not null comment '库类型',
   Parent_ID            int(11) not null comment '父节点',
   Model_ID             int(10) not null comment '模板编号',
   Subject_Type         tinyint(2) comment '专题类型',
   Subject_ID           int(10) comment '专题编号',
   Tables               tinyint(2) unsigned not null default 0 comment '数据表数量',
   Status               tinyint(1) comment '状态',
   Order_ID             int(11) comment '排序编号',
   Task_ID              int(11) comment '事务编号',
   Data_Update_Time     datetime comment '数据更新时间',
   Creator_ID           int(11) not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) not null comment '修改',
   Update_Time          datetime not null comment '修改时间',
   primary key (ID)
);

alter table data_base comment '数据库';

/*==============================================================*/
/* Table: data_base_field_map                                   */
/*==============================================================*/
create table data_base_field_map
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   Base_ID              int(11) not null comment '数据库编号',
   Field_ID             int(11) not null comment '字段编号',
   Type                 tinyint(2) not null comment '类型',
   Is_Display           tinyint(3) unsigned zerofill comment '是否可用于列表显示',
   primary key (ID)
);

alter table data_base_field_map comment '数据库字段映射表';

/*==============================================================*/
/* Table: data_field                                            */
/*==============================================================*/
create table data_field
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   Component_ID         int(10) comment '组件编号',
   Name                 char(100)  not null comment '名称',
   Code                 char(50) not null comment '编码',
   Code_Name            char(50) not null comment '编码名称',
   Data_Type            int(3) not null comment '数据类型',
   Nosg                 tinyint(1) not null comment '是否无符号',
   Leng                 int(10) comment '长度',
   Prec                 int(5) comment '准确度',
   Mand                 tinyint(1) not null comment '是否必填',
   Uniq                 tinyint(1) not null comment '是否唯一',
   Multi_Value          tinyint(1) not null comment '是否多值',
   Use_Enum             tinyint(1) not null comment '是否枚举',
   Index_Type           int(3) not null comment '索引类型',
   Index_Store          tinyint(1) not null comment '是否存储索引',
   Required             tinyint(1) not null comment '是否必须',
   Type                 int(3) not null comment '类型',
   Order_ID             int(10) not null comment '排序编号',
   Access_Type          int(3) not null comment '操作类型',
   For_Display          tinyint(1) not null comment '是否可用于显示',
   For_Order            tinyint(1) not null comment '是否排序',
   Memo                 char(200) comment '备注',
   primary key (ID)
);

alter table data_field comment '数据库字段表';

/*==============================================================*/
/* Table: data_model                                            */
/*==============================================================*/
create table data_model
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   License              char(50) comment '许可证',
   Model_Name           char(200) not null comment '名称',
   Model_Code           char(50) not null comment '编码',
   Model_Type           tinyint(1) not null comment '类型',
   Fields_Name          varchar(1000) comment '描述',
   For_System           tinyint(1) not null comment '是否可维护',
   Task_ID              int(11) comment '事务编号',
   Memo                 char(200) comment '备注',
   Creator_ID           int(11) not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) not null comment '修改',
   Update_Time          datetime not null comment '修改时间',
   primary key (ID)
);

alter table data_model comment '数据库模板表';

/*==============================================================*/
/* Table: data_model_field_map                                  */
/*==============================================================*/
create table data_model_field_map
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   Model_ID             int(11) not null comment '模板编号',
   Field_ID             int(11) not null comment '字段编号',
   primary key (ID)
);

alter table data_model_field_map comment '数据库模板字段表';

/*==============================================================*/
/* Table: data_table                                            */
/*==============================================================*/
create table data_table
(
   ID                   int(11) not null auto_increment comment '主键',
   Base_ID              int(11) not null comment '数据库编号',
   Name                 char(50) not null comment '数据表名',
   Row_Count            int(11) unsigned default 0 comment '数据量',
   primary key (ID)
);

alter table data_table comment '数据表格表';

/*==============================================================*/
/* Table: data_tag                                              */
/*==============================================================*/
create table data_tag
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   License              char(50) not null comment '许可证',
   Name                 char(50) not null comment '名称',
   Type                 tinyint(2) not null comment '类型',
   Code                 char(50) not null comment '编码',
   Path_Code            char(255) not null comment '层级路径',
   Parent_ID            int(11) not null comment '父节点',
   Order_ID             int(11) not null comment '排序编号',
   Status               int(1) not null comment '状态',
   Creator_ID           int(11) not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) not null comment '修改',
   Update_Time          datetime not null comment '修改时间',
   primary key (ID)
);

alter table data_tag comment '数据标签表';

/*==============================================================*/
/* Table: data_task                                             */
/*==============================================================*/
create table data_task
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   Task_Name            char(255) not null comment '事务名称',
   Task_Code            char(50) not null comment '事务编码',
   Task_Type            tinyint(2) not null comment '事务类型',
   Task_Info            varchar(500) comment '事务描述',
   Task_Target          varchar(2000) comment '事务目标',
   Task_Status          tinyint(1) not null comment '事务状态',
   Task_Progress        int(3) not null default 0 comment '事务进度',
   Task_Memo            char(255) comment '备注',
   Creator_ID           int(11) not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) comment '修改',
   Update_Time          datetime comment '修改时间',
   primary key (ID)
);

alter table data_task comment '数据事务表';

/*==============================================================*/
/* Table: data_task_sub                                         */
/*==============================================================*/
create table data_task_sub
(
   ID                   int(11) unsigned not null auto_increment comment '主键',
   Task_ID              int(11) not null comment '事务编号',
   Subtask_Name         char(255) not null comment '子任务名称',
   Subtask_Type         tinyint(2) not null comment '子任务类型',
   Subtask_Target       varchar(2000) comment '子任务目标',
   Subtask_Status       tinyint(1) not null default 0 comment '子任务状态',
   Subtask_Progress     int(3) not null default 0 comment '子任务进度',
   Subtask_Memo         char(255) comment '子任务备注',
   Creator_ID           int(11) not null comment '创建',
   Create_Time          datetime not null comment '创建时间',
   Updater_ID           int(11) not null comment '修改',
   Update_Time          datetime comment '修改时间',
   primary key (ID)
);

alter table data_task_sub comment '数据事务子任务表';
