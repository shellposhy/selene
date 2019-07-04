package com.selene.common.token.login;

import java.util.Date;
import java.util.Map;

import com.selene.common.token.support.AbstractToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Token processing for user login
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class LoginToken extends AbstractToken {

	public String encrypt(String appKey, String jti, String iss) {
		return super.encrypt(appKey, jti, iss, null, null, null, null, null, null, SignatureAlgorithm.HS256);
	}

	public String encrypt(String appKey, String jti, String iss, Date exp) {
		return super.encrypt(appKey, jti, iss, null, null, exp, null, null, null, SignatureAlgorithm.HS256);
	}

	public String encrypt(String appKey, String jti, String iss, String sub, Date exp) {
		return super.encrypt(appKey, jti, iss, sub, null, exp, null, null, null, SignatureAlgorithm.HS256);
	}

	public String encrypt(String appKey, String jti, String iss, String sub, Date exp, Map<String, Object> headParams) {
		return super.encrypt(appKey, jti, iss, sub, null, exp, null, null, headParams, SignatureAlgorithm.HS256);
	}

	public Claims claims(String appKey, String content) {
		return super.decrypt(appKey, content).getBody();
	}

	public JwsHeader<?> header(String appKey, String content) {
		return super.decrypt(appKey, content).getHeader();
	}

	public String jti(String appKey, String content) {
		return super.decrypt(appKey, content).getBody().getId();
	}

}
