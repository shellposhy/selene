package com.selene.common;

import com.selene.common.util.Chineses;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class Test {

	public static void main(String[] args) {
		System.out.println(Chineses.lower("中国社会扶贫网"));

		for (int i = 0; i < 100; i++) {
			HttpRequest request = HttpRequest.get("https://baike.baidu.com/item/%E6%9D%A8%E4%B8%9C%E5%8D%87/23649489");
			HttpResponse response = request.send();
			System.out.println(response.getHttpRequest().bodyText());
		}
	}
}
