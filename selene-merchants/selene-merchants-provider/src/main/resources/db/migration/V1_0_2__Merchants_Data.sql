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

COMMIT;