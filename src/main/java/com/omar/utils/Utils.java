package com.omar.utils;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.omar.security.SecurityConstants;

public class Utils {

	public static String createToken(String username) {
		String token = JWT.create().withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.sign(HMAC512(SecurityConstants.SECRET.getBytes()));

		return token;
	}

}
