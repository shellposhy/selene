package com.selene.common.push.umeng.andriod;

import org.json.JSONObject;

public class Groupcast extends AndroidNotification {
	public Groupcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
