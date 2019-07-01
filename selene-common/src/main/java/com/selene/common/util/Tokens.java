package com.selene.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Token utilities.
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class Tokens {
	/**
	 * 加密
	 * 
	 * @param id
	 * @param issuer
	 * @param subject
	 * @param ttlMillis
	 * @return
	 */
	private String encrypt(String appKey, String id, String issuer, String subject, long ttlMillis) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(appKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	/**
	 * 解密
	 * 
	 * @param jwt
	 */
	private void decrypt(String appKey, String content) {
		// This line will throw an exception if it is not a signed JWS (as
		// expected)
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appKey)).parseClaimsJws(content)
				.getBody();
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
	}

	public static void main(String[] args) {
		Tokens t = new Tokens();
		// 加密
		String jwt = t.encrypt("12121", "3133", "13dsdda", "afrefsa", 20171252);
		System.out.println(jwt);

		// 解密
		t.decrypt("12121", jwt);
	}
}
