package com.omar.security;

public class SecurityConstants {
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users/sign-up";
	public static final String SIGN_IN_URL = "/users/sign-in";
	public static final String RESET_PASSWORD_URL = "/password-reset/**";

	public static final String FILE_UPLOAD_URL = "/file";
	public static final String FILE_GET_URL = "/file/**";
}
