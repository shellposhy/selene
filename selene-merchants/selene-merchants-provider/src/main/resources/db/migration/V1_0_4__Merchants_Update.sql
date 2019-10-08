/* Merchants_Action update data  */

BEGIN;


-- Records of update
UPDATE merchants_action SET Uri='/admin/searching/quick,/admin/searching,/admin/searching/s' WHERE ID=51;

UPDATE merchants_action SET Uri='/admin/searching/advance,/admin/searching/field,/admin/searching,/admin/searching/presearch,/admin/searching/s' WHERE ID=52;

UPDATE merchants_action SET Uri='/admin/templating/page' WHERE ID=21;

UPDATE merchants_action SET Uri='/admin/templating/model' WHERE ID=22;

COMMIT;