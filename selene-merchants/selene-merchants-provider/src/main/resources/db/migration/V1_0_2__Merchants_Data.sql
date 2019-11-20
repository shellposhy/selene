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

/* index center  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('1','首页','Index','0','0','/admin','menu_home','1');

/* users center  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('10','个人中心','Content_Manage','0','0','#','menu_center','100');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('11','我的任务','My_Task','10','0','#',NULL,'101');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('12','用户数据','User_Data','10','0','#',NULL,'102');

/* pages management  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('20','页面管理','Page_Manage','0','0','#','menu_page','200');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('21','页面发布','Page_Set','20','0','/admin/templating/page,/admin/templating/page/publish,/admin/templating/page/(\\d+)/new,/admin/templating/page/model/(\\d+),/admin/templating/page/search/(\\d+),/admin/templating/page/tree,/admin/templating/page/save,/admin/templating/page/config/(\\d+),/admin/templating/page/config/preview/(\\d+),/admin/templating/page/config/filter/(\\w+),/admin/templating/page/config/(\\d+)/(\\w+),/admin/templating/page/config/(\\d+)/save',NULL,'201');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('22','模板配置','Page_Model','20','0','/admin/templating/model,/admin/templating/model/(\\d+)/template,/admin/templating/model/scan/(\\d+),/admin/templating/model/(\\d+)/template/file,/admin/templating/model/template/file/read,/admin/templating/model/(\\d+)/new,/admin/templating/model/(\\d+)/edit,/admin/templating/model/(\\d+)/delete,/admin/templating/model/save,/admin/templating/model/find/(\\d+),/admin/templating/model/bill/(\\d+)/new,/admin/templating/model/bill/(\\d+)/edit,/admin/templating/model/bill/save,/admin/templating/model/bill/tree',NULL,'202');

/* searching management  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('30','数据查询','Data_Search','0','0','#','menu_search','300');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('31','快捷查询','Quick_Search','30','0','/admin/searching/quick,/admin/searching,/admin/searching/s',NULL,'301');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('32','高级查询','Advanced_Search','30','0','/admin/searching/advance,/admin/searching/field,/admin/searching,/admin/searching/presearch,/admin/searching/s',NULL,'302');

/* datas management  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('40','数据管理','Data_Manage','0','0','#','menu_db','400');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('41','数据模板管理','Model_Manage','40','0','/admin/dataing/model,/admin/dataing/model/s,/admin/dataing/model/new,/admin/dataing/model/(\\d+)/edit,/admin/dataing/model/save,/admin/dataing/model/delete',NULL,'401');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('42','数据库管理','Database_Manage','40','0','/admin/dataing/library,/admin/dataing/directory/tree,/admin/dataing/directory/(\\d+)/new,/admin/dataing/directory/(\\d+)/edit,/admin/dataing/directory/save,/admin/dataing/library/find/(\\d+),/admin/dataing/library/show/(\\d+),/admin/dataing/library/(\\d+)/new,/admin/dataing/library/(\\d+)/edit,/admin/dataing/library/search,/admin/dataing/library/save,/admin/dataing/library/data/new/(\\d+),/admin/dataing/library/data/head/(\\d+),/admin/dataing/library/data/search/(\\d+),/admin/dataing/library/data/save',NULL,'402');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('43','数据标签管理','Tag_Manage','40','0','/admin/dataing/tag,/admin/dataing/tag/s,/admin/dataing/tag/custom/s,/admin/dataing/tag/(\\d+)/new,/admin/dataing/tag/(\\d+)/edit,/admin/dataing/tag/(\\d+)/delete,/admin/dataing/tag/save',NULL,'403');

/* merchants management  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('50','商户管理','Merchants_Manage','0','0','#','menu_user','500');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('51','商户查询','All_User','50','0','/admin/merchants,/admin/merchants/org/s,/admin/merchants/user/(\\d+)/s',NULL,'501');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('52','机构管理','Org_Manage','50','0','/admin/merchants/org,/admin/merchants/org/s,/admin/merchants/user/(\\d+)/s,/admin/merchants/org/(\\d+)/new,/admin/merchants/org/(\\d+)/edit,/admin/merchants/org/(\\d+)/delete,/admin/merchants/org/save',NULL,'502');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('53','用户管理','User_Manage','50','0','/admin/merchants/user,/admin/merchants/org/s,/admin/merchants/user/(\\d+)/s,/admin/merchants/user/(\\d+)/new,/admin/merchants/user/(\\d+)/edit,/admin/merchants/user/delete,/admin/merchants/user/save',NULL,'503');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('54','角色管理','Role_Manage','50','0','/admin/merchants/role,/admin/merchants/role/s,/admin/merchants/role/new,/admin/merchants/role/(\\d+)/edit,/admin/merchants/role/save,/admin/merchants/role/action/s,/admin/merchants/role/delete',NULL,'504');

/* loging management  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('60','日志报告','Log_Manage','0','0','#','menu_count','600');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('61','后台日志','Admin_Log','60','0','#',NULL,'601');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('62','前台日志','Web_Log','60','0','#',NULL,'602');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('63','日志统计','Count_Log','60','0','#',NULL,'603');

/* system management  */
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('70','系统设置','System_Manage','0','0','#','menu_sys','700');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('71','字段管理','Field_Manage','70','0','#',NULL,'701');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('72','系统任务','System_Task','70','0','#',NULL,'702');
INSERT INTO 
	merchants_action (ID, Name, Code, Parent_ID, Type, Uri, Icon_Skin, Order_ID) 
VALUES('73','系统参数','System_Param','70','0','#',NULL,'703');

COMMIT;