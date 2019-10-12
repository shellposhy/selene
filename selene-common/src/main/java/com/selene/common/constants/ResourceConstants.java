package com.selene.common.constants;

/**
 * System basic resources parameters
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface ResourceConstants {
	String /* Resource base path */ RESOURCE_BASE_PATH_KEY = "resource.base.path";

	// Path for images
	String /* Custom images base path */ BASE_PIC_PATH = "/pic";
	String /* Custom temporary images base path */ TEMP_PIC_PATH = "/tmp/pic";

	// Path for attachments
	String /* Custom document base path */ BASE_DOC_PATH = "/doc";
	String /* Custom temporary document base path */ TEMP_DOC_PATH = "/tmp/doc";

	// Path for template
	String /* Custom template base path */ BASE_TEMPLATE_PATH = "/template";
	String /* Custom temporary template base path */ TEMP_TEMPLATE_PATH = "/tmp/template";

	// Path for static
	String /* Custom template base name */ BASE_STATIC_NAME = "static";
	String /* Custom template base path */ BASE_STATIC_PATH = "/static";
}
