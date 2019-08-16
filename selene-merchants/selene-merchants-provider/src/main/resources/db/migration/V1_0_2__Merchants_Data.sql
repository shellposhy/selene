/* Merchants_org init data  */

BEGIN;


-- Records of merchants_role
INSERT INTO 
	merchants_role (
		ID, License, Name, Code, All_Data_Authority, All_Admin_Authority, All_Front_Authority, Default_Page_Type, 
		Default_Page_ID, Default_Page_Url, Secret_Level, Memo, Create_Time, Creator_ID, Update_Time, Updater_ID) 
VALUES('1','14c37c59848cd3e28b57209ec3269368','全部权限','all','1','1','1','0','0',NULL,'0','全部权限','2019-06-22 20:30:11','1','2019-06-22 20:30:18','1');

INSERT INTO 
	merchants_user (
		ID, Type, User_Type, Name, Real_Name, Nick_Name, User_Password, Org_ID, Sex, Order_ID, IP_Address, 
		ID_Card, Phone_Number, Email, Position, Pic, Status, Create_Time, Creator_ID, Update_Time, Updater_ID) 
VALUES('1','0','0','sa','sa','sa','c12e01f2a13ff5587e1e9e4aedb8242d','1','0','1',NULL,NULL,NULL,NULL,NULL,NULL,'1','2019-06-22 20:57:12','1','2019-06-22 20:57:16','1');

INSERT INTO merchants_user_role (ID, Group_ID, User_ID) VALUES('1','1','1');

INSERT INTO 
	merchants_org (
		ID, Super_Status, License, Org_Type, Name, Code, Parent_ID, Order_ID, 
		Status, Inherit, Create_Time, Creator_ID, Update_Time, Updater_ID) 
VALUES('1','1','14c37c59848cd3e28b57209ec3269368','4','南山智慧谷（北京）科技有限公司','nszhg','0','1','1','1','2019-06-22 20:38:14','1','2019-06-22 20:38:17','1');

INSERT INTO merchants_org_role (ID, Group_ID, Org_ID) VALUES('1','1','1');

INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('1','首页','Index','0','0','/admin','menu_home','100');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('10','个人中心','Content_Manage','0','0','#','menu_center','200');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('11','我的任务','My_Task','10','0','#',NULL,'201');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('12','用户数据','User_Data','10','0','#',NULL,'202');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('20','页面管理','Page_Manage','0','0','#','menu_page','300');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('21','页面发布','Page_Set','20','0','/admin/view/page,/admin/view/page/s,/admin/view/page/publish,/admin/view/page/new,/admin/view/page/model/(\\d+),/admin/view/page/delete,/admin/view/page/config/(\\d+),/admin/view/page/config/preview/(\\d+),/admin/view/page/config/(\\d+)/saveItem',NULL,'301');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('22','模板配置','Page_Model','20','0','/admin/view/model,/admin/view/model/s,/admin/view/model/(\\d+)/fileTree,/admin/view/model/directory/tree,/admin/view/model/find/(\\d+),/admin/view/model/directory/new/(\\d+),/admin/view/model/directory/(\\d+)/edit,/admin/view/model/directory/(\\d+)/delete,/admin/view/model/directory/save,/admin/view/model/(\\d+)/readFiles,/admin/view/model/import/(\\d+),/admin/view/model/path/readFile,/admin/view/model/saveFile,/admin/view/model/edit/(\\d+),/admin/view/model/new/(\\d+),/admin/view/model/delete/(\\d+),/admin/view/model/scan/(\\d+),/admin/view/model/(\\d+)/fileTree,/admin/view/model/contentHtml/(\\d+),/admin/view/content/(\\d+)/(\\d+)',NULL,'302');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('50','数据查询','Data_Search','0','0','#','menu_search','400');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('51','快捷查询','Quick_Search','50','0','/admin/data/qs,/admin/data,/admin/data/s',NULL,'401');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('52','高级查询','Advanced_Search','50','0','/admin/data/as,/admin/data,/admin/data/s,/admin/data/(\\d+)/as,/admin/data/field',NULL,'402');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('60','数据管理','Data_Manage','0','0','#','menu_db','600');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('61','数据模板管理','Model_Manage','60','0','/admin/dataing/model,/admin/dataing/model/s,/admin/dataing/model/new,/admin/dataing/model/(\\d+)/edit,/admin/dataing/model/save,/admin/dataing/model/delete',NULL,'601');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('62','数据库管理','Database_Manage','60','0','/admin/dataing/library,/admin/dataing/directory/tree,/admin/dataing/directory/(\\d+)/new,/admin/dataing/directory/(\\d+)/edit,/admin/dataing/directory/save,/admin/dataing/library/find/(\\d+),/admin/dataing/library/show/(\\d+),/admin/dataing/library/(\\d+)/new,/admin/dataing/library/(\\d+)/edit,/admin/dataing/library/search,/admin/dataing/library/save,/admin/dataing/library/data/new/(\\d+),/admin/dataing/library/data/head/(\\d+),/admin/dataing/library/data/search/(\\d+)',NULL,'602');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('63','数据标签管理','Tag_Manage','60','0','/admin/dataing/tag',NULL,'603');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('70','商户管理','Merchants_Manage','0','0','#','menu_user','700');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('71','商户查询','All_User','70','0','/admin/merchants,/admin/merchants/org/s,/admin/merchants/user/(\\d+)/s',NULL,'701');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('72','机构管理','Org_Manage','70','0','/admin/merchants/org,/admin/merchants/org/s,/admin/merchants/user/(\\d+)/s,/admin/merchants/org/(\\d+)/new,/admin/merchants/org/(\\d+)/edit,/admin/merchants/org/(\\d+)/delete,/admin/merchants/org/save',NULL,'702');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('73','用户管理','User_Manage','70','0','/admin/merchants/user,/admin/merchants/org/s,/admin/merchants/user/(\\d+)/s,/admin/merchants/user/(\\d+)/new,/admin/merchants/user/(\\d+)/edit,/admin/merchants/user/delete,/admin/merchants/user/save',NULL,'703');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('74','角色管理','Role_Manage','70','0','/admin/merchants/role,/admin/merchants/role/s,/admin/merchants/role/new,/admin/merchants/role/(\\d+)/edit,/admin/merchants/role/save,/admin/merchants/role/action/s,/admin/merchants/role/delete',NULL,'704');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('80','用户组管理','Group_Manage','0','0','#','menu_users','700');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('81','所有用户组','All_Group','80','0','/admin/userGroup,/admin/userGroup/s,/admin/userGroup/(\\d+)/edit,/admin/userGroup,/admin/userGroup/new,/admin/userGroup/delete',NULL,'701');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('90','日志报告','Log_Manage','0','0','#','menu_count','800');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('91','后台日志','Admin_Log','90','0','/admin/log/1/list,/admin/log/(\\d+)/s',NULL,'801');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('92','前台日志','Web_Log','90','0','/admin/log/0/list,/admin/log/(\\d+)/s',NULL,'802');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('93','日志统计','Count_Log','90','0','/admin/report,/admin/report/list,/admin/report/chart',NULL,'803');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('100','系统设置','System_Manage','0','0','#','menu_sys','900');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('101','字段管理','Field_Manage','100','0','/admin/data/field,/admin/data/field/s',NULL,'901');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('102','系统任务','System_Task','100','0','/admin/task,/admin/task/s',NULL,'902');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('103','系统参数','System_Param','100','0','/admin/param,/admin/param/s,/admin/param/new,/admin/param/(\\d+)/edit',NULL,'903');

COMMIT;