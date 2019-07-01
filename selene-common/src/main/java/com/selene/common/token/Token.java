package com.selene.common.token;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Base interface based on token validation
 * <p>
 * The method includes the encryption and decoding of a piece of information
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface Token {
	public String encrypt(String appKey, String jti, String iss, String sub, String aud, Date exp, Date nbf, Date iat,
			Map<String, Object> headParams, SignatureAlgorithm alg);

	public Jws<Claims> decrypt(String appKey, String content);
}
