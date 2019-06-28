package com.selene.common;

/**
 * Enumeration of HTTP status codes.
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum HttpStatus {
	ERROR(100, "Error"), OK(200, "OK"), FORBIDDEN(403, "Forbidden"), NOT_FOUND(404, "Not Found"), INTERNAL_SERVER_ERROR(
			500,
			"Internal Server Error"), BAD_GATEWAY(502, "Bad Gateway"), SERVICE_UNAVAILABLE(503, "Service Unavailable");
	private final int code;

	private final String message;

	HttpStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}
}
