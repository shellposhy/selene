/*Table structure for table data_base */
DROP TABLE IF EXISTS data_base;
CREATE TABLE data_base (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Name varchar(50) NOT NULL COMMENT '名称',
  Code varchar(50) NOT NULL COMMENT '编码',
  Path_Code varchar(200) NOT NULL COMMENT '路径',
  Type int(11) NOT NULL COMMENT '类别',
  Node_Type int(11) NOT NULL COMMENT '数据库类别',
  Parent_ID int(11) NOT NULL COMMENT '父节点',
  Model_ID int(11) NOT NULL COMMENT '模板编号',
  Tables int(11) unsigned DEFAULT '0' COMMENT '数据表数量',
  Status int(11) DEFAULT NULL COMMENT '状态',
  Order_ID int(11) DEFAULT NULL COMMENT '排序编号',
  Task_ID int(11) DEFAULT NULL COMMENT '事务编号',
  Data_Update_Time datetime NOT NULL COMMENT '数据更新时间',
  Create_Time datetime NOT NULL COMMENT '创建时间',
  Creator_ID int(11) NOT NULL COMMENT '创建',
  Update_Time datetime NOT NULL COMMENT '修改时间',
  Updater_ID int(11) NOT NULL COMMENT '修改',
  PRIMARY KEY (ID)
);
ALTER TABLE data_base COMMENT '数据库';

/*Table structure for table data_base_field_map */
DROP TABLE IF EXISTS data_base_field_map;
CREATE TABLE data_base_field_map (
  ID int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  Base_ID int(10) unsigned NOT NULL COMMENT '库ID',
  Field_ID int(10) unsigned NOT NULL COMMENT '字段ID',
  Type tinyint(3) unsigned NOT NULL COMMENT '类型',
  Is_Display tinyint(3) unsigned zerofill NOT NULL COMMENT '是否可用于列表显示',
  PRIMARY KEY (ID)
);
ALTER TABLE data_base_field_map COMMENT '库字段关系';

/*Table structure for table data_field */
DROP TABLE IF EXISTS data_field;
CREATE TABLE data_field (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Name varchar(50) NOT NULL COMMENT '名称',
  Code varchar(50) NOT NULL COMMENT '编码',
  Code_Name varchar(50) NOT NULL COMMENT '编码名称',
  Data_Type int(3) NOT NULL COMMENT '数据类型',
  Nosg int(1) NOT NULL COMMENT '是否无符号',
  Leng int(10) DEFAULT NULL COMMENT '长度',
  Prec int(10) DEFAULT NULL COMMENT '准确度',
  Mand int(1) NOT NULL COMMENT '是否必填',
  Uniq int(1) NOT NULL COMMENT '是否唯一',
  Multi_Value int(1) NOT NULL COMMENT '是否多值',
  Use_Enum int(1) NOT NULL COMMENT '是否使用枚举',
  Index_Type int(3) NOT NULL COMMENT '索引类型',
  Index_Store int(1) NOT NULL COMMENT '是否存储索引',
  Required int(1) NOT NULL COMMENT '是否必须',
  Type int(1) NOT NULL COMMENT '类型',
  Order_ID int(10) NOT NULL COMMENT '排序编号',
  Access_Type int(3) NOT NULL COMMENT '操作类型',
  For_Display tinyint(1) unsigned NOT NULL COMMENT '是否可用于显示',
  Component_ID int(10) NOT NULL COMMENT '组件编号',
  For_Order int(1) NOT NULL COMMENT '是否排序',
  Memo varchar(100) DEFAULT NULL COMMENT '备注',
  Create_Time datetime NOT NULL COMMENT '创建时间',
  Creator_ID int(10) NOT NULL COMMENT '创建',
  Update_Time datetime NOT NULL COMMENT '修改时间',
  Updater_ID int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (ID)
);
ALTER TABLE data_field COMMENT '数据字段表';

/*Table structure for table data_table */
DROP TABLE IF EXISTS data_table;
CREATE TABLE data_table (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  Base_ID int(10) NOT NULL COMMENT '数据库编号',
  Name varchar(50) NOT NULL COMMENT '数据表名',
  Row_Count int(10) DEFAULT '0' COMMENT '数据量',
  PRIMARY KEY (ID)
);
ALTER TABLE data_table COMMENT '数据表格表';

/*Table structure for table data_sort */
DROP TABLE IF EXISTS data_sort;
CREATE TABLE data_sort (
  ID int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  Name varchar(50) NOT NULL COMMENT '名称',
  Code varchar(50) NOT NULL COMMENT '编码',
  Type int(11) DEFAULT NULL COMMENT '类型',
  Path_Code varchar(200) NOT NULL COMMENT '层级路径编码',
  Base_ID int(11) NOT NULL COMMENT '库ID',
  Parent_ID int(11) NOT NULL COMMENT '父ID',
  For_Sys tinyint(1) NOT NULL COMMENT '是否系统专用',
  Order_ID int(11) NOT NULL COMMENT '排序ID',
  Status tinyint(1) NOT NULL COMMENT '状态',
  Create_Time datetime NOT NULL COMMENT '创建时间',
  Creator_ID int(11) NOT NULL COMMENT '创建者',
  Update_Time datetime NOT NULL COMMENT '修改时间',
  Updater_ID int(11) NOT NULL COMMENT '修改者',
  PRIMARY KEY (id)
);
ALTER TABLE data_sort COMMENT '数据分类';