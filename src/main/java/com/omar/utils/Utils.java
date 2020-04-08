package com.omar.utils;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.omar.errors.AuthorizationException;
import com.omar.security.SecurityConstants;

public class Utils {

	@Autowired
	private static JavaMailSender javaMailSender;

	public static void sendEmail(String to, String subject, String text) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);

		msg.setSubject(subject);
		msg.setText(text);

		javaMailSender.send(msg);

	}

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

}
