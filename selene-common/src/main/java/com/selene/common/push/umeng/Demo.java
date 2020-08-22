package com.selene.common.push.umeng;

import org.json.JSONArray;
import org.json.JSONObject;

import com.selene.common.push.umeng.andriod.Broadcast;
import com.selene.common.push.umeng.andriod.Customizedcast;
import com.selene.common.push.umeng.andriod.Filecast;
import com.selene.common.push.umeng.andriod.Groupcast;
import com.selene.common.push.umeng.andriod.Unicast;
import com.selene.common.push.umeng.andriod.AndroidNotification;

public class Demo {

	private String appkey = "594c6ab9c62dca4e170009d8";
	private String appMasterSecret = "kvslfgwr4t7ky0juad6wyqli9yzfdcoh";
	@SuppressWarnings("unused")
	private String timestamp = null;
	private Pusher client = new Pusher();

	public Demo(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void sendAndroidBroadcast() throws Exception {
		Broadcast broadcast = new Broadcast(appkey, appMasterSecret);
		broadcast.setTicker("Android broadcast ticker");
		broadcast.setTitle("中文的title");
		broadcast.setText("Android broadcast text");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("test", "helloworld");
		client.send(broadcast);
	}

	public void sendAndroidUnicast() throws Exception {
		Unicast unicast = new Unicast(appkey, appMasterSecret);
		// Set your device token
		unicast.setDeviceToken("your device token");
		unicast.setTicker("Android unicast ticker");
		unicast.setTitle("中文的title");
		unicast.setText("Android unicast text");
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}

	public void sendAndroidGroupcast() throws Exception {
		Groupcast groupcast = new Groupcast(appkey, appMasterSecret);
		/*
		 * Construct the filter condition: "where": { "and": [ {"tag":"test"},
		 * {"tag":"Test"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		groupcast.setFilter(filterJson);
		groupcast.setTicker("Android groupcast ticker");
		groupcast.setTitle("中文的title");
		groupcast.setText("Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}

	public void sendAndroidCustomizedcast() throws Exception {
		Customizedcast customizedcast = new Customizedcast(appkey, appMasterSecret);
		// Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidCustomizedcastFile() throws Exception {
		Customizedcast customizedcast = new Customizedcast(appkey, appMasterSecret);
		// Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidFilecast() throws Exception {
		Filecast filecast = new Filecast(appkey, appMasterSecret);
		// upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setTicker("Android filecast ticker");
		filecast.setTitle("中文的title");
		filecast.setText("Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}

	public void sendIOSBroadcast() throws Exception {
		com.selene.common.push.umeng.ios.Broadcast broadcast = new com.selene.common.push.umeng.ios.Broadcast(appkey,
				appMasterSecret);

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge(0);
		broadcast.setSound("default");
		// set 'production_mode' to 'true' if your app is under production
		// mode
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}

	public void sendIOSUnicast() throws Exception {
		com.selene.common.push.umeng.ios.Unicast unicast = new com.selene.common.push.umeng.ios.Unicast(appkey,
				appMasterSecret);
		// Set your device token
		unicast.setDeviceToken("xx");
		unicast.setAlert("IOS 单播测试");
		unicast.setBadge(0);
		unicast.setSound("default");
		// set 'production_mode' to 'true' if your app is under production
		// mode
		unicast.setTestMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}

	public void sendIOSGroupcast() throws Exception {
		com.selene.common.push.umeng.ios.Groupcast groupcast = new com.selene.common.push.umeng.ios.Groupcast(appkey,
				appMasterSecret);
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge(0);
		groupcast.setSound("default");
		// set 'production_mode' to 'true' if your app is under production
		// mode
		groupcast.setTestMode();
		client.send(groupcast);
	}

	public void sendIOSCustomizedcast() throws Exception {
		com.selene.common.push.umeng.ios.Customizedcast customizedcast = new com.selene.common.push.umeng.ios.Customizedcast(
				appkey, appMasterSecret);
		// Set your alias and alias_type here, and use comma to split them
		// if there are multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge(0);
		customizedcast.setSound("default");
		// set 'production_mode' to 'true' if your app is under production
		// mode
		customizedcast.setTestMode();
		client.send(customizedcast);
	}

	public void sendIOSFilecast() throws Exception {
		com.selene.common.push.umeng.ios.Filecast filecast = new com.selene.common.push.umeng.ios.Filecast(appkey,
				appMasterSecret);
		// upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge(0);
		filecast.setSound("default");
		// set 'production_mode' to 'true' if your app is under production
		// mode
		filecast.setTestMode();
		client.send(filecast);
	}

	public static void main(String[] args) {
		// set your appkey and master secret here
		String appkey = "594c6ab9c62dca4e170009d8";
		String appMasterSecret = "kvslfgwr4t7ky0juad6wyqli9yzfdcoh";
		Demo demo = new Demo(appkey, appMasterSecret);
		try {
			// demo.sendAndroidUnicast();
			demo.sendIOSBroadcast();
			// demo.sendIOSUnicast();
			/*
			 * these methods are all available, just fill in some fields and do
			 * the test demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast(); demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast(); demo.sendAndroidFilecast();
			 * 
			 * demo.sendIOSBroadcast(); demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast(); demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
