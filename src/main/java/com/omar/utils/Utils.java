package com.omar.utils;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.omar.errors.AuthorizationException;
import com.omar.security.SecurityConstants;

@Component
public class Utils {

	public static String createToken(String username) {
		String token = JWT.create().withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.sign(HMAC512(SecurityConstants.SECRET.getBytes()));

		return token;
	}

	public static String getAuthorizedUser(HttpServletRequest request) throws AuthorizationException {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token != null) {
			// parse the token.
			String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes())).build()
					.verify(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getSubject();

			if (user != null) {
				return user;
			}

			throw new AuthorizationException();
		}

		throw new AuthorizationException();
	}

	public static List<String> list(String... strs) {
		List<String> l = new ArrayList<>();

		for (String str : strs) {
			l.add(str);
		}

		return l;
	}

}
