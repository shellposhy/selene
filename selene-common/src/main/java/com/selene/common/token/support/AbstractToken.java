package com.selene.common.token.support;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.selene.common.token.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Basic implementation for {@code Token}.
 * 
 * @author shaobo shih
 * @version 1.0
 */
public abstract class AbstractToken implements Token {

	@Override
	public String encrypt(String appKey, String jti, String iss, String sub, String aud, Date exp, Date nbf, Date iat,
			Map<String, Object> headParams, SignatureAlgorithm alg) {
		if (alg == null) {
			alg = SignatureAlgorithm.HS256;
		}
		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(appKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, alg.getJcaName());
		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder();
		if (!isNullOrEmpty(jti)) {
			builder.setId(jti);
		}
		if (!isNullOrEmpty(iss)) {
			builder.setIssuer(iss);
		}
		if (!isNullOrEmpty(sub)) {
			builder.setSubject(sub);
		}
		if (!isNullOrEmpty(aud)) {
			builder.setAudience(aud);
		}
		if (exp != null) {
			builder.setExpiration(exp);
		}
		if (nbf != null) {
			builder.setNotBefore(nbf);
		}
		if (iat != null) {
			builder.setIssuedAt(iat);
		}
		if (headParams != null && headParams.size() > 0) {
			builder.setHeaderParams(headParams);
		}
		builder.signWith(alg, signingKey);
		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	@Override
	public Jws<Claims> decrypt(String appKey, String content) {
		// This line will throw an exception if it is not a signed JWS (as
		// expected)
		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appKey)).parseClaimsJws(content);
	}

}
