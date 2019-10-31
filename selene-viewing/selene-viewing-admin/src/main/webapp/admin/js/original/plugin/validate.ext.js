$(document).ready(function() {
	// Extend jquery validate for only letter case-insensitive
	jQuery.validator.addMethod("seleneExtLetter", function(value, element) {
		return this.optional(element) || /^[A-Za-z]+$/.test(value);
	}, "输入内容仅为英文字母，不区分大小写！");
	// Extend jquery validate for only mobile phone
	jQuery.validator.addMethod("seleneExtPhone", function(value, element) {
		return this.optional(element) || /^((0\d{2,3}-\d{7,8})|(1[3456789]\d{9}))$/.test(value);
	}, "输入内容仅为手机号码！");
	// Extend jquery validate for only email
	jQuery.validator.addMethod("seleneExtMail", function(value, element) {
		return this.optional(element) || /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value);
	}, "电子邮箱格式不正确！");
	// Extend jquery validate for only id card
	jQuery.validator.addMethod("seleneExtIDCard", function(value, element) {
		return this.optional(element) || /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
	}, "身份证号码格式不正确！");
	// Extend jquery validate for only bank card no
	jQuery.validator.addMethod("seleneExtBankCard", function(value, element) {
		return this.optional(element) || /^([1-9]{1})(\d{14}|\d{18}|\d{15})$/.test(value);
	}, "银行卡号码格式不正确！");
	// Extend jquery validate for only blank
	jQuery.validator.addMethod("seleneExtBlank", function(value, element) {
		return this.optional(element) || /^\s+$/gi.test(value);
	}, "输入内容存在空格！");
});