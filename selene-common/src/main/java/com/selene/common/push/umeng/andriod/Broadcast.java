package com.selene.common.push.umeng.andriod;

public class Broadcast extends AndroidNotification {
	public Broadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
	}
}
