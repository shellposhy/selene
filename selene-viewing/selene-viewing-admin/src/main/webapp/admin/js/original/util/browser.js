//Tester browser type
function testBrowser() {
	var ua = navigator.userAgent.toLowerCase();
	if (/msie/i.test(ua) && !/opera/.test(ua)) {
		return "IE";
	} else if (/firefox/i.test(ua)) {
		return "Firefox";
	} else if (/chrome/i.test(ua) && /webkit/i.test(ua) && /mozilla/i.test(ua)) {
		return "Chrome";
	} else if (/opera/i.test(ua)) {
		return "Opera";
	} else if (/webkit/i.test(ua)
			&& !(/chrome/i.test(ua) && /webkit/i.test(ua) && /mozilla/i
					.test(ua))) {
		return "Safari";
	} else {
		return "unKnow";
	}
}