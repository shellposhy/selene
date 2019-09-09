package com.selene.viewing.admin;

import java.io.IOException;

public class FileTest {
	public static void main(String[] args) throws IOException {
		// String source = "C:\\Users\\Administrator\\Desktop\\pic";
		String path = "/tmp/pic/edd334/test/233222.jpg";
		String relativeSource = "/tmp/pic/edd334";
		String relativeTarget = "/pic/1/2019/9";
		boolean containOldPath = true;

		int length = 0;
		if (/* Match user uploaded image */path.startsWith(relativeSource)) {
			if (/* Contain old path */containOldPath) {
				length = relativeSource.length();
			} else /* Not contain old path */ {
				length = path.lastIndexOf("/");
			}
			System.out.println(path.replaceAll(path, (relativeTarget.endsWith("/"))
					? relativeTarget + path.substring(length + 1) : relativeTarget + path.substring(length)));
		}
	}
}
